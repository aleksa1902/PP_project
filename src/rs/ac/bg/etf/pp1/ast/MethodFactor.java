// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:20


package rs.ac.bg.etf.pp1.ast;

public class MethodFactor extends Factor {

    private Designator Designator;
    private FactorParsOpt FactorParsOpt;

    public MethodFactor (Designator Designator, FactorParsOpt FactorParsOpt) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FactorParsOpt=FactorParsOpt;
        if(FactorParsOpt!=null) FactorParsOpt.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FactorParsOpt getFactorParsOpt() {
        return FactorParsOpt;
    }

    public void setFactorParsOpt(FactorParsOpt FactorParsOpt) {
        this.FactorParsOpt=FactorParsOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FactorParsOpt!=null) FactorParsOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FactorParsOpt!=null) FactorParsOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FactorParsOpt!=null) FactorParsOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodFactor(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorParsOpt!=null)
            buffer.append(FactorParsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodFactor]");
        return buffer.toString();
    }
}
