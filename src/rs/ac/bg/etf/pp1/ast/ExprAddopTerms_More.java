// generated with ast extension for cup
// version 0.8
// 25/2/2025 18:20:7


package rs.ac.bg.etf.pp1.ast;

public class ExprAddopTerms_More extends ExprAddopTerms {

    private Addop Addop;
    private Term Term;
    private ExprAddopTerms ExprAddopTerms;

    public ExprAddopTerms_More (Addop Addop, Term Term, ExprAddopTerms ExprAddopTerms) {
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.ExprAddopTerms=ExprAddopTerms;
        if(ExprAddopTerms!=null) ExprAddopTerms.setParent(this);
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public ExprAddopTerms getExprAddopTerms() {
        return ExprAddopTerms;
    }

    public void setExprAddopTerms(ExprAddopTerms ExprAddopTerms) {
        this.ExprAddopTerms=ExprAddopTerms;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Addop!=null) Addop.accept(visitor);
        if(Term!=null) Term.accept(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprAddopTerms_More(\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprAddopTerms!=null)
            buffer.append(ExprAddopTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprAddopTerms_More]");
        return buffer.toString();
    }
}
