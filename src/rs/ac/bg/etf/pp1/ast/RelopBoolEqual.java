// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:20


package rs.ac.bg.etf.pp1.ast;

public class RelopBoolEqual extends Relop {

    public RelopBoolEqual () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelopBoolEqual(\n");

        buffer.append(tab);
        buffer.append(") [RelopBoolEqual]");
        return buffer.toString();
    }
}
