// generated with ast extension for cup
// version 0.8
// 10/2/2026 16:37:22


package rs.ac.bg.etf.pp1.ast;

public class SwitchCaseList_More extends SwitchCaseList {

    private SwitchCaseNumber SwitchCaseNumber;
    private SwitchCaseColon SwitchCaseColon;
    private StatementList StatementList;
    private SwitchEndStatement SwitchEndStatement;
    private SwitchCaseList SwitchCaseList;

    public SwitchCaseList_More (SwitchCaseNumber SwitchCaseNumber, SwitchCaseColon SwitchCaseColon, StatementList StatementList, SwitchEndStatement SwitchEndStatement, SwitchCaseList SwitchCaseList) {
        this.SwitchCaseNumber=SwitchCaseNumber;
        if(SwitchCaseNumber!=null) SwitchCaseNumber.setParent(this);
        this.SwitchCaseColon=SwitchCaseColon;
        if(SwitchCaseColon!=null) SwitchCaseColon.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.SwitchEndStatement=SwitchEndStatement;
        if(SwitchEndStatement!=null) SwitchEndStatement.setParent(this);
        this.SwitchCaseList=SwitchCaseList;
        if(SwitchCaseList!=null) SwitchCaseList.setParent(this);
    }

    public SwitchCaseNumber getSwitchCaseNumber() {
        return SwitchCaseNumber;
    }

    public void setSwitchCaseNumber(SwitchCaseNumber SwitchCaseNumber) {
        this.SwitchCaseNumber=SwitchCaseNumber;
    }

    public SwitchCaseColon getSwitchCaseColon() {
        return SwitchCaseColon;
    }

    public void setSwitchCaseColon(SwitchCaseColon SwitchCaseColon) {
        this.SwitchCaseColon=SwitchCaseColon;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SwitchEndStatement getSwitchEndStatement() {
        return SwitchEndStatement;
    }

    public void setSwitchEndStatement(SwitchEndStatement SwitchEndStatement) {
        this.SwitchEndStatement=SwitchEndStatement;
    }

    public SwitchCaseList getSwitchCaseList() {
        return SwitchCaseList;
    }

    public void setSwitchCaseList(SwitchCaseList SwitchCaseList) {
        this.SwitchCaseList=SwitchCaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchCaseNumber!=null) SwitchCaseNumber.accept(visitor);
        if(SwitchCaseColon!=null) SwitchCaseColon.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(SwitchEndStatement!=null) SwitchEndStatement.accept(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchCaseNumber!=null) SwitchCaseNumber.traverseTopDown(visitor);
        if(SwitchCaseColon!=null) SwitchCaseColon.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(SwitchEndStatement!=null) SwitchEndStatement.traverseTopDown(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchCaseNumber!=null) SwitchCaseNumber.traverseBottomUp(visitor);
        if(SwitchCaseColon!=null) SwitchCaseColon.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(SwitchEndStatement!=null) SwitchEndStatement.traverseBottomUp(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchCaseList_More(\n");

        if(SwitchCaseNumber!=null)
            buffer.append(SwitchCaseNumber.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchCaseColon!=null)
            buffer.append(SwitchCaseColon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchEndStatement!=null)
            buffer.append(SwitchEndStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchCaseList!=null)
            buffer.append(SwitchCaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchCaseList_More]");
        return buffer.toString();
    }
}
