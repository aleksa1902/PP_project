// generated with ast extension for cup
// version 0.8
// 13/1/2022 2:11:58


package rs.ac.bg.etf.pp1.ast;

public class DesignatorWithArray extends DesignatorArray {

    private LBraceDesignatorArray LBraceDesignatorArray;
    private Expression Expression;

    public DesignatorWithArray (LBraceDesignatorArray LBraceDesignatorArray, Expression Expression) {
        this.LBraceDesignatorArray=LBraceDesignatorArray;
        if(LBraceDesignatorArray!=null) LBraceDesignatorArray.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public LBraceDesignatorArray getLBraceDesignatorArray() {
        return LBraceDesignatorArray;
    }

    public void setLBraceDesignatorArray(LBraceDesignatorArray LBraceDesignatorArray) {
        this.LBraceDesignatorArray=LBraceDesignatorArray;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LBraceDesignatorArray!=null) LBraceDesignatorArray.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LBraceDesignatorArray!=null) LBraceDesignatorArray.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LBraceDesignatorArray!=null) LBraceDesignatorArray.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorWithArray(\n");

        if(LBraceDesignatorArray!=null)
            buffer.append(LBraceDesignatorArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorWithArray]");
        return buffer.toString();
    }
}
