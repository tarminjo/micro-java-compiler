// generated with ast extension for cup
// version 0.8
// 3/7/2025 18:35:42


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclVarDeclList_Yes extends MethodDeclVarDeclList {

    private VarDeclList VarDeclList;
    private MethodDeclVarDeclList MethodDeclVarDeclList;

    public MethodDeclVarDeclList_Yes (VarDeclList VarDeclList, MethodDeclVarDeclList MethodDeclVarDeclList) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.MethodDeclVarDeclList=MethodDeclVarDeclList;
        if(MethodDeclVarDeclList!=null) MethodDeclVarDeclList.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public MethodDeclVarDeclList getMethodDeclVarDeclList() {
        return MethodDeclVarDeclList;
    }

    public void setMethodDeclVarDeclList(MethodDeclVarDeclList MethodDeclVarDeclList) {
        this.MethodDeclVarDeclList=MethodDeclVarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(MethodDeclVarDeclList!=null) MethodDeclVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(MethodDeclVarDeclList!=null) MethodDeclVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(MethodDeclVarDeclList!=null) MethodDeclVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclVarDeclList_Yes(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclVarDeclList!=null)
            buffer.append(MethodDeclVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclVarDeclList_Yes]");
        return buffer.toString();
    }
}
