package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ActParsCounter extends VisitorAdaptor {
	
	List<Struct> finalActParsList = new ArrayList<>();
	
	Stack<List<Struct>> actParsLists = new Stack<>();
	
	@Override
	public void visit(ActPars actPars) {
		actParsLists.peek().add(actPars.getExpr().struct);
	}
	
	@Override
	public void visit(ActParsListBegin actParsListBegin) {
		actParsLists.push(new ArrayList<>());
	}
	
	@Override
	public void visit(ActParsList_Yes actParsList_Yes) {
		finalActParsList = actParsLists.pop();
	}
	
	@Override
	public void visit(ActParsList_Epsilon actParsList_Epsilon) {
		finalActParsList = actParsLists.pop();
	}
}
