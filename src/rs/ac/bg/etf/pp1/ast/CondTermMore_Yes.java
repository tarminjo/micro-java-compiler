// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class CondTermMore_Yes extends CondTermMore {

    private CondTerm CondTerm;
    private CondTermMore CondTermMore;

    public CondTermMore_Yes (CondTerm CondTerm, CondTermMore CondTermMore) {
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
        this.CondTermMore=CondTermMore;
        if(CondTermMore!=null) CondTermMore.setParent(this);
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public CondTermMore getCondTermMore() {
        return CondTermMore;
    }

    public void setCondTermMore(CondTermMore CondTermMore) {
        this.CondTermMore=CondTermMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTerm!=null) CondTerm.accept(visitor);
        if(CondTermMore!=null) CondTermMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
        if(CondTermMore!=null) CondTermMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        if(CondTermMore!=null) CondTermMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTermMore_Yes(\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTermMore!=null)
            buffer.append(CondTermMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTermMore_Yes]");
        return buffer.toString();
    }
}
