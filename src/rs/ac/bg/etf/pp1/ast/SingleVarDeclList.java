// generated with ast extension for cup
// version 0.8
// 13/1/2022 14:47:19


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDeclList extends VarDeclList {

    private VarDefinition VarDefinition;

    public SingleVarDeclList (VarDefinition VarDefinition) {
        this.VarDefinition=VarDefinition;
        if(VarDefinition!=null) VarDefinition.setParent(this);
    }

    public VarDefinition getVarDefinition() {
        return VarDefinition;
    }

    public void setVarDefinition(VarDefinition VarDefinition) {
        this.VarDefinition=VarDefinition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDefinition!=null) VarDefinition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDefinition!=null) VarDefinition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDefinition!=null) VarDefinition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDeclList(\n");

        if(VarDefinition!=null)
            buffer.append(VarDefinition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDeclList]");
        return buffer.toString();
    }
}
