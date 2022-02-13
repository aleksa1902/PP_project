// generated with ast extension for cup
// version 0.8
// 13/1/2022 2:11:58


package rs.ac.bg.etf.pp1.ast;

public class MulGlobalDeclList extends ProgramDeclList {

    private ProgramDeclList ProgramDeclList;
    private GlobalDeclaration GlobalDeclaration;

    public MulGlobalDeclList (ProgramDeclList ProgramDeclList, GlobalDeclaration GlobalDeclaration) {
        this.ProgramDeclList=ProgramDeclList;
        if(ProgramDeclList!=null) ProgramDeclList.setParent(this);
        this.GlobalDeclaration=GlobalDeclaration;
        if(GlobalDeclaration!=null) GlobalDeclaration.setParent(this);
    }

    public ProgramDeclList getProgramDeclList() {
        return ProgramDeclList;
    }

    public void setProgramDeclList(ProgramDeclList ProgramDeclList) {
        this.ProgramDeclList=ProgramDeclList;
    }

    public GlobalDeclaration getGlobalDeclaration() {
        return GlobalDeclaration;
    }

    public void setGlobalDeclaration(GlobalDeclaration GlobalDeclaration) {
        this.GlobalDeclaration=GlobalDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramDeclList!=null) ProgramDeclList.accept(visitor);
        if(GlobalDeclaration!=null) GlobalDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramDeclList!=null) ProgramDeclList.traverseTopDown(visitor);
        if(GlobalDeclaration!=null) GlobalDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramDeclList!=null) ProgramDeclList.traverseBottomUp(visitor);
        if(GlobalDeclaration!=null) GlobalDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulGlobalDeclList(\n");

        if(ProgramDeclList!=null)
            buffer.append(ProgramDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalDeclaration!=null)
            buffer.append(GlobalDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulGlobalDeclList]");
        return buffer.toString();
    }
}
