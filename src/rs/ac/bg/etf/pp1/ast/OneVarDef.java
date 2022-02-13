// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class OneVarDef extends VarDefinition {

    private String LabelVarDefinitionName;

    public OneVarDef (String LabelVarDefinitionName) {
        this.LabelVarDefinitionName=LabelVarDefinitionName;
    }

    public String getLabelVarDefinitionName() {
        return LabelVarDefinitionName;
    }

    public void setLabelVarDefinitionName(String LabelVarDefinitionName) {
        this.LabelVarDefinitionName=LabelVarDefinitionName;
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
        buffer.append("OneVarDef(\n");

        buffer.append(" "+tab+LabelVarDefinitionName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneVarDef]");
        return buffer.toString();
    }
}
