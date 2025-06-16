// generated with ast extension for cup
// version 0.8
// 25/2/2025 18:20:7


package rs.ac.bg.etf.pp1.ast;

public class StatementList_Epsilon extends StatementList {

    public StatementList_Epsilon () {
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
        buffer.append("StatementList_Epsilon(\n");

        buffer.append(tab);
        buffer.append(") [StatementList_Epsilon]");
        return buffer.toString();
    }
}
