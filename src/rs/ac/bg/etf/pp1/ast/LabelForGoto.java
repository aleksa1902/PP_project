// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class LabelForGoto implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String GoToLabel;

    public LabelForGoto (String GoToLabel) {
        this.GoToLabel=GoToLabel;
    }

    public String getGoToLabel() {
        return GoToLabel;
    }

    public void setGoToLabel(String GoToLabel) {
        this.GoToLabel=GoToLabel;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("LabelForGoto(\n");

        buffer.append(" "+tab+GoToLabel);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LabelForGoto]");
        return buffer.toString();
    }
}
