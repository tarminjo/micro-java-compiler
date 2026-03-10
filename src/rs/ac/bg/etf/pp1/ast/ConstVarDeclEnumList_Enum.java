// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class ConstVarDeclEnumList_Enum extends ConstVarDeclEnumList {

    private ConstVarDeclEnumList ConstVarDeclEnumList;
    private EnumDecl EnumDecl;

    public ConstVarDeclEnumList_Enum (ConstVarDeclEnumList ConstVarDeclEnumList, EnumDecl EnumDecl) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.setParent(this);
        this.EnumDecl=EnumDecl;
        if(EnumDecl!=null) EnumDecl.setParent(this);
    }

    public ConstVarDeclEnumList getConstVarDeclEnumList() {
        return ConstVarDeclEnumList;
    }

    public void setConstVarDeclEnumList(ConstVarDeclEnumList ConstVarDeclEnumList) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
    }

    public EnumDecl getEnumDecl() {
        return EnumDecl;
    }

    public void setEnumDecl(EnumDecl EnumDecl) {
        this.EnumDecl=EnumDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.accept(visitor);
        if(EnumDecl!=null) EnumDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseTopDown(visitor);
        if(EnumDecl!=null) EnumDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseBottomUp(visitor);
        if(EnumDecl!=null) EnumDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarDeclEnumList_Enum(\n");

        if(ConstVarDeclEnumList!=null)
            buffer.append(ConstVarDeclEnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumDecl!=null)
            buffer.append(EnumDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarDeclEnumList_Enum]");
        return buffer.toString();
    }
}
