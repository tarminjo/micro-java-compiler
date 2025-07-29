// generated with ast extension for cup
// version 0.8
// 28/6/2025 16:8:25


package rs.ac.bg.etf.pp1.ast;

public class Expr_Regular extends Expr {

    private Term Term;
    private ExprAddopTerms ExprAddopTerms;

    public Expr_Regular (Term Term, ExprAddopTerms ExprAddopTerms) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.ExprAddopTerms=ExprAddopTerms;
        if(ExprAddopTerms!=null) ExprAddopTerms.setParent(this);
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
        if(Term!=null) Term.accept(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(ExprAddopTerms!=null) ExprAddopTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr_Regular(\n");

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
        buffer.append(") [Expr_Regular]");
        return buffer.toString();
    }
}
