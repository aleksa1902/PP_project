// generated with ast extension for cup
// version 0.8
// 13/1/2022 2:11:58


package rs.ac.bg.etf.pp1.ast;

public class MulActParsList extends ActParsList {

    private ActParsList ActParsList;
    private ActParsDef ActParsDef;

    public MulActParsList (ActParsList ActParsList, ActParsDef ActParsDef) {
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
        this.ActParsDef=ActParsDef;
        if(ActParsDef!=null) ActParsDef.setParent(this);
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
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
        if(ActParsList!=null) ActParsList.accept(visitor);
        if(ActParsDef!=null) ActParsDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
        if(ActParsDef!=null) ActParsDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        if(ActParsDef!=null) ActParsDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulActParsList(\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsDef!=null)
            buffer.append(ActParsDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulActParsList]");
        return buffer.toString();
    }
}
