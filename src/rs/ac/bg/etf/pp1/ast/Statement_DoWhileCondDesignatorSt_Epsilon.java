// generated with ast extension for cup
// version 0.8
// 5/7/2025 19:28:40


package rs.ac.bg.etf.pp1.ast;

public class Statement_DoWhileCondDesignatorSt_Epsilon extends StatementDoWhileCondDesignatorSt {

    public Statement_DoWhileCondDesignatorSt_Epsilon () {
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
        buffer.append("Statement_DoWhileCondDesignatorSt_Epsilon(\n");

        buffer.append(tab);
        buffer.append(") [Statement_DoWhileCondDesignatorSt_Epsilon]");
        return buffer.toString();
    }
}
