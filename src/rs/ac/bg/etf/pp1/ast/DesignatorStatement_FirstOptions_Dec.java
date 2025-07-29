// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_FirstOptions_Dec extends DesignatorStatementFirstOptions {

    public DesignatorStatement_FirstOptions_Dec () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_FirstOptions_Dec(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_FirstOptions_Dec]");
        return buffer.toString();
    }
}
