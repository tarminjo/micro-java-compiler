// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class CondFact_1 extends CondFact {

    private NonTernaryExpr NonTernaryExpr;

    public CondFact_1 (NonTernaryExpr NonTernaryExpr) {
        this.NonTernaryExpr=NonTernaryExpr;
        if(NonTernaryExpr!=null) NonTernaryExpr.setParent(this);
    }

    public NonTernaryExpr getNonTernaryExpr() {
        return NonTernaryExpr;
    }

    public void setNonTernaryExpr(NonTernaryExpr NonTernaryExpr) {
        this.NonTernaryExpr=NonTernaryExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonTernaryExpr!=null) NonTernaryExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonTernaryExpr!=null) NonTernaryExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonTernaryExpr!=null) NonTernaryExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact_1(\n");

        if(NonTernaryExpr!=null)
            buffer.append(NonTernaryExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact_1]");
        return buffer.toString();
    }
}
