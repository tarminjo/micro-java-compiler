// generated with ast extension for cup
// version 0.8
// 3/7/2025 18:35:42


package rs.ac.bg.etf.pp1.ast;

public class Relop_Equal extends Relop {

    public Relop_Equal () {
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
        buffer.append("Relop_Equal(\n");

        buffer.append(tab);
        buffer.append(") [Relop_Equal]");
        return buffer.toString();
    }
}
