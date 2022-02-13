package rs.ac.bg.etf.pp1;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.HashMap;

import rs.ac.bg.etf.pp1.ast.*;

public class CodeGenerator extends VisitorAdaptor{
	private static final int UNKLOWN_ADDRESS = 0;
	private int mainPC;
	
	private ArrayList<Integer> gotoFixup = new ArrayList<>();
	private ArrayList<String> labelNames = new ArrayList<>();
	private HashMap<String, Integer> mapLabelFixup = new HashMap<>(); 
	
	
	public CodeGenerator() {
		Obj ordMethod = Tab.find("ord");
		ordMethod.setAdr(Code.pc);
		
		Obj chrMethod = Tab.find("chr");
		chrMethod.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj lenMethod = Tab.find("len");
		lenMethod.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public int getMainPC() {
		return mainPC;
	}
	
	public void visit(Program program) {
		for(int i = 0; i < gotoFixup.size(); i++) {
			int fixupAddr = gotoFixup.get(i);
			int lblAddr = mapLabelFixup.get(labelNames.get(i));
			int jmp = lblAddr - fixupAddr + 1;
			Code.put2(fixupAddr, jmp);
		}
	}
	
	public void visit(GotoStatement gotoStatement) {
		Code.putJump(UNKLOWN_ADDRESS);
		int fixupAddr = Code.pc - 2;
		gotoFixup.add(fixupAddr);
		labelNames.add(gotoStatement.getLabelStatement());
	}

	public void visit(GoToStatementList goToStatementList) {
		mapLabelFixup.put(goToStatementList.getGoToLabel(), Code.pc);
	}
	
	// METHOD START
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit); // posto je void ide samo ovo
		Code.put(Code.return_);
		//System.out.println("MethodDecl\n");
	}
	
	public void visit(MethodTypeVoid methodTypeVoid) {
		if(methodTypeVoid.getLabelMethodName().toString().equals("main")) {
			mainPC = Code.pc;
		}
		
		methodTypeVoid.obj.setAdr(mainPC);
		
		Code.put(Code.enter);
		Code.put(methodTypeVoid.obj.getLevel());
		Code.put(methodTypeVoid.obj.getLocalSymbols().size());
		//System.out.println("MethodTypeVoid\n");
	}
	// METHOD END
	
	// DESIGNATORSTATEMENT START
	public void visit(DesingnatorVal desingnatorVal) {
		Designator designator = desingnatorVal.getDesignator();
		Obj objDesignator = null;
		
		if(designator.getDesignatorArray() instanceof DesignatorWithArray) {
			objDesignator = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
		}else {
			objDesignator = designator.obj;
		}
		
		Code.store(objDesignator);
		//System.out.println("DesignatorVal\n");
	}
	
	public void visit(DesignatorIncrement designatorIncrement) {
		Designator designator = designatorIncrement.getDesignator();
		Obj objDesignator = null;
		
		if(designator.getDesignatorArray() instanceof DesignatorWithArray) {
			objDesignator = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
		}else {
			objDesignator = designator.obj;
		}
		
		//System.out.println("Designator INC");
		Code.load(objDesignator);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(objDesignator);
		//System.out.println("DesignatorINC\n");
	}
	
	public void visit(DesignatorDecrement designatorDecrement) {
		Designator designator = designatorDecrement.getDesignator();
		Obj objDesignator = null;
		
		if(designator.getDesignatorArray() instanceof DesignatorWithArray) {
			objDesignator = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
		}else {
			objDesignator = designator.obj;
		}
		
		Code.load(objDesignator);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(objDesignator);
		//System.out.println("DesignatorDEC\n");
	}
	// DESIGNATORSTATEMENT END
	
	// DESIGNATOR START
	public void visit(Designator designator) {
		if((designator.getParent() instanceof DesignatorIncrement || designator.getParent() instanceof DesignatorDecrement) && designator.getDesignatorArray() instanceof DesignatorWithArray) {
			//Code.load(designator.obj);
			Code.put(Code.dup2);
		}
		//System.out.println("Designator " + designator.getLine() + "\n");
	}
	
	public void visit(LBraceDesignatorArray lBraceDesignatorArray) {
		Designator designator = ((Designator)lBraceDesignatorArray.getParent().getParent());
		Code.load(designator.obj);
	}
	// DESIGNATOR END
	
	// FACTOR START
	public void visit(MethodFactor methodFactor) {
		Designator designator = methodFactor.getDesignator();
		Obj objDesignator = null;
		
		if(methodFactor.getParent() instanceof MulTerm) {
			if(designator.getDesignatorArray() instanceof DesignatorWithArray) {
				objDesignator = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
			}else {
				objDesignator = designator.obj;
			}
			Code.load(objDesignator);
		}
		//System.out.println("MethodFactor\n");
	}
	
	public void visit(ConstNumFactor constNumFactor) {
		
		Obj constNum = Tab.insert(Obj.Con, "$", new Struct(constNumFactor.obj.getKind(), constNumFactor.obj.getType()));
		constNum.setLevel(0);
		constNum.setAdr(constNumFactor.getN1());
		
		//System.out.println(constNum.getAdr());
		Code.load(constNum);
		
		if(constNumFactor.getParent() instanceof TermClassic && constNumFactor.getParent().getParent() instanceof ExprClassic && constNumFactor.getParent().getParent().getParent() instanceof MulExprTerm && constNumFactor.getParent().getParent().getParent().getParent() instanceof SubExprTerm) {
			Code.put(Code.neg);
		}
		//System.out.println("constnumfact " + constNumFactor.getN1() + "\n");
	}
	
	public void visit(ConstCharFactor constCharFactor) {
		Obj constChar = Tab.insert(Obj.Con, "$", new Struct(constCharFactor.obj.getKind(), constCharFactor.obj.getType()));
		constChar.setLevel(0);
		constChar.setAdr(constCharFactor.getC1());
		Code.load(constChar);
		//System.out.println("constcharfact\n");
	}
	
	public void visit(ConstBoolFactor constBoolFactor) {
		Obj constBool = Tab.insert(Obj.Con, "$", new Struct(constBoolFactor.obj.getKind(), constBoolFactor.obj.getType()));
		
		if(constBoolFactor.getB1().toString() == "true") {
			constBool.setAdr(1);
		}else {
			constBool.setAdr(0);
		}
		
		constBool.setLevel(0);

		
		
		Code.load(constBool);
		//System.out.println("constboolfact\n");
	}

	public void visit(NewFactorArray newFactorArray) {
		Code.put(Code.newarray);
		if(!newFactorArray.obj.getType().getElemType().equals(Tab.charType)) {
			Code.put(1);
		}else {
			Code.put(0);
		}
		//System.out.println("newfactorarray\n");
	}
	// FACTOR END
	
	// EXPR START
	public void visit(SubExprTerm subExprTerm) {
		Code.put(Code.neg);
		//System.out.println("SubExprTerm\n");
	}
	
	public void visit(MulExprTerm mulExprTerm) {
		if(mulExprTerm.getAddop() instanceof AddopAdd) {
			Code.put(Code.add);
		}else if(mulExprTerm.getAddop() instanceof AddopSub) {
			Code.put(Code.sub);
		}
		//System.out.println("mulexprterm\n");
	}
	// EXPR END
	
	// TERM START
	public void visit(TermClassic termClassic) {
		Obj objDesignator = null;
		
		if(termClassic.getFactor() instanceof MethodFactor) {
			MethodFactor methodFactor = (MethodFactor) termClassic.getFactor();
			Designator designator = methodFactor.getDesignator();
			if(designator.getDesignatorArray() instanceof DesignatorWithArray) {
				objDesignator = new Obj(Obj.Elem, "elem", designator.obj.getType().getElemType());
			}else {
				objDesignator = designator.obj;
			}
			if(designator.getDesignatorArray() instanceof DesignatorWithArray && (termClassic.getParent() instanceof MulExprTerm || (termClassic.getParent() instanceof ExprClassic && termClassic.getParent().getParent() instanceof MulExprTerm))) {
				//System.out.println(designator.obj.getName() + " "+ designator.getDesignatorArray() + " " + designator.getLine());
				Code.put(Code.dup2);
			}
			Code.load(objDesignator);
		}
		//System.out.println("termclassic\n");
	}
	
	public void visit(MulTerm mulTerm) {
		Mulop mulop = mulTerm.getMulop();
		
		if(mulop instanceof MulopMul) {
			Code.put(Code.mul);
		}else if(mulop instanceof MulopDiv) {
			Code.put(Code.div);
		}else if(mulop instanceof MulopMod) {
			Code.put(Code.rem);
		}
		//System.out.println("multerm\n");
	}
	// TERM END
	
	// SINGLESTATEMENT START
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		
		Obj objDesignator = readStatement.getDesignator().obj;
		
		if(readStatement.getDesignator().obj.getType().getKind() == Struct.Array) {
			objDesignator = new Obj(Obj.Elem, "elem", readStatement.getDesignator().obj.getType().getElemType());
		}else {
			
		}
		Code.store(objDesignator);
		//System.out.println("readstatement\n");
	}
	
	public void visit(PrintStatementNoArgs printStatementNoArgs) {
		if(printStatementNoArgs.getExpression().obj.getType().getKind() != Struct.Char) {
			Code.loadConst(5);
			Code.put(Code.print);
		}else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
		//System.out.println("printstatementnoargs\n");
	}
	
	public void visit(PrintStatementArgs printStatementArgs) {
		if(printStatementArgs.getExpression().obj.getType().getKind() != Struct.Char) {
			Code.loadConst(printStatementArgs.getLabelNumConst());
			Code.put(Code.print);
		}else {
			Code.loadConst(printStatementArgs.getLabelNumConst());
			Code.put(Code.bprint);
		}
		//System.out.println("printstatementargs\n");
	}
	// SINGLESTATEMENT END
	
	// CONSTDECL START
	//public void visit(NumConstVal numConstVal) {
	//	Code.load(numConstVal.obj);
	//}
	
	//public void visit(CharConstVal charConstVal) {
	//	Code.load(charConstVal.obj);
	//}
	
	//public void visit(BoolConstVal boolConstVal) {
	//	Code.load(boolConstVal.obj);
	//}
	// CONSTDECL END
}
