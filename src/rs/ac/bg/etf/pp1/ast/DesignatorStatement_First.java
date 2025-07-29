// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_First extends DesignatorStatement {

    private Designator Designator;
    private DesignatorStatementFirstOptions DesignatorStatementFirstOptions;

    public DesignatorStatement_First (Designator Designator, DesignatorStatementFirstOptions DesignatorStatementFirstOptions) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorStatementFirstOptions=DesignatorStatementFirstOptions;
        if(DesignatorStatementFirstOptions!=null) DesignatorStatementFirstOptions.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorStatementFirstOptions getDesignatorStatementFirstOptions() {
        return DesignatorStatementFirstOptions;
    }

    public void setDesignatorStatementFirstOptions(DesignatorStatementFirstOptions DesignatorStatementFirstOptions) {
        this.DesignatorStatementFirstOptions=DesignatorStatementFirstOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorStatementFirstOptions!=null) DesignatorStatementFirstOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorStatementFirstOptions!=null) DesignatorStatementFirstOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorStatementFirstOptions!=null) DesignatorStatementFirstOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_First(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementFirstOptions!=null)
            buffer.append(DesignatorStatementFirstOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_First]");
        return buffer.toString();
    }
}
