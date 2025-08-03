package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import java.util.*;

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
	private boolean returnHappened = false; // da li je bilo return-a u bilo kojoj metodi
	private int loopCnt = 0;
	
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
	
	@Override
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
		
		if(currentMethod.getType() != Tab.noType && !returnHappened) {
			report_error("Ne postoji iskaz return unutar metode: " + currentMethod.getName(), methodDecl);
			
		}
		
		currentMethod = null;
		returnHappened = false;
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
			// Elem se ne moze uopste dohvatiti iz tabele simbola, i zbog toga je zakomentarisano
			report_error("Neadekvatna promenljiva: " + designator_Regular.getI1(), designator_Regular);
			designator_Regular.obj = Tab.noObj;
		}
		else {
			designator_Regular.obj = varObj;
			
			//ispis report_info poruka za pristup: 
			// 1. Simbolicke konstante
			if(varObj.getKind() == Obj.Con && !varObj.getName().equals("null")) {
				report_info("Pristup simbolickoj konstanti: " + varObj.getName(), designator_Regular);
			}
			// 2. Globalne promenljive
			if(varObj.getKind() == Obj.Var && varObj.getLevel() == 0) {
				report_info("Pristup globalnoj promenljivoj: " + varObj.getName(), designator_Regular);
			} 
			// 3. Lokalne promenljive
			if(varObj.getKind() == Obj.Var && varObj.getLevel() == 1){
				report_info("Pristup lokalnoj promenljivoj: " + varObj.getName(), designator_Regular);
			}
			// 4. Globalne funkcije
			if(varObj.getKind() == Obj.Meth){
				report_info("Pristup globalnoj funkciji: " + varObj.getName(), designator_Regular);
			}
			// 5. Formalni parametri
			if(varObj.getKind() == Obj.Var && varObj.getFpPos() == 1) {
				report_info("Pristup formalnom argumentu: " + varObj.getName(), designator_Regular);
			}
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
			report_info("Pristup elementu niza: " + arrObj.getName(), designator_Array);
		}
	}
	
	/* ================================= FACTOR ================================= */
	
	@Override
	public void visit(Factor_Meth factor_Meth) {
		if(factor_Meth.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + factor_Meth.getDesignator().obj.getName(), 
					factor_Meth);
			factor_Meth.struct = Tab.noType;
		} else {
			List<Struct> fpList = new ArrayList<>();
			for(Obj local: factor_Meth.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					fpList.add(0, local.getType());
				}
			}
			
			ActParsCounter apc = new ActParsCounter();
			factor_Meth.getActParsList().traverseBottomUp(apc);
			List<Struct> apList = apc.finalActParsList;

			try {
				if(fpList.size() != apList.size()) {
					throw new Exception("Greska velicina");
				}
				for(int i = 0; i < fpList.size(); i++) {
					Struct fps = fpList.get(i);
					Struct aps = apList.get(i);
					if(!aps.assignableTo(fps)) {
						throw new Exception("Greska tipovi");
					}
				}
			} 
			catch (Exception e) {
				report_error("[" + e.getMessage() + "] Nekompatibilnost parametara pri pozivu metode: " + 
						factor_Meth.getDesignator().obj.getName(), factor_Meth);
			}
			
			factor_Meth.struct = factor_Meth.getDesignator().obj.getType();
			
		}
	}
	
	@Override
	public void visit(Factor_Var factor_Var) {
		
		factor_Var.struct = factor_Var.getDesignator().obj.getType();
		
//		if(factor_Var.getDesignator().obj == Tab.noObj) {
//			report_error("Nepostojeca promenljiva: " + factor_Var.getDesignator().obj.getName(), 
//					factor_Var);
//			factor_Var.struct = Tab.noType;
//		} else {
//			factor_Var.struct = factor_Var.getDesignator().obj.getType();
//		}
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
	
	/* ================================= EXPRESSION ================================= */
	
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
	
	// TODO: Odradi map metodu
	@Override
	public void visit(Expr_Designator expr_Designator) {
		
	}
	
	
	// novo
	@Override
	public void visit(Expr_Minus expr_Minus) {
		Struct term = expr_Minus.getTerm().struct;
		Struct more = expr_Minus.getExprAddopTerms().struct;
		
		if(term.equals(Tab.intType) && more.equals(Tab.intType)) {
			expr_Minus.struct = Tab.intType;
		} else if(!term.equals(Tab.intType)) {
			report_error("	Negacija ne int vrednosti", expr_Minus);
			expr_Minus.struct = Tab.noType;
		} else if(more.equals(Tab.noType)) {
			expr_Minus.struct = term;
		} else {
			report_error("Addop operacije ne int vrednosti.", expr_Minus);
			expr_Minus.struct = Tab.noType;
		}
	}
	
	/* ================================= DESIGNATOR ================================= */
	
	@Override
	public void visit(DesignatorStatement_Assign designatorStatement_Assign) {
		
		int kind = designatorStatement_Assign.getDesignator().obj.getKind();
		
		if(kind != Obj.Var && kind != Obj.Elem) {
			
			report_error("Dodela u neadekvatnu promenjivu: " + 
				designatorStatement_Assign.getDesignator().obj.getName(), designatorStatement_Assign);
			
		} else if (!designatorStatement_Assign.getExpr().struct
					.assignableTo(designatorStatement_Assign.getDesignator().obj.getType())) {
			
			report_error("Neadekvatna dodela vrednosti u promenljivu: " + 
					designatorStatement_Assign.getDesignator().obj.getName(), designatorStatement_Assign);
		}
	}
	
	// TODO: dodela povratne vrednosti metode
	@Override
	public void visit(DesignatorStatement_ActPars designatorStatement_ActPars) {
		if(designatorStatement_ActPars.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + 
					designatorStatement_ActPars.getDesignator().obj.getName(), designatorStatement_ActPars);
		} else {
			List<Struct> fpList = new ArrayList<>();
			for(Obj local: designatorStatement_ActPars.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					fpList.add(0, local.getType());
				}
			}
			
			ActParsCounter apc = new ActParsCounter();
			designatorStatement_ActPars.getActParsList().traverseBottomUp(apc);
			List<Struct> apList = apc.finalActParsList;

			try {
				
				if(fpList.size() != apList.size()) {
					throw new Exception("Greska velicina");
				}
				for(int i = 0; i < fpList.size(); i++) {
					Struct fps = fpList.get(i);
					Struct aps = apList.get(i);
					if(!aps.assignableTo(fps)) {
						throw new Exception("Greska tipovi");
					}
				}
			} 
			catch (Exception e) {
				report_error("[" + e.getMessage() + "] Nekompatibilnost parametara pri pozivu metode: " + 
					designatorStatement_ActPars.getDesignator().obj.getName(), designatorStatement_ActPars);
			}
			
		}
	}
	
	@Override
	public void visit(DesignatorStatement_Inc designatorStatement_Inc) {
		
		int kind = designatorStatement_Inc.getDesignator().obj.getKind();
		
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Inkrement neadekvatne promenljive: " + 
					designatorStatement_Inc.getDesignator().obj.getName(), designatorStatement_Inc);
		} else if(!designatorStatement_Inc.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Inkrement ne int promenljive: " + 
					designatorStatement_Inc.getDesignator().obj.getName(), designatorStatement_Inc);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_Dec designatorStatement_Dec) {
		
		int kind = designatorStatement_Dec.getDesignator().obj.getKind();
		
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Dekrement neadekvatne promenljive: " + 
					designatorStatement_Dec.getDesignator().obj.getName(), designatorStatement_Dec);
		} else if(!designatorStatement_Dec.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Dekrement ne int promenljive: " + 
					designatorStatement_Dec.getDesignator().obj.getName(), designatorStatement_Dec);
		}
	}
	
	public void visit(Statement_Read statement_Read) {
		
		int kind = statement_Read.getDesignator().obj.getKind();
		Struct type = statement_Read.getDesignator().obj.getType();
		
		if(kind != Obj.Var && kind != Obj.Elem) {
			
			report_error("Read operacija neadekvatne promenljive: " + 
					statement_Read.getDesignator().obj.getName(), statement_Read);
			
		} else if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			
			report_error("Read operacija ne int/char/bool promenljive: " + 
					statement_Read.getDesignator().obj.getName(), statement_Read);
		}
	}
	
	@Override
	public void visit(Statement_PrintNumConst_Yes statement_PrintNumConst_Yes) {
		statement_PrintNumConst_Yes.struct = Tab.intType;
	}
	
	@Override
	public void visit(Statement_PrintNumConst_Epsilon statement_PrintNumConst_Epsilon) {
		statement_PrintNumConst_Epsilon.struct = Tab.noType;
	}
	
	@Override
	public void visit(Statement_Print statement_Print) {
		
		Struct type = statement_Print.getExpr().struct;
		
		//TODO: Logika za set
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("Print operacija ne int/char/bool vrednosti.", statement_Print);
		}
	}
	
	public void visit(Statement_Return statement_Return) {
		returnHappened = true;
		
		if(!currentMethod.getType().equals(statement_Return.getStatementReturnExpr().struct)) {
			report_error("Dogodio se nevalidan return iskaz unutar metode: " + currentMethod.getName(), 
				statement_Return);
		}
	}
	
	@Override
	public void visit(Statement_ReturnExpr_Yes statement_ReturnExpr_Yes) {
		statement_ReturnExpr_Yes.struct = statement_ReturnExpr_Yes.getExpr().struct;
	}
	
	@Override
	public void visit(Statement_ReturnExpr_Epsilon statement_ReturnExpr_Epsilon) {
		statement_ReturnExpr_Epsilon.struct = Tab.noType;
	}
	
	
	@Override
	public void visit(DoNonterm doNonterm){
		loopCnt++;
	}
	
	@Override
	public void visit(Statement_DoWhile statement_DoWhile) {
		loopCnt--;
	}
	
	@Override
	public void visit(Statement_Break statement_Break) {
		if(loopCnt == 0) {
			report_error("Break naredba se ne nalazi unutar tela petlje.", statement_Break);
		}
	}
	
	@Override
	public void visit(Statement_Continue statement_Continue) {
		if(loopCnt == 0) {
			report_error("Continue naredba se ne nalazi unutar tela petlje.", statement_Continue);
		}
	}
	
	@Override
	public void visit(CondFact_1 condFact_1) {
		
		Struct expr = condFact_1.getExpr().struct;
		
		if(!expr.equals(boolType)) {
			report_error("Logicki operand nije tipa bool.", condFact_1);
			condFact_1.struct = Tab.noType;
		} else {
			condFact_1.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFact_2 condFact_2) {
		// ovde proveravam da li su oba izraza tipa bool
		Struct expr1 = condFact_2.getExpr().struct;
		Struct expr2 = condFact_2.getExpr1().struct;
		
		if(!expr1.compatibleWith(expr2)) {
			
			report_error("Logicki operandi nisu kompatibilni.", condFact_2);
			condFact_2.struct = Tab.noType;
		
		} else if (expr1.isRefType() || expr2.isRefType()) {
			
			if(condFact_2.getRelop() instanceof Relop_Equal || condFact_2.getRelop() instanceof Relop_Notequal) {
				condFact_2.struct = boolType;
			} else {
				report_error("Prodjenje ref tipova sa ne adekvatnim relacionim operatorom.", condFact_2);
				condFact_2.struct = Tab.noType;
			}
		
		} else {
			condFact_2.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFactMore_Epsilon condFactMore_Epsilon) {
		condFactMore_Epsilon.struct = Tab.noType;
	}
	
	@Override
	public void visit(CondFactMore_Yes condFactMore_Yes) {
		// ovde proveravam da li su oba izraza int tipa
		Struct condFact = condFactMore_Yes.getCondFact().struct;
		Struct condFactMore = condFactMore_Yes.getCondFactMore().struct;
		
		if(!condFact.equals(boolType)) {
			
			report_error("Logicki operand nije tipa bool.", condFactMore_Yes);
			condFactMore_Yes.struct = Tab.noType;
		} else if(!condFactMore.equals(boolType) && !condFactMore.equals(Tab.noType)) {
			
			report_error("Logicki operand nije tipa bool.", condFactMore_Yes);
			condFactMore_Yes.struct = Tab.noType;
		} else {
			condFactMore_Yes.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		Struct condFact = condTerm.getCondFact().struct;
		Struct condFactMore = condTerm.getCondFactMore().struct;
		
		if(!condFact.equals(boolType)) {
			
			report_error("Logicki operand nije tipa bool.", condTerm);
			condTerm.struct = Tab.noType;
		} else if(!condFactMore.equals(boolType) && !condFactMore.equals(Tab.noType)) {
			
			report_error("Logicki operand nije tipa bool.", condTerm);
			condTerm.struct = Tab.noType;
		} else {
			condTerm.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondTermMore_Epsilon condTermMore_Epsilon) {
		condTermMore_Epsilon.struct = Tab.noType;
	}
	
	@Override
	public void visit(CondTermMore_Yes condTermMore_Yes) {
		// ovde proveravam da li su oba izraza tipa bool
		Struct condTerm = condTermMore_Yes.getCondTerm().struct;
		Struct condTermMore = condTermMore_Yes.getCondTermMore().struct;
		
		if(!condTerm.equals(boolType)) {
			
			report_error("Logicki operand nije tipa bool.", condTermMore_Yes);
			condTermMore_Yes.struct = Tab.noType;
		} else if(!condTermMore.equals(boolType) && !condTermMore.equals(Tab.noType)){
			
			report_error("Logicki operand nije tipa bool.", condTermMore_Yes);
			condTermMore_Yes.struct = Tab.noType;
		} else {
			condTermMore_Yes.struct = boolType;
		}
	}
	
	@Override
	public void visit(Condition condition) {
		// ovde proveravam da li je tip uslova bool
		Struct condTerm = condition.getCondTerm().struct;
		Struct condTermMore = condition.getCondTermMore().struct;
		
		if(!condTerm.equals(boolType)) {
			
			report_error("Logicki operand nije tipa bool.", condition);
			condition.struct = Tab.noType;
		} else if(!condTermMore.equals(boolType) && !condTermMore.equals(Tab.noType)) {
			
			report_error("Logicki operand nije tipa bool.", condition);
			condition.struct = Tab.noType;
		} else {
			condition.struct = boolType;
		}
	}
	
	
	
	
	
	
	
	
}

