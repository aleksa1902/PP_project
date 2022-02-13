// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:20


package rs.ac.bg.etf.pp1.ast;

public class DesignatorClassic extends DesignatorArray {

    public DesignatorClassic () {
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
        buffer.append("DesignatorClassic(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorClassic]");
        return buffer.toString();
    }
}
