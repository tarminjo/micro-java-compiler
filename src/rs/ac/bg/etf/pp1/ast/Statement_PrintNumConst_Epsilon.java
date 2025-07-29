// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class Statement_PrintNumConst_Epsilon extends StatementPrintNumConst {

    public Statement_PrintNumConst_Epsilon () {
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
        buffer.append("Statement_PrintNumConst_Epsilon(\n");

        buffer.append(tab);
        buffer.append(") [Statement_PrintNumConst_Epsilon]");
        return buffer.toString();
    }
}
