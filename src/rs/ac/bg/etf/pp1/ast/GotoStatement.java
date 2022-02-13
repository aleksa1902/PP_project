// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class GotoStatement extends Statement {

    private String LabelStatement;

    public GotoStatement (String LabelStatement) {
        this.LabelStatement=LabelStatement;
    }

    public String getLabelStatement() {
        return LabelStatement;
    }

    public void setLabelStatement(String LabelStatement) {
        this.LabelStatement=LabelStatement;
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
        buffer.append("GotoStatement(\n");

        buffer.append(" "+tab+LabelStatement);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GotoStatement]");
        return buffer.toString();
    }
}
