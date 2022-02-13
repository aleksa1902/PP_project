package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol; 
import java.util.*;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;


%%

%{
	//CODE Sekcija
	
	private List<CompilerError> lexError = new ArrayList<>();

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	
	public List<CompilerError> getLexList()
	{
		return lexError;
	}

%}

%public
%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 		{ }
"\b" 		{ }
"\t" 		{ }
"\r\n" 		{ }
"\f"		{ }

"program" 	{ return new_symbol(sym.PROG, yytext()); }
"break" 	{ return new_symbol(sym.BREAK, yytext()); }
"class" 	{ return new_symbol(sym.CLASS, yytext()); }
"enum"		{ return new_symbol(sym.ENUM, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const" 	{ return new_symbol(sym.CONST, yytext()); }
"if" 		{ return new_symbol(sym.IF, yytext()); }
"do" 		{ return new_symbol(sym.DO, yytext()); }
"while" 	{ return new_symbol(sym.WHILE, yytext()); }
"new" 		{ return new_symbol(sym.NEW, yytext()); }
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"case" 		{ return new_symbol(sym.CASE, yytext()); }
"goto" 		{ return new_symbol(sym.GOTO, yytext()); }

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-"     	{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }
"==" 		{ return new_symbol(sym.BOOL_EQUAL, yytext()); }
"!=" 		{ return new_symbol(sym.BOOL_NOT_EQUAL, yytext()); }
">" 		{ return new_symbol(sym.GREATER, yytext()); }
">=" 		{ return new_symbol(sym.GREATER_OR_EQUAL, yytext()); }
"<" 		{ return new_symbol(sym.LESS, yytext()); }
"<=" 		{ return new_symbol(sym.LESS_OR_EQUAL, yytext()); }
"&&" 		{ return new_symbol(sym.AND, yytext()); }
"||" 		{ return new_symbol(sym.OR, yytext()); }
"=" 		{ return new_symbol(sym.EQUALS, yytext()); }
"++" 		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
":" 		{ return new_symbol(sym.COLON, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"[" 		{ return new_symbol(sym.LSQUARE, yytext()); }
"]" 		{ return new_symbol(sym.RSQUARE, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}" 		{ return new_symbol(sym.RBRACE, yytext()); }
"?" 		{ return new_symbol(sym.CONDITIONAL, yytext()); }
"." 		{ return new_symbol(sym.DOT, yytext()); }


"+=" 		{ return new_symbol(sym.PLUS_EQUALS, yytext()); }
"-=" 		{ return new_symbol(sym.MINUS_EQUALS, yytext()); }
"*=" 		{ return new_symbol(sym.MUL_EQUALS, yytext()); }
"/=" 		{ return new_symbol(sym.DIV_EQUALS, yytext()); }
"%=" 		{ return new_symbol(sym.MOD_EQUALS, yytext()); }

"//" 			 { yybegin(COMMENT); }
<COMMENT> . 	 { yybegin(COMMENT); }
<COMMENT>"\n"    { yybegin(YYINITIAL); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"true"							{ return new_symbol(sym.BOOL_CONST, new Boolean(yytext())); }
"false"							{ return new_symbol(sym.BOOL_CONST, new Boolean(yytext())); }
[0-9]+       					{ return new_symbol(sym.NUM_CONST, new Integer(yytext())); }
"'"."'" 						{ return new_symbol(sym.CHAR_CONST, new Character(yytext().charAt(1))); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol(sym.IDENT, yytext()); }


. {lexError.add(new CompilerError(yyline+1, "Leksicka greska u liniji: ", CompilerErrorType.LEXICAL_ERROR)); System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }

































