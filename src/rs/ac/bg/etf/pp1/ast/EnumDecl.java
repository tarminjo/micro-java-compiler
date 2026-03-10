// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class EnumDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private EnumBegin EnumBegin;
    private EnumConst EnumConst;
    private EnumConstMore EnumConstMore;

    public EnumDecl (EnumBegin EnumBegin, EnumConst EnumConst, EnumConstMore EnumConstMore) {
        this.EnumBegin=EnumBegin;
        if(EnumBegin!=null) EnumBegin.setParent(this);
        this.EnumConst=EnumConst;
        if(EnumConst!=null) EnumConst.setParent(this);
        this.EnumConstMore=EnumConstMore;
        if(EnumConstMore!=null) EnumConstMore.setParent(this);
    }

    public EnumBegin getEnumBegin() {
        return EnumBegin;
    }

    public void setEnumBegin(EnumBegin EnumBegin) {
        this.EnumBegin=EnumBegin;
    }

    public EnumConst getEnumConst() {
        return EnumConst;
    }

    public void setEnumConst(EnumConst EnumConst) {
        this.EnumConst=EnumConst;
    }

    public EnumConstMore getEnumConstMore() {
        return EnumConstMore;
    }

    public void setEnumConstMore(EnumConstMore EnumConstMore) {
        this.EnumConstMore=EnumConstMore;
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
        if(EnumBegin!=null) EnumBegin.accept(visitor);
        if(EnumConst!=null) EnumConst.accept(visitor);
        if(EnumConstMore!=null) EnumConstMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumBegin!=null) EnumBegin.traverseTopDown(visitor);
        if(EnumConst!=null) EnumConst.traverseTopDown(visitor);
        if(EnumConstMore!=null) EnumConstMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumBegin!=null) EnumBegin.traverseBottomUp(visitor);
        if(EnumConst!=null) EnumConst.traverseBottomUp(visitor);
        if(EnumConstMore!=null) EnumConstMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDecl(\n");

        if(EnumBegin!=null)
            buffer.append(EnumBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumConst!=null)
            buffer.append(EnumConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumConstMore!=null)
            buffer.append(EnumConstMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDecl]");
        return buffer.toString();
    }
}
