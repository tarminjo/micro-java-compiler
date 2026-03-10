// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class ConstVarDeclEnumList_Var extends ConstVarDeclEnumList {

    private ConstVarDeclEnumList ConstVarDeclEnumList;
    private VarDeclList VarDeclList;

    public ConstVarDeclEnumList_Var (ConstVarDeclEnumList ConstVarDeclEnumList, VarDeclList VarDeclList) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public ConstVarDeclEnumList getConstVarDeclEnumList() {
        return ConstVarDeclEnumList;
    }

    public void setConstVarDeclEnumList(ConstVarDeclEnumList ConstVarDeclEnumList) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarDeclEnumList_Var(\n");

        if(ConstVarDeclEnumList!=null)
            buffer.append(ConstVarDeclEnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarDeclEnumList_Var]");
        return buffer.toString();
    }
}
