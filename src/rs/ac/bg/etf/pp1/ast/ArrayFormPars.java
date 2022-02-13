// generated with ast extension for cup
// version 0.8
// 13/1/2022 2:11:58


package rs.ac.bg.etf.pp1.ast;

public class ArrayFormPars extends FormParsDef {

    private Type Type;
    private String LabelParameterName;

    public ArrayFormPars (Type Type, String LabelParameterName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.LabelParameterName=LabelParameterName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getLabelParameterName() {
        return LabelParameterName;
    }

    public void setLabelParameterName(String LabelParameterName) {
        this.LabelParameterName=LabelParameterName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayFormPars(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+LabelParameterName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayFormPars]");
        return buffer.toString();
    }
}
