// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class ArrayVarDef extends VarDefinition {

    private String LabelArrayName;

    public ArrayVarDef (String LabelArrayName) {
        this.LabelArrayName=LabelArrayName;
    }

    public String getLabelArrayName() {
        return LabelArrayName;
    }

    public void setLabelArrayName(String LabelArrayName) {
        this.LabelArrayName=LabelArrayName;
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
        buffer.append("ArrayVarDef(\n");

        buffer.append(" "+tab+LabelArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVarDef]");
        return buffer.toString();
    }
}
