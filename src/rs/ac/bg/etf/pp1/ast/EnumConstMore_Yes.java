// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class EnumConstMore_Yes extends EnumConstMore {

    private EnumConst EnumConst;
    private EnumConstMore EnumConstMore;

    public EnumConstMore_Yes (EnumConst EnumConst, EnumConstMore EnumConstMore) {
        this.EnumConst=EnumConst;
        if(EnumConst!=null) EnumConst.setParent(this);
        this.EnumConstMore=EnumConstMore;
        if(EnumConstMore!=null) EnumConstMore.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumConst!=null) EnumConst.accept(visitor);
        if(EnumConstMore!=null) EnumConstMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumConst!=null) EnumConst.traverseTopDown(visitor);
        if(EnumConstMore!=null) EnumConstMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumConst!=null) EnumConst.traverseBottomUp(visitor);
        if(EnumConstMore!=null) EnumConstMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumConstMore_Yes(\n");

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
        buffer.append(") [EnumConstMore_Yes]");
        return buffer.toString();
    }
}
