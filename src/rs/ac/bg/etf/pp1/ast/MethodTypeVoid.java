// generated with ast extension for cup
// version 0.8
// 13/1/2022 2:11:58


package rs.ac.bg.etf.pp1.ast;

public class MethodTypeVoid extends MethodType {

    private String LabelMethodName;

    public MethodTypeVoid (String LabelMethodName) {
        this.LabelMethodName=LabelMethodName;
    }

    public String getLabelMethodName() {
        return LabelMethodName;
    }

    public void setLabelMethodName(String LabelMethodName) {
        this.LabelMethodName=LabelMethodName;
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
        buffer.append("MethodTypeVoid(\n");

        buffer.append(" "+tab+LabelMethodName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodTypeVoid]");
        return buffer.toString();
    }
}
