// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:20


package rs.ac.bg.etf.pp1.ast;

public class SingleActParsList extends ActParsList {

    private ActParsDef ActParsDef;

    public SingleActParsList (ActParsDef ActParsDef) {
        this.ActParsDef=ActParsDef;
        if(ActParsDef!=null) ActParsDef.setParent(this);
    }

    public ActParsDef getActParsDef() {
        return ActParsDef;
    }

    public void setActParsDef(ActParsDef ActParsDef) {
        this.ActParsDef=ActParsDef;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsDef!=null) ActParsDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsDef!=null) ActParsDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsDef!=null) ActParsDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleActParsList(\n");

        if(ActParsDef!=null)
            buffer.append(ActParsDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleActParsList]");
        return buffer.toString();
    }
}
