package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


public class SemanticAnalyzer extends VisitorAdaptor{
	public static final Struct boolType = new Struct(Struct.Bool);
	
	int numberOfVars;
	
	Struct currentSelectedType = Tab.noType;
	String currentTypeName = "";
	
	Obj currentSelectedMethod;
	String currentMethodName = "";
	List<Obj> currSelMethodFormParsList;
	int nMainMethod = 0;
	
	boolean errorDetected = false;
	
	private List<CompilerError> reportedErrors = new ArrayList<>();
	
	Logger log = Logger.getLogger(getClass());
	
	public SemanticAnalyzer() {
		init();
	}
	
	public static void init() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}
	
	public List<CompilerError> getErrors(){
		return reportedErrors;
	}
	
	public boolean passed() {
		return !errorDetected && nMainMethod == 1;
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		
		reportedErrors.add(new CompilerError(line, msg.toString(), CompilerErrorType.SEMANTIC_ERROR));
		log.error(msg.toString());
	}
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.info(msg.toString());
	}
	
	// PROGRAM START
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Program program) {
		numberOfVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	// PROGRAM END
	
	
	// CONSTDECL START
	public void visit(ConstDef constDef) {
		Obj constVal = constDef.getConstVal().obj;
		
		if(constVal.getType().equals(currentSelectedType)) {
			if(Tab.find(constDef.getLabelConstName()) == Tab.noObj) {
				Obj currConst = Tab.insert(Obj.Con, constDef.getLabelConstName(), currentSelectedType);
				currConst.setAdr(constVal.getAdr());
				currConst.setLevel(0);
			}else {
				report_error("Error on line " + constDef.getLine() + " "  + constDef.getLabelConstName() + " already declared.", constDef);
			}
		}else {
			report_error("Error on line " + constDef.getLine() + " incompatible type.", constDef);
		}
	}
	
	public void visit(BoolConstVal bcv) {
		if(bcv.getBoolValue().toString().equals("true")) {
			bcv.obj = new Obj(Obj.Con, "", SemanticAnalyzer.boolType, 1, Obj.NO_VALUE);
		}else {
			bcv.obj = new Obj(Obj.Con, "", SemanticAnalyzer.boolType, 0, Obj.NO_VALUE);
		}
		//report_info("Const " + bcv.getBoolValue().toString(), bcv);
	}
	
	public void visit(NumConstVal ncv) {
		ncv.obj = new Obj(Obj.Con, "", Tab.intType, ncv.getNumValue(), Obj.NO_VALUE);
		//report_info("Const " + ncv.getNumValue(), ncv);
	}
	
	public void visit(CharConstVal ccv) {
		ccv.obj = new Obj(Obj.Con, "", Tab.charType, ccv.getCharValue(), Obj.NO_VALUE);
		//report_info("Const " + ccv.getCharValue(), ccv);
	}
	// CONSTDECL END
	
	
	// VARDECL START
	public void visit(OneVarDef oneVarDef) {
		String varName = oneVarDef.getLabelVarDefinitionName();
		
		if(!Tab.find(varName).equals(Tab.noObj)) {
			report_error("Error on line " + oneVarDef.getLine() + " variable with name " + varName + " already declared.", null);
		}else {
			oneVarDef.obj = Tab.insert(Obj.Var, varName, currentSelectedType);
			//report_info("Var " + oneVarDef.getLabelVarDefinitionName(), oneVarDef);
		}
	}
	
	public void visit(ArrayVarDef arrVarDef) {
		String arrName = arrVarDef.getLabelArrayName();
		
		if(!Tab.find(arrName).equals(Tab.noObj)) {
			report_error("Error on line " + arrVarDef.getLine() + " variable with name " + arrName + " already declared.", null);
		}else {
			arrVarDef.obj = Tab.insert(Obj.Var, arrName, new Struct(Struct.Array, currentSelectedType));
			//report_info("ArrVar " + arrVarDef.getLabelArrayName(), arrVarDef);
		}
	}
	
	public void visit(VarDeclNoError varDeclNoError) {
		currentSelectedType = null;
	}
	
	// VARDECL END
	
	
	// METHOD START
	public void visit(MethodTypeVoid methodType) {
		if(Tab.currentScope().findSymbol(methodType.getLabelMethodName()) != null) {
			report_error("Error on line " + methodType.getLine() + " method " + methodType.getLabelMethodName() + " already declared.", methodType);
		}else {
			currentSelectedMethod = Tab.insert(Obj.Meth, methodType.getLabelMethodName(), Tab.noType);
			currentMethodName = methodType.getLabelMethodName();
			Tab.openScope();
			currSelMethodFormParsList = new ArrayList<Obj>();
			methodType.obj = currentSelectedMethod;
			
		}
		
		if(methodType.getLabelMethodName().equals("main")) {
			nMainMethod++;
		}
	}
	
	public void visit(MethodDecl methodDecl) {
		if(nMainMethod != 1) {
			report_error("Error missing main method.", methodDecl);
		}else {
			//report_info("Main method", methodDecl);
			Tab.chainLocalSymbols(currentSelectedMethod);
			Tab.closeScope();
			
			currentSelectedMethod = null;
		}
	}
	// METHOD END
	
	
	// FORMPARS START
	public void visit(FormParsClassic formParsClassic) {
		for(Obj obj: currSelMethodFormParsList) {
			if(obj.getName().equals(formParsClassic.getLabelParameterName())) {
				report_error("Error on line " + formParsClassic.getLine() + " because parameter is already declared.", formParsClassic);
				break;
			}
		}
		
		if(formParsClassic.getType().struct == Tab.noType) {
			report_error("Error on line " + formParsClassic.getLine() + " because is undefined parameter type.", formParsClassic);
			formParsClassic.obj = Tab.noObj;
		}else {
			Obj obj = new Obj(Obj.Var, formParsClassic.getLabelParameterName(), formParsClassic.getType().struct);
			currSelMethodFormParsList.add(obj);
			//report_info("FormParsClassic " + formParsClassic.getLabelParameterName(), formParsClassic);
		}
	}
	
	public void visit(ArrayFormPars arrayFormPars) {
		for(Obj obj: currSelMethodFormParsList) {
			if(obj.getName().equals(arrayFormPars.getLabelParameterName())) {
				report_error("Error on line " + arrayFormPars.getLine() + " because parameter is already declared.", arrayFormPars);
				break;
			}
		}
		if(arrayFormPars.getType().struct == Tab.noType) {
			report_error("Error on line " + arrayFormPars.getLine() + " because is undefined parameter type.", arrayFormPars);
			arrayFormPars.obj = Tab.noObj;
		}else {
			Obj obj = new Obj(Obj.Var, arrayFormPars.getLabelParameterName(), new Struct(Struct.Array, arrayFormPars.getType().struct));
			currSelMethodFormParsList.add(obj);
			//report_info("FormParsClassic " + arrayFormPars.getLabelParameterName(), arrayFormPars);
		}
	}

	public void visit(FormParsA formParsA) {
		if(currSelMethodFormParsList != null) {
			int n = 0;
			for(Obj obj : currSelMethodFormParsList) {
				Obj formParsObj = Tab.insert(obj.getKind(), obj.getName(), obj.getType());
				formParsObj.setFpPos(n++);
			}
			currentSelectedMethod.setLevel(currSelMethodFormParsList.size());
		}
	}
	// FORMPARS END
	
		
	// TYPE START
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getLabelTypeName());
		
		if(typeNode == Tab.noObj && typeNode.getType().getKind() != Struct.Bool) {
			report_error("Error on line " + type.getLine() + ", type not found in symbol table.", null);
			type.struct = Tab.noType;
		}else {
			if(Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentSelectedType = typeNode.getType();
				currentTypeName = type.getLabelTypeName();
				//report_info("Type " + type.getLabelTypeName(), type);
			}else {
				report_error("Error on line " + type.getLine() + ", type not found." + type.getLabelTypeName() + " in symbol table.", null);
				type.struct = Tab.noType;
			}
		}
	}
	// TYPE END
	
	
	// STATEMENT START
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType().getKind() != Obj.Var) {
			report_error("Error on line " + readStatement.getLine() + " incompatible type for argument.", readStatement);
		}else {
			if(readStatement.getDesignator().obj.getType().getKind() != Struct.Char &&
				readStatement.getDesignator().obj.getType().getKind() != Struct.Int &&
				readStatement.getDesignator().obj.getType().getKind() != Struct.Bool &&
				readStatement.getDesignator().obj.getType().getKind() != Struct.Array
					) {
				report_error("Error on line " + readStatement.getLine() + " incompatible type for argument.", readStatement);
			}
		}
	}
	
	public void visit(PrintStatementNoArgs printStatementNoArgs) {
		if(printStatementNoArgs.getExpression().obj.getType().getKind() == Struct.Array) {
			if(printStatementNoArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Char &&
				printStatementNoArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Int &&
				printStatementNoArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Bool
					) {
				report_error("Error on line " + printStatementNoArgs.getLine() + " incompatible type of argument.", printStatementNoArgs);
			}	
		}else if(printStatementNoArgs.getExpression().obj.getType().getKind() != Struct.Char &&
				printStatementNoArgs.getExpression().obj.getType().getKind() != Struct.Int &&
				printStatementNoArgs.getExpression().obj.getType().getKind() != Struct.Bool
				) {
			report_error("Error on line " + printStatementNoArgs.getLine() + " incompatible type of argument.", printStatementNoArgs);
		}
	}
	
	public void visit(PrintStatementArgs printStatementArgs) {
		if(printStatementArgs.getExpression().obj.getType().getKind() == Struct.Array) {
			if(printStatementArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Char &&
				printStatementArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Int &&
				printStatementArgs.getExpression().obj.getType().getElemType().getKind() != Struct.Bool	
					) {
				report_error("Error on line " + printStatementArgs.getLine() + " incompatible type of argument.", printStatementArgs);
			}	
		}else if(printStatementArgs.getExpression().obj.getType().getKind() != Struct.Char &&
				printStatementArgs.getExpression().obj.getType().getKind() != Struct.Int &&
				printStatementArgs.getExpression().obj.getType().getKind() != Struct.Bool
				) {
			report_error("Error on line " + printStatementArgs.getLine() + " incompatible type of argument.", printStatementArgs);
		}
	}
	
	public void visit(ReturnStatement returnStatement) {
		if(currentSelectedMethod == Tab.noObj) {
			report_error("Error on line " + returnStatement.getLine() + " method not found.", returnStatement);
		}else {
			if(returnStatement.getExprOpt().obj != Tab.noObj) {
				if(currentSelectedMethod.getType() == Tab.noType) {
					report_error("Error on line " + returnStatement.getLine() + " wrong type of method.", returnStatement);
				}
			}else {
				if(currentSelectedMethod.getType() != Tab.noType) {
					report_error("Error on line " + returnStatement.getLine() + " method dont have type.", returnStatement);
				}else if(!currentSelectedMethod.getType().compatibleWith(returnStatement.getExprOpt().obj.getType())) {
					report_error("Error on line " + returnStatement.getLine() + " incompatible method.", returnStatement);
				}
			}
		}
	}
	// STATEMENT END fali goto
	
	
	// DESIGNATORSTATEMENT START
	public void visit(DesingnatorVal desingnatorVal) {
		Obj obj = Tab.find(desingnatorVal.getDesignator().getLabelDesignatorName().toString());
		
		if(obj.getKind() != Obj.Var) {
			report_error("Error on line " + desingnatorVal.getLine() + " invalind method.", desingnatorVal);
		}
		
		if(obj.getType().getKind() != Struct.Array && !obj.getType().assignableTo(desingnatorVal.getExpression().obj.getType())) {
			report_error("Error on line " +desingnatorVal.getLine() + " wrong type.", desingnatorVal);
		}
	}
	
	public void visit(DesignatorIncrement designatorIncrement) {
		Obj obj = Tab.find(designatorIncrement.getDesignator().getLabelDesignatorName());
		
		if(obj.getKind() != Obj.Var) {
			report_error("Error on line " + designatorIncrement.getLine() + " invalind method.", designatorIncrement);
		}
		
		if(obj.getType().getKind() == Struct.Array && (designatorIncrement.getDesignator().getDesignatorArray() instanceof DesignatorClassic)) {
			report_error("Error on line " + designatorIncrement.getLine() + " invalind method.", designatorIncrement);
		}
		
		if(obj.getType().getKind() != Struct.Array && !obj.getType().equals(Tab.intType)) {
			report_error("Error on line " + designatorIncrement.getLine() + " wrong type.", designatorIncrement);
		}
		
		
		
	}
	
	public void visit(DesignatorDecrement designatorDecrement) {
		Obj obj = Tab.find(designatorDecrement.getDesignator().getLabelDesignatorName());
		
		if(obj.getKind() != Obj.Var) {
			report_error("Error on line " + designatorDecrement.getLine() + " invalind method.", designatorDecrement);
		}
		
		if(!obj.getType().equals(Tab.intType)) {
			report_error("Error on line " + designatorDecrement.getLine() + " wrong type.", designatorDecrement);
		}
		
		if(obj.getType().getKind() == Struct.Array && (designatorDecrement.getDesignator().getDesignatorArray() instanceof DesignatorClassic)) {
			report_error("Error on line " + designatorDecrement.getLine() + " invalind method.", designatorDecrement);
		}
		
	}
	
	//DESIGNATORSTATEMENT END
	
	
	// EXPR START
	public void visit(ExprClassic exprClass) {
		exprClass.obj = exprClass.getTerm().obj;
		//if(exprClass.getLine() == 16) {
			//report_info("ExprClassic " + exprClass.getTerm().obj, exprClass);
			//report_info("" + exprClass.getTerm(), exprClass);
		//}
		
	}
	
	public void visit(SubExprTerm subExprTerm) {
		if(subExprTerm.getTerm().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + subExprTerm.getLine() + " because not int type.", subExprTerm);
		}else {
			subExprTerm.obj = subExprTerm.getTerm().obj;
		}
	}
	
	public void visit(MulExprTerm mulExprTerm) {
		if(mulExprTerm.getExpression().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + mulExprTerm.getLine() + " because not int type.", mulExprTerm);
		}
		
		if(mulExprTerm.getTerm().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + mulExprTerm.getLine() + " because not int type.", mulExprTerm);
		}
		
		if(mulExprTerm.getExpression().obj.getType().getKind() == Struct.Int) {
			mulExprTerm.obj = mulExprTerm.getExpression().obj;
		}else {
			mulExprTerm.obj = mulExprTerm.getTerm().obj;
		}
	
	}
	// EXPR END
	
	// TERM START
	public void visit(TermClassic termClassic) {
		termClassic.obj = termClassic.getFactor().obj;
		//report_info("TermClassic " + termClassic.getFactor(), termClassic);
	}
	
	public void visit(MulTerm mulTerm) {
		if(mulTerm.getTerm().obj.getType().getKind() != Struct.Int || mulTerm.getFactor().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + mulTerm.getLine() + " because not int type.", mulTerm);
		}else {
			mulTerm.obj = mulTerm.getFactor().obj;
		}
	}
	// TERM END
	
	// FACTOR START
	public void visit(MethodFactor methodFactor) {
		//methodFactor.obj = methodFactor.getDesignator().obj;
		Obj obj = Tab.find(methodFactor.getDesignator().getLabelDesignatorName());
		
		if(methodFactor.getDesignator().getDesignatorArray() instanceof DesignatorWithArray) {
			Obj obj1 = new Obj(obj.getKind(), obj.getName(), obj.getType().getElemType());
			methodFactor.obj = obj1;
		}else {
			methodFactor.obj = obj;
		}
	}
	
	public void visit(ConstNumFactor constNumFactor) {
		constNumFactor.obj = new Obj(Obj.Con, "", Tab.intType, constNumFactor.getN1(), Obj.NO_VALUE);
		//report_info("ConstNumFactor " + constNumFactor.getN1(), constNumFactor);
	}
	
	public void visit(ConstCharFactor constCharFactor) {
		constCharFactor.obj = new Obj(Obj.Con, "", Tab.charType, constCharFactor.getC1(), Obj.NO_VALUE);
	}
	
	public void visit(ConstBoolFactor constBoolFactor) {
		if(constBoolFactor.getB1().toString().equals("true")) {
			constBoolFactor.obj = new Obj(Obj.Con, "", SemanticAnalyzer.boolType, 1, Obj.NO_VALUE);
		}else {
			constBoolFactor.obj = new Obj(Obj.Con, "", SemanticAnalyzer.boolType, 0, Obj.NO_VALUE);
		}
	}
	
	public void visit(NewFactorArray newFactorArray) {
		if(newFactorArray.getExpression().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + newFactorArray.getLine() + " because not int type.", newFactorArray);
		}else {
			newFactorArray.obj = new Obj(Obj.Type, "NewArray", new Struct(Struct.Array, currentSelectedType));
		}
	}
	
	public void visit(FactorExpr factorExpr) {
		factorExpr.obj = factorExpr.getExpression().obj;
	}
	// FACTOR END
	
	
	// DESIGNATOR START
	
	public void visit(Designator designator) {
		Obj obj = Tab.find(designator.getLabelDesignatorName());
		designator.obj = obj;
		
		if(obj.equals(Tab.noObj)) {
			report_error("Error on line " + designator.getLine() + " not declared.", null);
		}
		else if(obj.getType().getKind() != Struct.Array && designator.getDesignatorArray() instanceof DesignatorWithArray) {
			report_error("Error on line " + designator.getLine() + " wrong type.", null);
		}
	}
	
	public void visit(DesignatorWithArray designatorWithArray) {
		if(designatorWithArray.getExpression().obj.getType().getKind() != Struct.Int) {
			report_error("Error on line " + designatorWithArray.getLine() + " wrong type.", designatorWithArray);
		}else {
			//report_info("OK", designatorWithArray);
		}
	}
	
	// DESIGNATOR END
	

}
