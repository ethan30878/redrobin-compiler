
/**
 * -----------------------------------------------------------------------------
 * Project Name: Parser (Phase 2)
 * File Name: [Parser.java]
 * 
 * Description: Phase 2 Parser Code
 * 
 * -----------------------------------------------------------------------------
 * Authors: Jordan Dennison, Riley Potter, Matt Chehovin
 * Reviewers: Jee McCloud, Ethan Glenn, Alejandro Santiago-kwon
 * Date Created:  16OCT24
 * Date Last Modified:  1NOV2024
 * **/
package phase2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	static int LHS = 0;
	static int RHS = 0;
	int destinationLabel = 0;

	private Token currentToken;
	int index = 0;

	public Parser(Queue<Token> tokens) {
		this.tokens = tokens;
	}

	private void advance() {
		currentToken = tokens.poll();
		if (currentToken == null) {
			System.out.println("Warning: No more tokens available.");
		}
	}
	

	public void parse() {

		// Get the first token
		currentToken = tokens.poll();
		// Start parsing the expression
		parseStatements();

	}

	int tempValue = 1; // Start from 1 to avoid T-1

	private void parseDecl() {

		match("LET_KEYWORD");

		advance();

		String Ident = match("IDENTIFIER").data;
		tempValue++;
		String tempVar = "temp" + tempValue;

		// String atomString = "(MOV," + tempVar + ",," + Ident + ")";
		// atoms.add(atomString);

		advance();

		if (currentToken.tokenIdentifier.equals("COLON")) {

			match("COLON");

			advance();

			// TODO: NOT INCLUDED IN DOC
			if (currentToken.tokenIdentifier.equals("MUT_KEYWORD")) {

				System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

				advance();

				if (currentToken.tokenIdentifier.equals("I32_KEYWORD")
						|| currentToken.tokenIdentifier.equals("I64_KEYWORD")
						|| currentToken.tokenIdentifier.equals("F32_KEYWORD")
						|| currentToken.tokenIdentifier.equals("F64_KEYWORD")) {

					System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

					advance();

				} else {
					throw new RuntimeException("Error: Expected type keyword but got " + currentToken.tokenIdentifier);
				}

			}

		}

		match("ASSIGNMENT_OPERATOR");

		advance();

		String result = parseExpr();
		result = createExprAtoms(result);

		// System.out.println("YEP IS " + result);

		match("SEMICOLON");
		advance();
		atoms.add("(MOV," + result + ",," + Ident + ")");

	}
	public void parseAssign() {
		Token id = match("IDENTIFIER");
		advance();
	
		match("ASSIGNMENT_OPERATOR");
		advance();
	
		String result = parseExpr();
	
		// Directly assign literals without using a temporary variable
		if (result.matches("^[0-9]+$")) { // If result is a literal (integer)
			atoms.add("(MOV," + result + ",," + id.data + ")");
		} else {
			// If it's an expression, result is already a temp (e.g., T1, T2, ...)
			atoms.add("(MOV," + result + ",," + id.data + ")");
		}
	
		match("SEMICOLON");
		advance();
	}
	
	
	
	
		

		public String parseBase() {
			LHS++;
		
			if (currentToken.tokenIdentifier.equals("INT_LITERAL") || 
				currentToken.tokenIdentifier.equals("FLOAT_LITERAL") || 
				currentToken.tokenIdentifier.equals("IDENTIFIER")) {
		
				System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);
				String toRet = currentToken.data;
				advance();
				return toRet;
		
			} else if (currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {
		
				match("LEFT_PARANTHESIS");
				advance();
		
				String result = parseExpr();
				result = createExprAtoms(result);
		
				match("RIGHT_PARANTHESIS");
				advance();
				return result;
		
			} else {
				throw new RuntimeException(
					"Error: Expected int literal or identifier but got " + currentToken.tokenIdentifier);
			}
		}
		
	

	// public void parseExpr() {
	// tempValue++;

	// System.out.println("IN PARSEEXPR");

	// parseMulDiv();

	// parseExprTail();

	// }

	// public void parseMulDiv() {
	// System.out.println("IN PARSEMULDIV");
	// parseBase();

	// parseMulDivTail();

	// }

	// public void parseMulDivTail() {
	// System.out.println("IN PARSEMULDIVTAIL");

	// if (currentToken.tokenIdentifier.equals("MULTIPLICATION_OPERATOR")
	// || currentToken.tokenIdentifier.equals("DIVISION_OPERATOR")) {

	// System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " +
	// currentToken.data);
	// RHS++;
	// advance();

	// parseBase();

	// parseMulDivTail();

	// }

	// }

	// public void parseExprTail() {
	// System.out.println("IN PARSEEXPRTAIL");
	// if (currentToken.tokenIdentifier.equals("ADDITION_OPERATOR")
	// || currentToken.tokenIdentifier.equals("SUBTRACTION_OPERATOR")) {

	// System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " +
	// currentToken.data);
	// RHS++;
	// advance();

	// parseMulDiv();

	// parseExprTail();

	// }

	// }

	public String parseExpr() {
		String left = parseAddSub();
		
		// If no operations and 'left' is already an INT_LITERAL or IDENTIFIER, use it directly
		if (!left.startsWith("T") && !left.matches("^[0-9]+$")) {
			String tempVar = "T" + tempValue++;
			atoms.add("(MOV," + left + ",," + tempVar + ")");
			left = tempVar;
		}
	
		while (currentToken != null &&
			   (currentToken.tokenIdentifier.equals("ADDITION_OPERATOR") ||
				currentToken.tokenIdentifier.equals("SUBTRACTION_OPERATOR"))) {
	
			Token operatorToken = match(currentToken.tokenIdentifier);
	
			String operation = operatorToken.tokenIdentifier.equals("ADDITION_OPERATOR") ? "ADD" : "SUB";
	
			advance();
			String right = parseAddSub();
	
			String tempVar = "T" + tempValue++;
			atoms.add("(" + operation + "," + left + "," + right + "," + tempVar + ")");
			left = tempVar;
		}
	
		return left;
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
			if (operation.equals("*")) {
				String toRet = "(MUL," + yep2 + "," + yep + ")";

				return toRet;
			} else {
				String toRet = "(DIV," + yep + "," + yep2 + ")";

				return toRet;
			}

		}

		return yep;

	}

	public void parseStatement() {

		if (currentToken == null) {
			return; // Safely exit if no tokens are left
		}
	
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
		} else if (currentToken.tokenIdentifier.equals("RIGHT_CURLY_BRACKET")) {
			// Safely return on block end
			return;
		} else {
			throw new RuntimeException("Unexpected token: " + currentToken.tokenIdentifier);
		}
	}
	

	public void parseStatements() {
		while (currentToken != null && !currentToken.tokenIdentifier.equals("RIGHT_CURLY_BRACKET")) {
			parseStatement();
		}
	}
	

	public void parseBranch() {

		match("IF_KEYWORD");

		advance();

		match("LEFT_PARANTHESIS");

		advance();
		int destCount = parseCond();
		String condLabel = "L" + destCount;

		match("RIGHT_PARANTHESIS");

		advance();

		match("LEFT_CURLY_BRACKET");

		advance();

		parseStatement();

		match("RIGHT_CURLY_BRACKET");
		String endLabel = "L" + (destCount + 1);
		atoms.add("(JMP,,,,," + endLabel + ")");
		atoms.add("(LBL,,,,," + condLabel + ")");
		parseElse();

		atoms.add("(LBL,,,,," + endLabel + ")");

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

	public int parseCond() {

		String operator;
		String leftOp;
		String rightOp;
		int destCount = ++destinationLabel;
		String destLbl = "L" + destCount;

		if (currentToken.tokenIdentifier.equals("IDENTIFIER") || currentToken.tokenIdentifier.equals("INT_LITERAL")
				|| currentToken.tokenIdentifier.equals("FLOAT_LITERAL")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			leftOp = currentToken.data;

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

			if (currentToken.tokenIdentifier.equals("IS_EQUAL_OPERATOR")) {
				operator = "6";
			} else if (currentToken.tokenIdentifier.equals("NOT_EQUAL_OPERATOR")) {
				operator = "1";
			} else if (currentToken.tokenIdentifier.equals("LESS_THAN_OPERATOR")) {
				operator = "5";
			} else if (currentToken.tokenIdentifier.equals("GREATER_THAN_OPERATOR")) {
				operator = "4";
			} else if (currentToken.tokenIdentifier.equals("LESS_THAN_OR_EQUAL_OPERATOR")) {
				operator = "3";
			} else {
				operator = "2";
			}

			advance();

		} else {

			throw new RuntimeException("Error: Expected comparison operator but got " + currentToken.tokenIdentifier);

		}

		if (currentToken.tokenIdentifier.equals("IDENTIFIER") || currentToken.tokenIdentifier.equals("INT_LITERAL")
				|| currentToken.tokenIdentifier.equals("FLOAT_LITERAL")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			rightOp = currentToken.data;

			advance();

		} else {

			throw new RuntimeException("Error: Expected expression but got " + currentToken.tokenIdentifier);

		}

		String atomString = "(TST," + leftOp + "," + rightOp + ",," + operator + "," + destLbl + ")";
		atoms.add(atomString);

		return destCount;

	}

	public void parseFor() {

		String counterVar;
		String start;
		String end;
		int destCount = ++destinationLabel;
		String topLabel = "L" + destCount;
		destCount = ++destinationLabel;
		String endLabel = "L" + destCount;

		match("FOR_KEYWORD");

		advance();

		match("LEFT_PARANTHESIS");

		advance();

		match("IDENTIFIER");

		counterVar = currentToken.data;

		advance();

		match("IN_KEYWORD");

		advance();

		// TODO: Syntax is wierd
		match("INT_LITERAL");

		start = currentToken.data;

		advance();

		match("INT_LITERAL");

		end = currentToken.data;

		advance();

		match("RIGHT_PARANTHESIS");

		advance();

		match("LEFT_CURLY_BRACKET");

		advance();

		String atomString = "(MOV, " + start + ",,,," + counterVar + ")";
		atoms.add(atomString);

		atomString = "(LBL,,,,," + topLabel + ")";
		atoms.add(atomString);

		atomString = "(TST," + counterVar + ", " + end + ",, " + 5 + ", " + endLabel + ")";
		atoms.add(atomString);

		parseStatement();

		atomString = "(ADD, " + counterVar + ", " + 1 + ", " + counterVar + ")";
		atoms.add(atomString);

		atomString = "(JMP,,,,," + topLabel + ")";
		atoms.add(atomString);

		atomString = "(LBL,,,,," + endLabel + ")";
		atoms.add(atomString);

		match("RIGHT_CURLY_BRACKET");

		advance();

	}

	public void parseWhile() {

		String counterVar;
		int destCount = ++destinationLabel;
		String topLabel = "L" + destCount;

		match("WHILE_KEYWORD");

		advance();

		match("LEFT_PARANTHESIS");

		advance();

		counterVar = currentToken.data;

		String atomString = "(LBL,,,,," + topLabel + ")";
		atoms.add(atomString);

		int returnLbl = parseCond();

		match("RIGHT_PARANTHESIS");

		advance();

		match("LEFT_CURLY_BRACKET");

		advance();

		parseStatement();

		String endLabel = "L" + returnLbl;
		atomString = "(JMP,,,,," + topLabel + ")";
		atoms.add(atomString);

		atomString = "(LBL,,,,," + endLabel + ")";
		atoms.add(atomString);

		match("RIGHT_CURLY_BRACKET");

	}

	public Token match(String expectedToken) {

		if (currentToken.tokenIdentifier.equals(expectedToken)) {

			System.out.println("Matched: " + expectedToken + " --> " + currentToken.data);
			return currentToken;

		} else {

			System.out.println("Error: Expected " + expectedToken + " but got " + currentToken.tokenIdentifier);
			System.exit(0);
			return null;
		}

	}

	public void matchIdentifier() {

	}

	public void matchIntLiteral() {

	}

	public String createExprAtoms(String OoOp) {
		int tempsNeeded = 0;
	
		for (int i = 0; i < OoOp.length(); i++) {
			if (OoOp.charAt(i) == ')') {
				tempsNeeded++;
			}
		}
	
		for (int p = 0; p < tempsNeeded; p++) {
			int end = 0;
			int start = 0;
	
			for (int i = 0; i < OoOp.length(); i++) {
				if (OoOp.charAt(i) == ')') {
					end = i;
					break;
				}
			}
	
			String tempReg = ""; 
			
			for (int i = end; i > 0; i--) {
				if (OoOp.charAt(i) == '(') {
					start = i;
					break;
				}
			}
	
			String atom = OoOp.substring(start + 1, end); // Remove parentheses
			atom += "," + tempReg + ")";
			String temp = OoOp.substring(0, start) + tempReg + OoOp.substring(end + 1);
			OoOp = temp;
	
			atoms.add("(" + atom); // Add atom in correct format
		}
	
		return "T" + (tempValue - 1); // Correctly return the last temp variable
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

	// public static void main(String[] args) {

	// 	// Test to read in txt file
	// 	Queue<Token> tokensTest = new LinkedList<>();
	// 	String filename = "phase2/ifTest.txt";
	// 	String[] fileLines = readFileToArray(filename);

	// 	// for (String line : fileLines) {
	// 	// 	String[] lineTokens = line.split(" ");
	// 	// 	String identifier = lineTokens[0].trim();
	// 	// 	String data = lineTokens[1].trim();
	// 	// 	tokensTest.add(new Token(identifier, data));
	// 	// }
	// 	for (String line : fileLines) {
	// 		String[] lineTokens = line.split(" ");
	// 		String identifier = lineTokens[0].trim();
	// 		String data;
		
	// 		// If the line contains data, use it
	// 		if (lineTokens.length > 1) {
	// 			data = lineTokens[1].trim();
	// 		} else {
	// 			// Automatically fill in data for known single-token types
	// 			switch (identifier) {
	// 				case "ADDITION_OPERATOR":
	// 					data = "+";
	// 					break;
	// 				case "SUBTRACTION_OPERATOR":
	// 					data = "-";
	// 					break;
	// 				case "MULTIPLICATION_OPERATOR":
	// 					data = "*";
	// 					break;
	// 				case "DIVISION_OPERATOR":
	// 					data = "/";
	// 					break;
	// 				case "LEFT_PARANTHESIS":
	// 					data = "(";
	// 					break;
	// 				case "RIGHT_PARANTHESIS":
	// 					data = ")";
	// 					break;
	// 				case "LEFT_CURLY_BRACKET":
	// 					data = "{";
	// 					break;
	// 				case "RIGHT_CURLY_BRACKET":
	// 					data = "}";
	// 					break;
	// 				default:
	// 					data = ""; // Default to empty if no specific data is required
	// 			}
	// 		}
	// 		tokensTest.add(new Token(identifier, data));
	// 	}
	// 	System.out.println();
	// 	// System.out.println("Token Test: " + tokensTest);
	// 	System.out.println();

	// 	// Test for while loop
	// 	Queue<Token> whileTest = new LinkedList<>();
	// 	whileTest.add(new Token("WHILE_KEYWORD", "while"));
	// 	whileTest.add(new Token("LEFT_PARANTHESIS", "("));
	// 	whileTest.add(new Token("IDENTIFIER", "y"));
	// 	whileTest.add(new Token("GREATER_THAN_OPERATOR", ">"));
	// 	whileTest.add(new Token("INT_LITERAL", "5"));
	// 	whileTest.add(new Token("RIGHT_PARANTHESIS", ")"));
	// 	whileTest.add(new Token("LEFT_CURLY_BRACKET", "{"));

	// 	whileTest.add(new Token("LET_KEYWORD", "let"));
	// 	whileTest.add(new Token("IDENTIFIER", "id"));
	// 	whileTest.add(new Token("COLON", ":"));
	// 	whileTest.add(new Token("MUT_KEYWORD", "mut"));
	// 	whileTest.add(new Token("I32_KEYWORD", "i32"));
	// 	whileTest.add(new Token("ASSIGNMENT_OPERATOR", "="));

	// 	whileTest.add(new Token("IDENTIFIER", "y"));
	// 	whileTest.add(new Token("MULTIPLICATION_OPERATOR", "*"));
	// 	whileTest.add(new Token("LEFT_PARANTHESIS", "("));
	// 	whileTest.add(new Token("IDENTIFIER", "x"));
	// 	whileTest.add(new Token("ADDITION_OPERATOR", "+"));
	// 	whileTest.add(new Token("IDENTIFIER", "y"));
	// 	whileTest.add(new Token("DIVISION_OPERATOR", "/"));
	// 	whileTest.add(new Token("IDENTIFIER", "z"));

	// 	whileTest.add(new Token("ADDITION_OPERATOR", "+"));
	// 	whileTest.add(new Token("IDENTIFIER", "i"));
	// 	whileTest.add(new Token("RIGHT_PARANTHESIS", ")"));
	// 	whileTest.add(new Token("SEMICOLON", ";"));

	// 	whileTest.add(new Token("RIGHT_CURLY_BRACKET", "}"));

	// 	// Test for for loop
	// 	Queue<Token> forTest = new LinkedList<>();
	// 	// forTest.add(new Token("IDENTIFIER", "a"));
	// 	// forTest.add(new Token("ASSIGNMENT_OPERATOR", "="));
	// 	// forTest.add(new Token("IDENTIFIER", "a"));
	// 	// forTest.add(new Token("ADDITION_OPERATOR", "+"));
	// 	// forTest.add(new Token("IDENTIFIER", "b"));
	// 	// forTest.add(new Token("SUBTRACTION_OPERATOR", "-"));
	// 	// forTest.add(new Token("IDENTIFIER", "c"));

	// 	// forTest.add(new Token("FOR_KEYWORD", "for"));
	// 	// forTest.add(new Token("LEFT_PARANTHESIS", "("));
	// 	// forTest.add(new Token("IDENTIFIER", "y"));
	// 	// forTest.add(new Token("IN_KEYWORD", "in"));
	// 	// forTest.add(new Token("INT_LITERAL", "5"));
	// 	// forTest.add(new Token("INT_LITERAL", "15"));
	// 	// forTest.add(new Token("RIGHT_PARANTHESIS", ")"));
	// 	// forTest.add(new Token("LEFT_CURLY_BRACKET", "{"));

	// 	// forTest.add(new Token("LET_KEYWORD", "let"));
	// 	// forTest.add(new Token("IDENTIFIER", "id"));
	// 	// forTest.add(new Token("COLON", ":"));
	// 	// forTest.add(new Token("MUT_KEYWORD", "mut"));
	// 	// forTest.add(new Token("I32_KEYWORD", "i32"));
	// 	// forTest.add(new Token("ASSIGNMENT_OPERATOR", "="));

	// 	// forTest.add(new Token("IDENTIFIER", "y"));
	// 	// forTest.add(new Token("MULTIPLICATION_OPERATOR", "*"));
	// 	// forTest.add(new Token("LEFT_PARANTHESIS", "("));
	// 	// forTest.add(new Token("IDENTIFIER", "x"));
	// 	// forTest.add(new Token("ADDITION_OPERATOR", "+"));
	// 	// forTest.add(new Token("IDENTIFIER", "y"));
	// 	// forTest.add(new Token("DIVISION_OPERATOR", "/"));
	// 	// forTest.add(new Token("IDENTIFIER", "z"));

	// 	// forTest.add(new Token("ADDITION_OPERATOR", "+"));
	// 	// forTest.add(new Token("IDENTIFIER", "i"));
	// 	// forTest.add(new Token("RIGHT_PARANTHESIS", ")"));
	// 	// forTest.add(new Token("SEMICOLON", ";"));

	// 	// forTest.add(new Token("RIGHT_CURLY_BRACKET", "}"));

	// 	System.out.println("");
	// 	System.out.println("/////////////////////////////////////////////");
	// 	System.out.println("		PARSER");
	// 	System.out.println("/////////////////////////////////////////////");
	// 	System.out.println("");

	// 	Parser parser = new Parser(forTest);
	// 	parser.parse();

	// 	System.out.println("");
	// 	System.out.println("/////////////////////////////////////////////");
	// 	System.out.println("		ATOMS");
	// 	System.out.println("/////////////////////////////////////////////");
	// 	System.out.println("");

	// 	while (!atoms.isEmpty()) {
	// 		System.out.println(atoms.remove());
	// 	}

	// }

	public static void main(String[] args) {

		// Queue to hold tokens generated from the file
		Queue<Token> tokensTest = new LinkedList<>();
	
		// Read the file
		String filename = "scannerOut.txt";
		String[] fileLines = readFileToArray(filename);
	
		// Process each line and add tokens to the queue
		for (String line : fileLines) {
			String[] lineTokens = line.split(":", 2); // Split only at the first colon
			String identifier = lineTokens[0].trim();
			String data = (lineTokens.length > 1) ? lineTokens[1].trim() : "";
			tokensTest.add(new Token(identifier, data));		
	
			// // If the line contains data, use it
			// if (lineTokens.length > 1) {
			// 	data = lineTokens[1].trim();
			// } else {
			// 	// Automatically fill in data for known single-token types
			// 	switch (identifier) {
			// 		case "ADDITION_OPERATOR":
			// 			data = "+";
			// 			break;
			// 		case "SUBTRACTION_OPERATOR":
			// 			data = "-";
			// 			break;
			// 		case "MULTIPLICATION_OPERATOR":
			// 			data = "*";
			// 			break;
			// 		case "DIVISION_OPERATOR":
			// 			data = "/";
			// 			break;
			// 		case "LEFT_PARANTHESIS":
			// 			data = "(";
			// 			break;
			// 		case "RIGHT_PARANTHESIS":
			// 			data = ")";
			// 			break;
			// 		case "LEFT_CURLY_BRACKET":
			// 			data = "{";
			// 			break;
			// 		case "RIGHT_CURLY_BRACKET":
			// 			data = "}";
			// 			break;
			// 		default:
			// 			data = ""; // Default to empty if no specific data is required
			// 	}
			// }
			// tokensTest.add(new Token(identifier, data));
		}
	
		// Pass the tokens queue to the parser
		System.out.println("");
		System.out.println("/////////////////////////////////////////////");
		System.out.println("		PARSER");
		System.out.println("/////////////////////////////////////////////");
		System.out.println("");
	
		Parser parser = new Parser(tokensTest); // Use tokensTest from the file
		parser.parse();
	
		System.out.println("");
		System.out.println("/////////////////////////////////////////////");
		System.out.println("		ATOMS");
		System.out.println("/////////////////////////////////////////////");
		System.out.println("");
		try {
			File output = new File("atoms.txt");
			BufferedWriter w = new BufferedWriter(new FileWriter(output));

			while (!atoms.isEmpty()) {
				String a = atoms.remove();
				System.out.println(a);
				w.write(a);
			}
			w.close();
		}catch(IOException e){
			e.printStackTrace();
		}	
	}
	
}
