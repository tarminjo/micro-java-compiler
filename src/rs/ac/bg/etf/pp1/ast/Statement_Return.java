// generated with ast extension for cup
// version 0.8
// 25/2/2025 18:20:7


package rs.ac.bg.etf.pp1.ast;

public class Statement_Return extends Statement {

    private StatementReturnExpr StatementReturnExpr;

    public Statement_Return (StatementReturnExpr StatementReturnExpr) {
        this.StatementReturnExpr=StatementReturnExpr;
        if(StatementReturnExpr!=null) StatementReturnExpr.setParent(this);
    }

    public StatementReturnExpr getStatementReturnExpr() {
        return StatementReturnExpr;
    }

    public void setStatementReturnExpr(StatementReturnExpr StatementReturnExpr) {
        this.StatementReturnExpr=StatementReturnExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementReturnExpr!=null) StatementReturnExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementReturnExpr!=null) StatementReturnExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementReturnExpr!=null) StatementReturnExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_Return(\n");

        if(StatementReturnExpr!=null)
            buffer.append(StatementReturnExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_Return]");
        return buffer.toString();
    }
}
