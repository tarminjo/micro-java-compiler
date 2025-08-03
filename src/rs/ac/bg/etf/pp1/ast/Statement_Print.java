// generated with ast extension for cup
// version 0.8
// 3/7/2025 18:35:42


package rs.ac.bg.etf.pp1.ast;

public class Statement_Print extends Statement {

    private Expr Expr;
    private StatementPrintNumConst StatementPrintNumConst;

    public Statement_Print (Expr Expr, StatementPrintNumConst StatementPrintNumConst) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.StatementPrintNumConst=StatementPrintNumConst;
        if(StatementPrintNumConst!=null) StatementPrintNumConst.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public StatementPrintNumConst getStatementPrintNumConst() {
        return StatementPrintNumConst;
    }

    public void setStatementPrintNumConst(StatementPrintNumConst StatementPrintNumConst) {
        this.StatementPrintNumConst=StatementPrintNumConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(StatementPrintNumConst!=null) StatementPrintNumConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(StatementPrintNumConst!=null) StatementPrintNumConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(StatementPrintNumConst!=null) StatementPrintNumConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_Print(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementPrintNumConst!=null)
            buffer.append(StatementPrintNumConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_Print]");
        return buffer.toString();
    }
}
