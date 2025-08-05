// generated with ast extension for cup
// version 0.8
// 5/7/2025 19:0:4


package rs.ac.bg.etf.pp1.ast;

public class Statement_DoWhile extends Statement {

    private DoNonterm DoNonterm;
    private Statement Statement;
    private StatementDoWhileCond StatementDoWhileCond;

    public Statement_DoWhile (DoNonterm DoNonterm, Statement Statement, StatementDoWhileCond StatementDoWhileCond) {
        this.DoNonterm=DoNonterm;
        if(DoNonterm!=null) DoNonterm.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.StatementDoWhileCond=StatementDoWhileCond;
        if(StatementDoWhileCond!=null) StatementDoWhileCond.setParent(this);
    }

    public DoNonterm getDoNonterm() {
        return DoNonterm;
    }

    public void setDoNonterm(DoNonterm DoNonterm) {
        this.DoNonterm=DoNonterm;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public StatementDoWhileCond getStatementDoWhileCond() {
        return StatementDoWhileCond;
    }

    public void setStatementDoWhileCond(StatementDoWhileCond StatementDoWhileCond) {
        this.StatementDoWhileCond=StatementDoWhileCond;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoNonterm!=null) DoNonterm.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(StatementDoWhileCond!=null) StatementDoWhileCond.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoNonterm!=null) DoNonterm.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(StatementDoWhileCond!=null) StatementDoWhileCond.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoNonterm!=null) DoNonterm.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(StatementDoWhileCond!=null) StatementDoWhileCond.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_DoWhile(\n");

        if(DoNonterm!=null)
            buffer.append(DoNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementDoWhileCond!=null)
            buffer.append(StatementDoWhileCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_DoWhile]");
        return buffer.toString();
    }
}
