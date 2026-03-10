package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.Addop_Minus;
import rs.ac.bg.etf.pp1.ast.Addop_Plus;
import rs.ac.bg.etf.pp1.ast.CondFact_1;
import rs.ac.bg.etf.pp1.ast.CondFact_2;
import rs.ac.bg.etf.pp1.ast.CondTerm;
import rs.ac.bg.etf.pp1.ast.Condition;
import rs.ac.bg.etf.pp1.ast.DesignatorName;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement_ActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement_Assign;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement_Dec;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement_Inc;
import rs.ac.bg.etf.pp1.ast.Designator_Length;
import rs.ac.bg.etf.pp1.ast.Designator_Suffix;
import rs.ac.bg.etf.pp1.ast.DoNonterm;
import rs.ac.bg.etf.pp1.ast.Else;
import rs.ac.bg.etf.pp1.ast.ExprAddopTerms_More;
import rs.ac.bg.etf.pp1.ast.Expr_Ternary;
import rs.ac.bg.etf.pp1.ast.FactorMore_Yes;
import rs.ac.bg.etf.pp1.ast.Factor_BoolConst;
import rs.ac.bg.etf.pp1.ast.Factor_CharConst;
import rs.ac.bg.etf.pp1.ast.Factor_Meth;
import rs.ac.bg.etf.pp1.ast.Factor_New;
import rs.ac.bg.etf.pp1.ast.Factor_NumConst;
import rs.ac.bg.etf.pp1.ast.Factor_Var;
import rs.ac.bg.etf.pp1.ast.ForCondition_Epsilon;
import rs.ac.bg.etf.pp1.ast.ForCondition_Yes;
import rs.ac.bg.etf.pp1.ast.ForDesignatorStatement_1_Epsilon;
import rs.ac.bg.etf.pp1.ast.ForDesignatorStatement_1_Yes;
import rs.ac.bg.etf.pp1.ast.ForDesignatorStatement_2_Epsilon;
import rs.ac.bg.etf.pp1.ast.ForDesignatorStatement_2_Yes;
import rs.ac.bg.etf.pp1.ast.ForNonTerm;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodRetAndName_Type;
import rs.ac.bg.etf.pp1.ast.MethodRetAndName_Void;
import rs.ac.bg.etf.pp1.ast.Mulop_Div;
import rs.ac.bg.etf.pp1.ast.Mulop_Mod;
import rs.ac.bg.etf.pp1.ast.Mulop_Mult;
import rs.ac.bg.etf.pp1.ast.NonTernaryExpr_Minus;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.Relop_Equal;
import rs.ac.bg.etf.pp1.ast.Relop_Greater;
import rs.ac.bg.etf.pp1.ast.Relop_Greaterequal;
import rs.ac.bg.etf.pp1.ast.Relop_Less;
import rs.ac.bg.etf.pp1.ast.Relop_Lessequal;
import rs.ac.bg.etf.pp1.ast.Relop_Notequal;
import rs.ac.bg.etf.pp1.ast.Statement_Break;
import rs.ac.bg.etf.pp1.ast.Statement_Continue;
import rs.ac.bg.etf.pp1.ast.Statement_DoWhile;
import rs.ac.bg.etf.pp1.ast.Statement_For;
import rs.ac.bg.etf.pp1.ast.Statement_IfElse_Epsilon;
import rs.ac.bg.etf.pp1.ast.Statement_IfElse_Yes;
import rs.ac.bg.etf.pp1.ast.Statement_Print;
import rs.ac.bg.etf.pp1.ast.Statement_PrintNumConst_Epsilon;
import rs.ac.bg.etf.pp1.ast.Statement_PrintNumConst_Yes;
import rs.ac.bg.etf.pp1.ast.Statement_Read;
import rs.ac.bg.etf.pp1.ast.Statement_Return;
import rs.ac.bg.etf.pp1.ast.Statement_Switch;
import rs.ac.bg.etf.pp1.ast.SwitchCaseColon;
import rs.ac.bg.etf.pp1.ast.SwitchCaseList_Epsilon;
import rs.ac.bg.etf.pp1.ast.SwitchCaseNumber;
import rs.ac.bg.etf.pp1.ast.SwitchEndStatement;
import rs.ac.bg.etf.pp1.ast.SwitchNonTerm;
import rs.ac.bg.etf.pp1.ast.TernaryColon;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.WhileNonTerm;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc = 0; // adresa pocetka main funkcije

	public int getMainPc() {
		return mainPc;
	}
	
	private void initializePredeclaredMethods() {
		// 'ord' and 'chr' are the same code.
		Obj ordMethod = Tab.find("ord");
		Obj chrMethod = Tab.find("chr");
		ordMethod.setAdr(Code.pc);
		chrMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		Obj lenMethod = Tab.find("len");
		lenMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public CodeGenerator() {
		this.initializePredeclaredMethods();
	}
	
	/* METHOD DECALRATIONS */
	
	@Override
	public void visit(MethodRetAndName_Type methodRetAndName_Type) {
		// cuvamo adresu sa koje krece metoda
		methodRetAndName_Type.obj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(methodRetAndName_Type.obj.getLevel()); // b1 - broj formalnih parametara
		Code.put(methodRetAndName_Type.obj.getLocalSymbols().size()); // b2 - velicina lokalnih prom. i fp parametara
	}
	
	@Override
	public void visit(MethodRetAndName_Void methodRetAndName_Void) {
		// cuvamo adresu sa koje krece metoda
		methodRetAndName_Void.obj.setAdr(Code.pc);
		if(methodRetAndName_Void.getI1().equalsIgnoreCase("main"))
				this.mainPc = Code.pc;
			
		Code.put(Code.enter);
		Code.put(methodRetAndName_Void.obj.getLevel()); // b1 - broj formalnih parametara
		Code.put(methodRetAndName_Void.obj.getLocalSymbols().size()); // b2 - velicina lokalnih prom. i fp parametara
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/* STATEMENTS */
	
	// Designator statements
	@Override
	public void visit(DesignatorStatement_Assign designatorStatement_Assign) {
		Code.store(designatorStatement_Assign.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_ActPars designatorStatement_ActPars) {
		// metoda koja se poziva sa leve strane -> iz designator statement-a
		int offset = designatorStatement_ActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		// Ako se metoda pozove sa leve strane a nije void - moramo uraditi pop sa steka
		// To radimo da ne ostane djubre na steku - povratne vrednost metode
		if(designatorStatement_ActPars.getDesignator().obj.getType() != Tab.noType)
				Code.put(Code.pop);
	}
	
	@Override
	public void visit(Factor_Meth factor_Meth) {
		// metoda koja se poziva sa desne strane -> iz factor-a
		int offset = factor_Meth.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(DesignatorStatement_Inc designatorStatement_Inc) {
		if(designatorStatement_Inc.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(designatorStatement_Inc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatement_Inc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_Dec designatorStatement_Dec) {
		if(designatorStatement_Dec.getDesignator().obj.getKind() == Obj.Elem)
				Code.put(Code.dup2);
		Code.load(designatorStatement_Dec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatement_Dec.getDesignator().obj);
	}
	
	@Override
	public void visit(Statement_Print statement_Print) {
		// ako je ovo print sa witdh arg, on je vec na steku
		// u suprotnom treba da ga stavimo na stek - 0
		if(statement_Print.getStatementPrintNumConst() instanceof Statement_PrintNumConst_Epsilon) {
			Code.loadConst(0);
		}
		
		// za karaktere se koristi specijalni bprint
		if(statement_Print.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(Statement_PrintNumConst_Yes statement_PrintNumConst_Yes) {
		Code.loadConst(statement_PrintNumConst_Yes.getN1());
	}
	
	@Override
	public void visit(Statement_Return statement_Return) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_Read statement_Read) {
		if(statement_Read.getDesignator().obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);

		Code.store(statement_Read.getDesignator().obj);
	}
	
	/* EXPR */
	
	@Override
	public void visit(NonTernaryExpr_Minus nonTernaryExpr_Minus) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(ExprAddopTerms_More exprAddopTerms_More) {
		if(exprAddopTerms_More.getAddop() instanceof Addop_Plus)
			Code.put(Code.add);
		else if (exprAddopTerms_More.getAddop() instanceof Addop_Minus)
			Code.put(Code.sub);
	}
	
	/* FACTORS */
	
	@Override
	public void visit(FactorMore_Yes factorMore_Yes) {
		if(factorMore_Yes.getMulop() instanceof Mulop_Mult)
			Code.put(Code.mul);
		else if(factorMore_Yes.getMulop() instanceof Mulop_Div)
			Code.put(Code.div);
		else if(factorMore_Yes.getMulop() instanceof Mulop_Mod)
			Code.put(Code.rem);
	}
	
	@Override
	public void visit(Factor_Var factor_Var) {
			Obj obj = factor_Var.getDesignator().obj;

			// ne treba da radim jos 1 load nad designatorName ako je u pitanju length
			if(factor_Var.getDesignator() instanceof Designator_Suffix) {
				Designator_Suffix designatorSuffix = (Designator_Suffix) factor_Var.getDesignator();
        if(designatorSuffix.getDesignatorSuffix() instanceof Designator_Length) {
        	return;
        }
      }
			
	    if(obj.getKind() != Obj.Type) {
	        Code.load(obj);
	    }
	}
	
	@Override
	public void visit(DesignatorName designatorName) {
		 if(designatorName.obj.getKind() != Obj.Type) {
        Code.load(designatorName.obj);
    }
	}
	
	@Override
	public void visit(Factor_NumConst factor_NumConst) {
		Code.loadConst(factor_NumConst.getN1());
	}
	
	@Override
	public void visit(Factor_CharConst factor_CharConst) {
		Code.loadConst(factor_CharConst.getC1());
	}
	
	@Override
	public void visit(Factor_BoolConst factor_BoolConst) {
		Code.loadConst(factor_BoolConst.getB1());
	}
	
	@Override
	public void visit(Factor_New factor_New) {
		Code.put(Code.newarray);
		if(factor_New.getType().struct.equals(Tab.intType))
			Code.put(0);
		else
			Code.put(1);
	}
	
	/* CONDITION */
	private int returnRelOp(Relop relop) {
		if(relop instanceof Relop_Equal) 
			return Code.eq;
		else if(relop instanceof Relop_Notequal) 
			return Code.ne;
		else if(relop instanceof Relop_Greater) 
			return Code.gt;
		else if(relop instanceof Relop_Greaterequal) 
			return Code.ge;
		else if(relop instanceof Relop_Less) 
			return Code.lt;
		else if(relop instanceof Relop_Lessequal) 
			return Code.le;
		else
			return 0;
	}
	
	private Stack<Integer> skipCondFact = new Stack<>();
	private Stack<Integer> skipCondition = new Stack<>();
	private Stack<Integer> skipThen = new Stack<>();
	private Stack<Integer> skipElse = new Stack<>();
	
	@Override
	public void visit(CondFact_1 condFact_1) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); //netacna nit
		skipCondFact.push(Code.pc - 2);
		// tacna nit (netacna iskocila sa putFalseJump)
	}
	
	@Override
	public void visit(CondFact_2 condFact_2) {
		Code.putFalseJump(returnRelOp(condFact_2.getRelop()), 0); //netacna nit
		skipCondFact.push(Code.pc - 2);
		// tacna nit
		
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		// tacna nit (netacna iskocila sa putFalseJump)
		// Svi AND unutar OR su bili tacni
		Code.putJump(0); // tacne idu na then granu
		skipCondition.push(Code.pc - 2);
		// ovde vracam netacne
		while(!skipCondFact.isEmpty()) {
			Code.fixup(skipCondFact.pop());
		}
		// netacni
		
	}
	
	@Override
	public void visit(Condition condition) {
		// netacni
		Code.putJump(0); // netacne na ELSE
		skipThen.push(Code.pc - 2);
		// THEN
		while(!skipCondition.isEmpty()) {
			Code.fixup(skipCondition.pop());
		}
		// tacne niti
		
	}
	
	@Override
	public void visit(Statement_IfElse_Epsilon statement_IfElse_Epsilon) {
		// tacne
		Code.fixup(skipThen.pop());
		// tacne + netacne
		
	}
	
	@Override
	public void visit(Else else_) {
		// tacne
		Code.putJump(0);
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
		//netacne
	}
	
	@Override
	public void visit(Statement_IfElse_Yes statement_IfElse_Yes) {
		// netacne
		Code.fixup(skipElse.pop());
		// netacne + tacne
	}

	// DO WHILE
	private Stack<Integer> doWhileBegin = new Stack<>();
	
	@Override
	public void visit(DoNonterm doNonterm) {
		// pamtim pocetak doWhile petlje za povratak tacnih niti
		doWhileBegin.push(Code.pc);
		breakJump.push(new ArrayList<Integer>());
		continueJump.push(new ArrayList<Integer>());
	}
	
	@Override
	public void visit(Statement_DoWhile statement_DoWhile) {
		// tacne
		Code.putJump(doWhileBegin.pop());
		Code.fixup(skipThen.pop());
		// netacne
		for(Integer addr: breakJump.pop())
			Code.fixup(addr);
		// netacne + iskocile sa break
	}
	
	@Override
	public void visit(WhileNonTerm whileNonTerm) {
		for(Integer addr: continueJump.pop())
			Code.fixup(addr);
		// iskocile sa continue na ponovnu proveru uslova
	}
	
	// BREAK i CONTINUE
	private Stack<List<Integer>> breakJump = new Stack<>();
	private Stack<List<Integer>> continueJump = new Stack<>();
	
	@Override
	public void visit(Statement_Break statement_Break) {
		Code.putJump(0);
		if(!breakJump.isEmpty())
			breakJump.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(Statement_Continue statement_Continue) {
		Code.putJump(0);
		if(!continueJump.isEmpty())
			continueJump.peek().add(Code.pc - 2);
	}
	
	// LENGTH
	public void visit(Designator_Length designator_Length) {
		Code.put(Code.arraylength);
	}
	
	// TERNARY
	@Override
	public void visit(TernaryColon ternaryColon) {
		// tacne
		Code.putJump(0);
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
		//netacne
	}
	
	@Override
	public void visit(Expr_Ternary expr_Ternary) {
		// netacne
		Code.fixup(skipElse.pop());
		// netacne + tacne
	}
	
	// SWITCH
	private Stack<Stack<Integer>> caseJump = new Stack<>();
	private Stack<Stack<Integer>> fallThroughJump = new Stack<>();
	
	@Override
	public void visit(SwitchNonTerm switchNonTerm) {
		breakJump.push(new ArrayList<Integer>());
		caseJump.push(new Stack<>());
		fallThroughJump.push(new Stack<>());
	}
	
	@Override
	public void visit(SwitchCaseNumber switchCaseNumber) {
		if(!caseJump.peek().isEmpty())
      Code.fixup(caseJump.peek().pop());

	  int value = switchCaseNumber.getN1();
	
	  Code.put(Code.dup);
	  Code.loadConst(value);
	
	  Code.putFalseJump(Code.eq, 0);
	  caseJump.peek().push(Code.pc - 2);
	}
	
	@Override
	public void visit(SwitchCaseColon switchCaseColon) {
		while(!fallThroughJump.peek().isEmpty())
      Code.fixup(fallThroughJump.peek().pop());
	}
	
	@Override
	public void visit(SwitchCaseList_Epsilon switchCaseList_Epsilon) {
		while(!caseJump.peek().isEmpty())
      Code.fixup(caseJump.peek().pop());
		
		while(!fallThroughJump.peek().isEmpty())
      Code.fixup(fallThroughJump.peek().pop());
	}
	
	@Override
	public void visit(SwitchEndStatement switchEndStatement) {
		Code.putJump(0);
		fallThroughJump.peek().push(Code.pc - 2);
	}
	
	@Override
	public void visit(Statement_Switch statement_Switch) {
		for(Integer addr : breakJump.pop())
      Code.fixup(addr);
		// Ovde ce mi ostati jedan fallThrough ako 
		// nema break na poslednjem case-u
		while(!fallThroughJump.peek().isEmpty())
			Code.fixup(fallThroughJump.peek().pop());
		
		fallThroughJump.pop();
		caseJump.pop();

		Code.put(Code.pop);
	}
	
	// FOR
	private Stack<Integer> forBegin = new Stack<>();
	private Stack<Integer> stepBegin = new Stack<>();
	private Stack<Integer> forBodyBegin = new Stack<>();
	// 1 -> condition postoji, 0 -> condition ne postoji
	private Stack<Integer> conditionExists = new Stack<>();
	
	@Override
	public void visit(ForNonTerm forNonTerm) {
		breakJump.push(new ArrayList<Integer>());
		continueJump.push(new ArrayList<Integer>());
	}
	
	@Override
	public void visit(ForDesignatorStatement_1_Yes forDesignatorStatement_1_Yes) {
		forBegin.push(Code.pc);
	}
	
	@Override
	public void visit(ForDesignatorStatement_1_Epsilon forDesignatorStatement_1_Epsilon) {
		forBegin.push(Code.pc);
	}
	
	@Override
	public void visit(ForCondition_Yes forCondition_Yes) {
		conditionExists.push(1);
		// tacni - skacu na statement
		Code.putJump(0);
		forBodyBegin.push(Code.pc - 2);
		// sada pocinje step deo
		stepBegin.push(Code.pc);
	}
	
	@Override
	public void visit(ForCondition_Epsilon forCondition_Epsilon) {
		conditionExists.push(0);
		// tacni - skacu na statement
		Code.putJump(0);
		forBodyBegin.push(Code.pc - 2);
		// sada pocinje step deo
		stepBegin.push(Code.pc);
	}
	
	@Override
	public void visit(ForDesignatorStatement_2_Yes forDesignatorStatement_2_Yes) {
		// prvo evakuisem ove koji skacu odavde - na novu iteraciju for-a
		Code.putJump(forBegin.pop());
		// zatim dovucem one koji izvrsavaju statement - for body
		Code.fixup(forBodyBegin.pop());
	}
	
	@Override
	public void visit(ForDesignatorStatement_2_Epsilon forDesignatorStatement_2_Epsilon) {
		// prvo evakuisem ove koji skacu odavde - na novu iteraciju for-a
		Code.putJump(forBegin.pop());
		// zatim dovucem one koji izvrsavaju statement - for body
		Code.fixup(forBodyBegin.pop());
	}
	
	@Override
	public void visit(Statement_For statement_For) {
		// tacne - skacu na izvrsavanje DesignatorStatement_2
		for(Integer addr: continueJump.pop())
			Code.fixup(addr);
		Code.putJump(stepBegin.pop());
		// Ako je condition prazan - ne postoji skipThen
		if(conditionExists.pop() == 1)
			Code.fixup(skipThen.pop());
		// netacne
		for(Integer addr: breakJump.pop())
			Code.fixup(addr);
		// netacne + iskocile sa break
	}
}
