// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class EnumConst implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private EnumNumConst EnumNumConst;

    public EnumConst (String I1, EnumNumConst EnumNumConst) {
        this.I1=I1;
        this.EnumNumConst=EnumNumConst;
        if(EnumNumConst!=null) EnumNumConst.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public EnumNumConst getEnumNumConst() {
        return EnumNumConst;
    }

    public void setEnumNumConst(EnumNumConst EnumNumConst) {
        this.EnumNumConst=EnumNumConst;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumNumConst!=null) EnumNumConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumNumConst!=null) EnumNumConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumNumConst!=null) EnumNumConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumConst(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(EnumNumConst!=null)
            buffer.append(EnumNumConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumConst]");
        return buffer.toString();
    }
}
