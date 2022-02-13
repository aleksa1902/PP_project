// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class FormParsOpt implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private FormParsDef FormParsDef;
    private FormParsList FormParsList;

    public FormParsOpt (FormParsDef FormParsDef, FormParsList FormParsList) {
        this.FormParsDef=FormParsDef;
        if(FormParsDef!=null) FormParsDef.setParent(this);
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
    }

    public FormParsDef getFormParsDef() {
        return FormParsDef;
    }

    public void setFormParsDef(FormParsDef FormParsDef) {
        this.FormParsDef=FormParsDef;
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
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
        if(FormParsDef!=null) FormParsDef.accept(visitor);
        if(FormParsList!=null) FormParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsDef!=null) FormParsDef.traverseTopDown(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsDef!=null) FormParsDef.traverseBottomUp(visitor);
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsOpt(\n");

        if(FormParsDef!=null)
            buffer.append(FormParsDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsOpt]");
        return buffer.toString();
    }
}
