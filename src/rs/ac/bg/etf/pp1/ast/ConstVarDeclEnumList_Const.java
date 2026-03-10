// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class ConstVarDeclEnumList_Const extends ConstVarDeclEnumList {

    private ConstVarDeclEnumList ConstVarDeclEnumList;
    private ConstDeclList ConstDeclList;

    public ConstVarDeclEnumList_Const (ConstVarDeclEnumList ConstVarDeclEnumList, ConstDeclList ConstDeclList) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.setParent(this);
        this.ConstDeclList=ConstDeclList;
        if(ConstDeclList!=null) ConstDeclList.setParent(this);
    }

    public ConstVarDeclEnumList getConstVarDeclEnumList() {
        return ConstVarDeclEnumList;
    }

    public void setConstVarDeclEnumList(ConstVarDeclEnumList ConstVarDeclEnumList) {
        this.ConstVarDeclEnumList=ConstVarDeclEnumList;
    }

    public ConstDeclList getConstDeclList() {
        return ConstDeclList;
    }

    public void setConstDeclList(ConstDeclList ConstDeclList) {
        this.ConstDeclList=ConstDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.accept(visitor);
        if(ConstDeclList!=null) ConstDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseTopDown(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarDeclEnumList!=null) ConstVarDeclEnumList.traverseBottomUp(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarDeclEnumList_Const(\n");

        if(ConstVarDeclEnumList!=null)
            buffer.append(ConstVarDeclEnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclList!=null)
            buffer.append(ConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarDeclEnumList_Const]");
        return buffer.toString();
    }
}
