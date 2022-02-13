package rs.ac.bg.etf.pp1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.util.Log4JUtils;

import rs.etf.pp1.symboltable.*;
import java_cup.runtime.Symbol;
import rs.etf.pp1.mj.runtime.*;

public class MJCompiler implements Compiler{
    private Reader br = null;
    private Logger log;
    private List<CompilerError> errorList = new ArrayList<>();
    
    public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
        
    	log = Logger.getLogger(MJCompiler.class);
        br = null;
        try
        {
            File sourceCode = new File(sourceFilePath);
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());
            
            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);
            
            errorList.addAll(lexer.getLexList());
            
            MJParser p = new MJParser(lexer);
            Symbol s = p.parse();  //pocetak parsiranja
            
            errorList.addAll(p.getErrors());
            
            Program prog = (Program)(s.value);
            // ispis sintaksnog stabla
            log.info(prog.toString(""));
            
            log.info("===================================");
          SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
          
          prog.traverseBottomUp(semanticAnalyzer);
          errorList.addAll(semanticAnalyzer.getErrors());
          Tab.dump();

          log.info("===================================");
          log.info("Broj gresaka u lex " + lexer.getLexList().size());
          log.info("Broj gresaka u syn " + p.getErrors().size());
          log.info("Broj gresaka u sem " + semanticAnalyzer.getErrors().size());
          log.info("===================================");
          if(semanticAnalyzer.passed())
          {
              log.info("Semanticka analiza uspesno zavresna! :D");
              log.info("===================================");
          }
          else
          {
              log.error("Semanticka analiza neuspesno zavresna! :(");
              log.error("===================================");
          }
            if(errorList.size()==0){
                File objFile = new File(outputFilePath);
                if(objFile.exists()) objFile.delete();
                //log.info("Kreiranje CodeGeneatora");
                CodeGenerator codeGenerator = new CodeGenerator();
                //log.info("Ulazak u traverse Bottom up");
                prog.traverseBottomUp(codeGenerator);
                //log.info("Gotov obilazak");
                Code.dataSize = semanticAnalyzer.numberOfVars;
                Code.mainPc = codeGenerator.getMainPC();
                Code.write(new FileOutputStream(objFile));
                log.info("Parsiranje uspesno zavrseno! :D");
            }else{
                log.error("Parsiranje NIJE uspesno zavrseno! :(");
            }
        }
        catch(Exception e)
        {
        }
        finally
        {
            if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
        }
        // TODO Auto-generated method stub
        return errorList;
    }
    static
    {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }
    public static void main(String[] args)
    {
        String sourceFilePath = args[args.length - 2];
        String outputFilePath = args[args.length - 1];
        MJCompiler mc = new MJCompiler();
        mc.compile(sourceFilePath, outputFilePath);
    }
}
