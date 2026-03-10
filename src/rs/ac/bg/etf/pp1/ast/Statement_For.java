// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class Statement_For extends Statement {

    private ForNonTerm ForNonTerm;
    private ForDesignatorStatement_1 ForDesignatorStatement_1;
    private ForCondition ForCondition;
    private ForDesignatorStatement_2 ForDesignatorStatement_2;
    private Statement Statement;

    public Statement_For (ForNonTerm ForNonTerm, ForDesignatorStatement_1 ForDesignatorStatement_1, ForCondition ForCondition, ForDesignatorStatement_2 ForDesignatorStatement_2, Statement Statement) {
        this.ForNonTerm=ForNonTerm;
        if(ForNonTerm!=null) ForNonTerm.setParent(this);
        this.ForDesignatorStatement_1=ForDesignatorStatement_1;
        if(ForDesignatorStatement_1!=null) ForDesignatorStatement_1.setParent(this);
        this.ForCondition=ForCondition;
        if(ForCondition!=null) ForCondition.setParent(this);
        this.ForDesignatorStatement_2=ForDesignatorStatement_2;
        if(ForDesignatorStatement_2!=null) ForDesignatorStatement_2.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonTerm getForNonTerm() {
        return ForNonTerm;
    }

    public void setForNonTerm(ForNonTerm ForNonTerm) {
        this.ForNonTerm=ForNonTerm;
    }

    public ForDesignatorStatement_1 getForDesignatorStatement_1() {
        return ForDesignatorStatement_1;
    }

    public void setForDesignatorStatement_1(ForDesignatorStatement_1 ForDesignatorStatement_1) {
        this.ForDesignatorStatement_1=ForDesignatorStatement_1;
    }

    public ForCondition getForCondition() {
        return ForCondition;
    }

    public void setForCondition(ForCondition ForCondition) {
        this.ForCondition=ForCondition;
    }

    public ForDesignatorStatement_2 getForDesignatorStatement_2() {
        return ForDesignatorStatement_2;
    }

    public void setForDesignatorStatement_2(ForDesignatorStatement_2 ForDesignatorStatement_2) {
        this.ForDesignatorStatement_2=ForDesignatorStatement_2;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.accept(visitor);
        if(ForDesignatorStatement_1!=null) ForDesignatorStatement_1.accept(visitor);
        if(ForCondition!=null) ForCondition.accept(visitor);
        if(ForDesignatorStatement_2!=null) ForDesignatorStatement_2.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonTerm!=null) ForNonTerm.traverseTopDown(visitor);
        if(ForDesignatorStatement_1!=null) ForDesignatorStatement_1.traverseTopDown(visitor);
        if(ForCondition!=null) ForCondition.traverseTopDown(visitor);
        if(ForDesignatorStatement_2!=null) ForDesignatorStatement_2.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonTerm!=null) ForNonTerm.traverseBottomUp(visitor);
        if(ForDesignatorStatement_1!=null) ForDesignatorStatement_1.traverseBottomUp(visitor);
        if(ForCondition!=null) ForCondition.traverseBottomUp(visitor);
        if(ForDesignatorStatement_2!=null) ForDesignatorStatement_2.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_For(\n");

        if(ForNonTerm!=null)
            buffer.append(ForNonTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStatement_1!=null)
            buffer.append(ForDesignatorStatement_1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondition!=null)
            buffer.append(ForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStatement_2!=null)
            buffer.append(ForDesignatorStatement_2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_For]");
        return buffer.toString();
    }
}
