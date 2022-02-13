// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:20


package rs.ac.bg.etf.pp1.ast;

public class FactorParsOptClassic extends FactorParsOpt {

    private FactorMethodCheck FactorMethodCheck;
    private ActPars ActPars;

    public FactorParsOptClassic (FactorMethodCheck FactorMethodCheck, ActPars ActPars) {
        this.FactorMethodCheck=FactorMethodCheck;
        if(FactorMethodCheck!=null) FactorMethodCheck.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public FactorMethodCheck getFactorMethodCheck() {
        return FactorMethodCheck;
    }

    public void setFactorMethodCheck(FactorMethodCheck FactorMethodCheck) {
        this.FactorMethodCheck=FactorMethodCheck;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorMethodCheck!=null) FactorMethodCheck.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorMethodCheck!=null) FactorMethodCheck.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorMethodCheck!=null) FactorMethodCheck.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorParsOptClassic(\n");

        if(FactorMethodCheck!=null)
            buffer.append(FactorMethodCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorParsOptClassic]");
        return buffer.toString();
    }
}
