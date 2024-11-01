
/**
 * -----------------------------------------------------------------------------
 * Project Name: Parser (Phase 2)
 * File Name: [Parser.java]
 * 
 * Description:
 * 
 * -----------------------------------------------------------------------------
 * Authors: Jordan Dennison, Riley Potter, Matt Chehovin
 * Reviewers: Jee McCloud, Ethan Glenn, Alejandro Santiago-kwon
 * Date Created:  16OCT24
 * Date Last Modified:  2024
 * **/

 import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
 
 public class Parser {
 
	 static class Token {
 
		 String tokenIdentifier;
		 String data;
 
		 public Token(String tokenIdentifier, String data) {
			 this.tokenIdentifier = tokenIdentifier;
			 this.data = data;
		 }
 
		 @Override
		public String toString() {
			 return tokenIdentifier + " Data:  " + data;
		 }
	 }
 
	 Queue<Token> tokens;
	 static Queue<String> atoms = new LinkedList<String>();
	 static int LHS =0;
	 static int RHS =0;
	 

	

		 private Token currentToken;
		 int index = 0;
	  
		 public Parser(Queue<Token> tokens) {
			 this.tokens = tokens;
		 }
	  
		 private void advance() {
			 currentToken = tokens.poll();
		 }
	  
		 public void parse() {
	  
			 // Get the first token
			 currentToken = tokens.poll();
			 // Start parsing the expression
			 parseStatement();
	  
		 }
		 
		 int tempValue = 0;
		 private void parseDecl() {
	 
	 
	  
			 match("LET_KEYWORD");
	  
			 advance();
	  
			 String Ident = match("IDENTIFIER").data;
			 tempValue++;
			 String tempVar = "temp" + tempValue;
	 
			 String atomString = "(MOV," + tempVar+",,"+Ident+")";
			 atoms.add(atomString);
	  
			 advance();
	  
			 match("COLON");
	  
			 advance();
	  
			 // TODO: NOT INCLUDED IN DOC
			 if (currentToken.tokenIdentifier.equals("MUT_KEYWORD")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	  
				 advance();
	  
			 }
	  
			 if (currentToken.tokenIdentifier.equals("I32_KEYWORD") || currentToken.tokenIdentifier.equals("I64_KEYWORD")
					 || currentToken.tokenIdentifier.equals("F32_KEYWORD")
					 || currentToken.tokenIdentifier.equals("F64_KEYWORD")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	  
				 advance();
	  
				 match("ASSIGNMENT_OPERATOR");
	  
				 advance();
	  
				String yep = parseExpr();

				 System.out.println("YEP IS "+yep);


	  
				 match("SEMICOLON");
						
			 } else {
				 throw new RuntimeException("Error: Expected type keyword but got " + currentToken.tokenIdentifier);
			 }
	  tempValue--;
		 }
	 
		 public void parseAssign() {
	  
			
	  
			 match("IDENTIFIER");
	  
			 advance();
	  
			 match("ASSIGNMENT_OPERATOR");
	  
			 advance();
	  
			 parseExpr();
	  
			 match("SEMICOLON");
	  
			 advance();
	  
			
		 }
	  
		 public String parseBase() {
			
	
			LHS++;
	  
			 if (currentToken.tokenIdentifier.equals("INT_LITERAL") || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
					 || currentToken.tokenIdentifier.equals("IDENTIFIER")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
					String toRet = currentToken.data;
				 advance();
						return toRet;
	  
			 } else if (currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {
	  
				 match("LEFT_PARANTHESIS");
	  
				 advance();
	  
				 String Result = parseExpr();
	  
				 match("RIGHT_PARANTHESIS");
				 advance();
				 return Result;
				
			 }
	  
			 else {
	  
				 throw new RuntimeException(
						 "Error: Expected int literal or identifier but got " + currentToken.tokenIdentifier);
	  
			 }

		 }
	  
	// 	 public void parseExpr() {
	// 		tempValue++;
			
	//   System.out.println("IN PARSEEXPR");

	// 		 parseMulDiv();
	  
	// 		 parseExprTail();

		
	  
	// 	 }
	  
	// 	 public void parseMulDiv() {
	//   	  System.out.println("IN PARSEMULDIV");
	// 		 parseBase();
	  
	// 		 parseMulDivTail();
	  
	// 	 }
	  
	// 	 public void parseMulDivTail() {
	// 		System.out.println("IN PARSEMULDIVTAIL");
	  
	// 		 if (currentToken.tokenIdentifier.equals("MULTIPLICATION_OPERATOR")
	// 				 || currentToken.tokenIdentifier.equals("DIVISION_OPERATOR")) {
	  
	// 			 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	// 					RHS++;
	// 			 advance();
	  
	// 			 parseBase();
	  
	// 			 parseMulDivTail();
	  
	// 		 }
	  
	// 	 }
	  
	// 	 public void parseExprTail() {
	//   	  System.out.println("IN PARSEEXPRTAIL");
	// 		 if (currentToken.tokenIdentifier.equals("ADDITION_OPERATOR")
	// 				 || currentToken.tokenIdentifier.equals("SUBTRACTION_OPERATOR")) {
	  
	// 			 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	// 					RHS++;
	// 			 advance();
	  
	// 			 parseMulDiv();
	  
	// 			 parseExprTail();
	  
	// 		 }
	  
	// 	 }

	public String parseExpr() {
		tempValue++;
		

		String yep = parseAddSub();

	

		if (currentToken.tokenIdentifier.equals("ADDITION_OPERATOR")
		|| currentToken.tokenIdentifier.equals("SUBTRACTION_OPERATOR")) {

	System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
		   RHS++;
		   String operation = currentToken.data;   
	advance();
		String yep2 = parseExpr();
		if(operation.equals("+")){
			final String toRet = new String("ADD: " + yep2 +","+ yep);
			return toRet;
		}
		
		}

	
		return yep;
	 }
  
	 public String parseAddSub() {
	
		 String yep = parseBase();

		if (currentToken.tokenIdentifier.equals("MULTIPLICATION_OPERATOR")
					 || currentToken.tokenIdentifier.equals("DIVISION_OPERATOR")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
						RHS++;
						String operation = currentToken.data;
				 advance();
				String yep2 = parseBase();
				if(operation.equals("*")){
					 String toRet ="MULT: " + yep2 +","+ yep;
					
					return toRet;
				}
			
		
				
		 }

		return yep;
  


	 }
  

  
	 
  
	  
		 public void parseStatement() {
	  
			 if (currentToken.tokenIdentifier.equals("IF_KEYWORD")) {
	  
				 parseBranch();
	  
			 } else if (currentToken.tokenIdentifier.equals("IDENTIFIER")
					 || currentToken.tokenIdentifier.equals("INT_LITERAL")
					 || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
					 || currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {
	  
				 parseAssign();
	  
			 } else if (currentToken.tokenIdentifier.equals("LET_KEYWORD")) {
	  
				 parseDecl();
	  
			 } else if (currentToken.tokenIdentifier.equals("FOR_KEYWORD")) {
	  
				 parseFor();
	  
			 } else if (currentToken.tokenIdentifier.equals("WHILE_KEYWORD")) {
	  
				 parseWhile();
	  
			 } else {
	  
				 parseStatements();
	  
			 }
		 }
	  
		 public void parseStatements() {
	  
			 // TODO: ADD IF FOR START OF ANY PRODUCTION
			 parseStatement();
	  
			 // TODO: ELSE THROW ERROR
	  
		 }
	  
		 public void parseBranch() {
	  
			 match("IF_KEYWORD");
	  
			 advance();
	  
			 match("LEFT_PARANTHESIS");
	  
			 advance();
	  
			 parseCond();
	  
			 match("RIGHT_PARANTHESIS");
	  
			 advance();
	  
			 match("LEFT_CURLY_BRACKET");
	  
			 advance();
	  
			 parseStatement();
	  
			 match("RIGHT_CURLY_BRACKET");
	  
			 parseElse();
	  
		 }
	  
		 public void parseElse() {
	  
			 if (currentToken.tokenIdentifier.equals("ELSE_KEYWORD")) {
	  
				 match("ELSE_KEYWORD");
	  
				 advance();
	  
				 match("LEFT_CURLY_BRACKET");
	  
				 advance();
	  
				 parseStatement();
	  
				 // TODO: NOT INCLUDED IN DOC
				 match("RIGHT_CURLY_BRACKET");
	  
			 }
	  
		 }
	  
		 public void parseCond() {
	  
			 if (currentToken.tokenIdentifier.equals("IDENTIFIER") || currentToken.tokenIdentifier.equals("INT_LITERAL")
					 || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	  
				 advance();
	  
			 } else {
	  
				 throw new RuntimeException("Error: Expected expression but got " + currentToken.tokenIdentifier);
	  
			 }
	  
			 if (currentToken.tokenIdentifier.equals("IS_EQUAL_OPERATOR")
					 || currentToken.tokenIdentifier.equals("NOT_EQUAL_OPERATOR")
					 || currentToken.tokenIdentifier.equals("LESS_THAN_OPERATOR")
					 || currentToken.tokenIdentifier.equals("GREATER_THAN_OPERATOR")
					 || currentToken.tokenIdentifier.equals("LESS_THAN_OR_EQUAL_OPERATOR")
					 || currentToken.tokenIdentifier.equals("GREATHER_THAN_OR_EQUAL_OPERATOR")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	  
				 advance();
	  
			 } else {
	  
				 throw new RuntimeException("Error: Expected comparison operator but got " + currentToken.tokenIdentifier);
	  
			 }
	  
			 if (currentToken.tokenIdentifier.equals("IDENTIFIER") || currentToken.tokenIdentifier.equals("INT_LITERAL")
					 || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")) {
	  
				 System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
	  
				 advance();
	  
			 } else {
	  
				 throw new RuntimeException("Error: Expected expression but got " + currentToken.tokenIdentifier);
	  
			 }
	  
		 }
	  
		 // TODO: I don't understand RUST FOR loops
		 public void parseFor() {
	  
			 match("FOR_KEYWORD");
	  
			 advance();
	  
			 match("LEFT_PARANTHESIS");
	  
			 advance();
	  
			 match("IDENTIFIER");
	  
			 advance();
	  
			 match("IN_KEYWORD");
	  
			 advance();
	  
			 match("IDENTIFIER");
	  
			 advance();
	  
			 match("LEFT_CURLY_BRACKET");
	  
			 advance();
	  
			 parseStatement();
	  
			 match("RIGHT_CURLY_BRACKET");
	  
		 }
	  
		 public void parseWhile() {
	  
			 match("WHILE_KEYWORD");
	  
			 advance();
	  
			 match("LEFT_PARANTHESIS");
	  
			 advance();
	  
			 parseCond();
	  
			 match("RIGHT_PARANTHESIS");
	  
			 advance();
	  
			 match("LEFT_CURLY_BRACKET");
	  
			 advance();
	  
			 parseStatement();
	  
			 match("RIGHT_CURLY_BRACKET");
			 
	  
		 }
	  
		 public Token match(String expectedToken) {
	  
			 if (currentToken.tokenIdentifier.equals(expectedToken)) {
	  
				 System.out.println("Matched: " + expectedToken + " --> " + currentToken.data);
				 return currentToken;
	  
			 } else {
	  
				 System.out.println("Error: Expected " + expectedToken + " but got " + currentToken.tokenIdentifier);
			return null;
			 }
	  
		 }
	  
		 public void matchIdentifier() {
	  
		 }
	  
		 public void matchIntLiteral() {
	  
		 }
	  
		 public static String[] readFileToArray(String filename) {
			 ArrayList<String> lines = new ArrayList<>();
	  
			 try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				 String line;
				 while ((line = br.readLine()) != null) {
					 lines.add(line);
				 }
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
	  
			 return lines.toArray(new String[0]);
		 }
	  
		 public static void main(String[] args) {
	  
			 // Test to read in txt file
			 Queue<Token> tokensTest = new LinkedList<>();
			 String filename = "phase2/ifTest.txt";
			 String[] fileLines = readFileToArray(filename);
	  
			 for (String line : fileLines) {
				 String[] lineTokens = line.split(" ");
				 String identifier = lineTokens[0].trim();
				 String data = lineTokens[1].trim();
				 tokensTest.add(new Token(identifier, data));
			 }
	  
			 System.out.println();
			 // System.out.println("Token Test: " + tokensTest);
			 System.out.println();
	  
			 // Test for let statement
			 Queue<Token> letTest = new LinkedList<>();
			 letTest.add(new Token("LET_KEYWORD", "let"));
			 letTest.add(new Token("IDENTIFIER", "id"));
			 letTest.add(new Token("COLON", ":"));
			 letTest.add(new Token("MUT_KEYWORD", "mut"));
			 letTest.add(new Token("I32_KEYWORD", "i32"));
			 letTest.add(new Token("ASSIGNMENT_OPERATOR", "="));


			 letTest.add(new Token("LEFT_PARANTHESIS", "("));
			 letTest.add(new Token("IDENTIFIER", "x"));
			 letTest.add(new Token("ADDITION_OPERATOR", "+"));
			 letTest.add(new Token("IDENTIFIER", "y"));
			 letTest.add(new Token("MULTIPLICATION_OPERATOR", "*"));
			 letTest.add(new Token("IDENTIFIER", "z"));
			 letTest.add(new Token("RIGHT_PARANTHESIS", ")"));
			 letTest.add(new Token("ADDITION_OPERATOR", "+"));
			 letTest.add(new Token("IDENTIFIER", "i"));
			 letTest.add(new Token("SEMICOLON", ";"));
	  
  					// "MULTIPLICATION_OPERATOR"
					// "DIVISION_OPERATOR"
					// "ADDITION_OPERATOR"
					// "SUBTRACTION_OPERATOR"


			 System.out.println("");
			 System.out.println("//////////////////////////////////////////////////");
			 System.out.println("Parser Test");
			 System.out.println("//////////////////////////////////////////////////");
			 System.out.println("");
	  
			 Parser parser = new Parser(letTest);
			 parser.parse();
	  
			 System.out.println("");
			 System.out.println("//////////////////////////////////////////////////");
			 System.out.println("Parser Test didn't crash!");
			 System.out.println("//////////////////////////////////////////////////");
	 
			 while(!atoms.isEmpty()){
			System.out.println(atoms.remove());
		 }
		 System.out.println("Vars: "+LHS+" Operations: "+RHS);
	 }
 
 }