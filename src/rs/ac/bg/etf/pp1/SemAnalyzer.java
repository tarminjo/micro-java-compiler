package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemAnalyzer extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj currentProgram; // cuvanje naziva programa prilikom prolaska kroz ProgramName
	private Struct currentType; // cuvanje tipa za ConstDecl (kroz njega se prolazi pre ConstDecl)
	private int constant; // vrednost kontante kod ConstDecl
	private Struct constantType; // tip kontante prilikom provere podudaranja tipova
	private Struct boolType = Tab.find("bool").getType(); // u vreme kreiranja cvora ce se inicijalizovati
	//private boolean mainHappened = false;
	private Obj currentMethod;
	private Obj mainMethod;
	
	/* ================================= LOG MESSAGES ================================= */
	public void report_error(String message, SyntaxNode info) {
    	errorDetected = true;
    	StringBuilder msg = new StringBuilder(message);
    	int line = (info == null) ? 0: info.getLine();
    	if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
    	int line = (info == null) ? 0: info.getLine();
    	if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }
	
	public boolean passed() {
		return !errorDetected;
	}
	
	/* ================================= SEMANTIC PASS CODE ================================= */
	
	@Override
	public void visit(ProgramName programName) {
		currentProgram = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		// mnogo je bitno da zatvorim scope kad za to dodje vreme
		Tab.openScope();
	}

	@Override
	public void visit(Program program) {
		// ovde se radi prelancavanje globalnog scope u universe, neposredno pre zatvaranja
		Tab.chainLocalSymbols(currentProgram);
		// zatvaranje scope-a
		Tab.closeScope();
		currentProgram = null;
		
		// Level oznacava broj parametara kod main fje, a po uslovima main je void fja bez parametara
		if(mainMethod == null || mainMethod.getLevel() > 0)
			report_error("Program nema adekvatnu main metodu", program);
	}
	
	/* ================================= CONST DECLARATIONS ================================= */

	@Override
	public void visit(ConstDecl constDecl) {
		// ovim if-om proveravm da li je ovo dvostruka definica kontante
		Obj constObj = Tab.find(constDecl.getI1());
		// ako je vracen noObj, znaci da se prvi put definise
		if(constObj != Tab.noObj) {
			report_error("Dvostruka definica konstante: " + constDecl.getI1(), constDecl);
		}
		else {
			if(constantType.assignableTo(currentType)) {
				constObj = Tab.insert(Obj.Con, constDecl.getI1(), currentType);
				constObj.setAdr(constant);
			}
			else {
				report_error("Neadekvatna dodela konstanti: " + constDecl.getI1(), constDecl);
			}
		}
	}
	
	@Override
	public void visit(Constant_Number constant_Number) {
		// character se moze cuvati unutar int-a
		constant = constant_Number.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(Constant_Character constant_Character) {
		// razlog zbog cega se boolean cuvao kao broj -> da mogu sve staviti u jedan constant
		constant = constant_Character.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(Constant_Bool constant_Bool) {
		// razlog zbog cega se boolean cuvao kao broj -> da mogu sve staviti u jedan constant
		constant = constant_Bool.getB1();
		constantType = boolType;
	}

	/* ================================= VAR DECLARATIONS ================================= */
	@Override
	public void visit(VarDecl_Regular varDecl_Regular) {
		
		Obj varObj = null;
		
		// dvostruku deklaraciju trazicemo - unutar trenutnog opsega
		if(currentMethod == null) // globalni opseg
			varObj = Tab.find(varDecl_Regular.getI1());
		else
			varObj = Tab.currentScope().findSymbol(varDecl_Regular.getI1()); // lokalni opseg
		
		if(varObj == Tab.noObj || varObj == null) {
			varObj = Tab.insert(Obj.Var, varDecl_Regular.getI1(), currentType);	
		}
		else {
			report_error("Dvostruka definica promenljive: " + varDecl_Regular.getI1(), varDecl_Regular);
		}
	}
	
	public void visit(VarDecl_Array varDecl_Array) {
		
		Obj varObj = null;
		if(currentMethod == null)
			varObj = Tab.find(varDecl_Array.getI1());
		else
			varObj = Tab.currentScope().findSymbol(varDecl_Array.getI1());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varDecl_Array.getI1(), new Struct(Struct.Array, currentType));
		}
		else{
			report_error("Dvostruka definicija promenljiva: " + varDecl_Array.getI1(), varDecl_Array);
		}
	}
	
	/* ================================= METHOD DECLARATIONS ================================= */
	@Override
	public void visit(MethodRetAndName_Type methodRetAndName_Type) {
		// current type cemo imati prilikom ulaska u MethodDecl  je type levlji sin
		currentMethod = Tab.insert(Obj.Meth, methodRetAndName_Type.getI2(), currentType);
		Tab.openScope();
	}
	
	public void visit(MethodRetAndName_Void methodRetAndName_Void) {
		currentMethod = Tab.insert(Obj.Meth, methodRetAndName_Void.getI1(), Tab.noType);
		Tab.openScope();
		
		if(methodRetAndName_Void.getI1().equalsIgnoreCase("main"))
			mainMethod = currentMethod;
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	/* ================================= FORMPAR DECLARATIONS ================================= */
	@Override
	public void visit(FormPars_Regular formPars_Regular) {
		
		Obj varObj = null;
		
		// dvostruku deklaraciju trazicemo
		if(currentMethod == null)
			report_error("Semanticka greska. [formPars_Regular]", formPars_Regular);
		else
			varObj = Tab.currentScope().findSymbol(formPars_Regular.getI2());
		
		if(varObj == Tab.noObj || varObj == null) {
			varObj = Tab.insert(Obj.Var, formPars_Regular.getI2(), currentType);
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else {
			report_error("Dvostruka definica formalnog parametra: " + formPars_Regular.getI2(), formPars_Regular);
		}
	}
	
	@Override
	public void visit(FormPars_Array formPars_Array) {

		Obj varObj = null;
		
		// dvostruku deklaraciju trazicemo
		if(currentMethod == null)
			report_error("Semanticka greska. [formPars_Regular]", formPars_Array);
		else
			varObj = Tab.currentScope().findSymbol(formPars_Array.getI2());
		
		if(varObj == Tab.noObj || varObj == null) {
			varObj = Tab.insert(Obj.Var, formPars_Array.getI2(), new Struct(Struct.Array, currentType));
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else {
			report_error("Dvostruka definica formalnog parametra: " + formPars_Array.getI2(), formPars_Array);
		}
	}
	
	
	@Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getI1());
		// ako nismo nasli objekat naseg tipa, ovde ce biti noObj i to znaci semanticka greska
		// zbog toga radimo ovu proveru
		if(typeObj == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			currentType = Tab.noType;
		}
		// obj koji trazimo ima Kind = Type i to ovde proveravamo
		else if(typeObj.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + type.getI1(), type);
			currentType = Tab.noType;
		}
		else
			currentType = typeObj.getType();
	}
	
	/* ================================= CONTEXT CONDITIONS ================================= */
	
	//Designator
	@Override
	public void visit(Designator_Regular designator_Regular) {
		//ovde proveravamo da li je uopste definisana promenjiva
		Obj varObj = Tab.find(designator_Regular.getI1());
		if(varObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi: " + designator_Regular.getI1(), designator_Regular);
			designator_Regular.obj = Tab.noObj;
		}
		else if(varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth 
					/*&& varObj.getKind() != Obj.Elem - ne znam zbog cega je ovo ovde*/) {
			report_error("Neadekvatna promenljiva: " + designator_Regular.getI1(), designator_Regular);
			designator_Regular.obj = Tab.noObj;
		}
		else {
			designator_Regular.obj = varObj;
		}
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Obj varObj = Tab.find(designatorArrayName.getI1());
		if(varObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi niza: " + designatorArrayName.getI1(), designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		}
		else if(varObj.getKind() != Obj.Var || varObj.getType().getKind() != Struct.Array) {
			report_error("Neadekvatna promenljiva niza: " + designatorArrayName.getI1(), designatorArrayName);
			designatorArrayName.obj = Tab.noObj;
		}
		else {
			designatorArrayName.obj = varObj;
		}
	}
	
	@Override
	public void visit(Designator_Array designator_Array) {
		Obj arrObj = designator_Array.getDesignatorArrayName().obj;
		if(arrObj == Tab.noObj) {
			designator_Array.obj = Tab.noObj;
		} else if(!designator_Array.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje sa ne int vrednosti.", designator_Array);
			designator_Array.obj = Tab.noObj;
		}
		else {
			designator_Array.obj = new Obj(Obj.Elem, arrObj.getName() + "[$]", arrObj.getType().getElemType());
		}
	}
	
	//Factor
	@Override
	public void visit(Factor_Designator factor_Designator) {
		factor_Designator.struct = factor_Designator.getDesignator().obj.getType();		
	}
	
	@Override
	public void visit(Factor_NumConst factor_NumConst) {
		factor_NumConst.struct = Tab.intType;
	}
	
	@Override
	public void visit(Factor_CharConst factor_CharConst) {
		factor_CharConst.struct = Tab.charType;
	}
	
	@Override
	public void visit(Factor_BoolConst factor_BoolConst) {
		factor_BoolConst.struct = boolType;
	}
	
	@Override
	public void visit(Factor_New factor_New) {
		if(!factor_New.getExpr().struct.equals(Tab.intType)) {
			report_error("Velicina niza nije int tipa.", factor_New);
			factor_New.struct = Tab.noType;
		} else {
			factor_New.struct = new Struct(Struct.Array, currentType);
		}
	}
	
	@Override
	public void visit(Factor_Expr factor_Expr) {
		factor_Expr.struct = factor_Expr.getExpr().struct;
	}
	
	@Override
	public void visit(Expr_Minus expr_Minus) {
		if(!expr_Minus.getTerm().getFactor().struct.equals(Tab.intType)) {
			report_error("Negacija ne int vrednosti", expr_Minus);
		}
	}
	
	/* EXPR */
	@Override
	public void visit(FactorMore_Epsilon factorMore_Epsilon) {
		factorMore_Epsilon.struct = Tab.noType;
	}

	@Override
	public void visit(FactorMore_Yes factorMore_Yes) {
		Struct factor = factorMore_Yes.getFactor().struct;
		Struct more = factorMore_Yes.getFactorMore().struct;
		
		if(factor.equals(Tab.intType) && (more.equals(Tab.intType) || more.equals(Tab.noType))) {
			factorMore_Yes.struct = Tab.intType;
		} else {
			report_error("Mulop operacije ne int vrednosti.", factorMore_Yes);
			factorMore_Yes.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Term term) {
		Struct factor = term.getFactor().struct;
		Struct more = term.getFactorMore().struct;
		
		if(factor.equals(Tab.intType) && more.equals(Tab.intType)) {
			term.struct = Tab.intType;
		} else if(more.equals(Tab.noType)) {
			term.struct = factor;
		} 
		else {
			report_error("Mulop operacije ne int vrednosti.", term);
			term.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(ExprAddopTerms_Epsilon exprAddopTerms_Epsilon) {
		exprAddopTerms_Epsilon.struct = Tab.noType;
	}
	
	@Override
	public void visit(ExprAddopTerms_More exprAddopTerms_More) {
		Struct term = exprAddopTerms_More.getTerm().struct;
		Struct more = exprAddopTerms_More.getExprAddopTerms().struct;
		
		if(term.equals(Tab.intType) && (more.equals(Tab.intType) || more.equals(Tab.noType))) {
			exprAddopTerms_More.struct = Tab.intType;
		} else {
			report_error("Addop operacije ne int vrednosti.", exprAddopTerms_More);
			exprAddopTerms_More.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Expr_Regular expr_Regular) {
		Struct term = expr_Regular.getTerm().struct;
		Struct more = expr_Regular.getExprAddopTerms().struct;
		
		if(term.equals(Tab.intType) && more.equals(Tab.intType)) {
			expr_Regular.struct = Tab.intType;
		} else if(more.equals(Tab.noType)) {
			expr_Regular.struct = term;
		} else {
			report_error("Addop operacije ne int vrednosti.", expr_Regular);
			expr_Regular.struct = Tab.noType;
		}
	}
	
	// OVO JE OSTALO KAO NESTO STO NIJE ODRADJENO NA TUTORIJALIMA
	@Override
	public void visit(Expr_Designator expr_Designator) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
