// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class Statement_DoWhileCond_Yes extends StatementDoWhileCond {

    private Condition Condition;
    private StatementDoWhileCondDesignatorSt StatementDoWhileCondDesignatorSt;

    public Statement_DoWhileCond_Yes (Condition Condition, StatementDoWhileCondDesignatorSt StatementDoWhileCondDesignatorSt) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.StatementDoWhileCondDesignatorSt=StatementDoWhileCondDesignatorSt;
        if(StatementDoWhileCondDesignatorSt!=null) StatementDoWhileCondDesignatorSt.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public StatementDoWhileCondDesignatorSt getStatementDoWhileCondDesignatorSt() {
        return StatementDoWhileCondDesignatorSt;
    }

    public void setStatementDoWhileCondDesignatorSt(StatementDoWhileCondDesignatorSt StatementDoWhileCondDesignatorSt) {
        this.StatementDoWhileCondDesignatorSt=StatementDoWhileCondDesignatorSt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(StatementDoWhileCondDesignatorSt!=null) StatementDoWhileCondDesignatorSt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(StatementDoWhileCondDesignatorSt!=null) StatementDoWhileCondDesignatorSt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(StatementDoWhileCondDesignatorSt!=null) StatementDoWhileCondDesignatorSt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_DoWhileCond_Yes(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementDoWhileCondDesignatorSt!=null)
            buffer.append(StatementDoWhileCondDesignatorSt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_DoWhileCond_Yes]");
        return buffer.toString();
    }
}
