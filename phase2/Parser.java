
/**
 * -----------------------------------------------------------------------------
 * Project Name: Parser (Phase 2)
 * File Name: [Parser.java]
 * 
 * Description:
 * 
 * -----------------------------------------------------------------------------
 * Authors: Jordan Dennison, Riley Potter, Matt Chehovin
 * Reviewers: Jee McCloud, Ethan Glenn
 * Date Created:  16OCT24
 * Date Last Modified:  2024
 * **/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Iterator;

public class Parser {

	static class Token {

		String tokenIdentifier;
		String data;

		public Token(String tokenIdentifier, String data) {
			this.tokenIdentifier = tokenIdentifier;
			this.data = data;
		}

		public String toString() {
			return tokenIdentifier + " Data:  " + data;
		}
	}

	Queue<Token> tokens;
	Queue<Queue<String>> atoms = new LinkedList<Queue<String>>();
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

	private void parseDecl() {

		match("LET_KEYWORD");

		advance();

		match("IDENTIFIER");

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

			parseExpr();

			match("SEMICOLON");

		} else {
			throw new RuntimeException("Error: Expected type keyword but got " + currentToken.tokenIdentifier);
		}

	}

	public void parseAssign() {

		Queue<String> atom = new LinkedList<String>();

		match("IDENTIFIER");

		advance();

		match("ASSIGNMENT_OPERATOR");

		advance();

		parseExpr();

		match("SEMICOLON");

		atoms.add(atom);

	}

	public void parseBase() {

		if (currentToken.tokenIdentifier.equals("INT_LITERAL") || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
				|| currentToken.tokenIdentifier.equals("IDENTIFIER")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

		} else if (currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {

			match("LEFT_PARANTHESIS");

			advance();

			parseExpr();

			match("RIGHT_PARANTHESIS");

		}

		else {

			throw new RuntimeException(
					"Error: Expected int literal or identifier but got " + currentToken.tokenIdentifier);

		}

	}

	public void parseExpr() {

		// TODO: Should be calling ParseAssign() at some point, idk where
		// parseAssign();

		parseMulDiv();

		parseExprTail();

	}

	public void parseMulDiv() {

		parseBase();

		parseMulDivTail();

	}

	public void parseMulDivTail() {

		if (currentToken.tokenIdentifier.equals("MULTIPLICATION_OPERATOR")
				|| currentToken.tokenIdentifier.equals("DIVISION_OPERATOR")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			parseBase();

			parseMulDivTail();

		}

	}

	public void parseExprTail() {

		if (currentToken.tokenIdentifier.equals("ADDITION_OPERATOR")
				|| currentToken.tokenIdentifier.equals("SUBTRACTION_OPERATOR")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			parseMulDiv();

			parseExprTail();

		}

	}

	public void parseStatement() {

		if (currentToken.tokenIdentifier.equals("IF_KEYWORD")) {

			parseBranch();

		} else if (currentToken.tokenIdentifier.equals("IDENTIFIER")
				|| currentToken.tokenIdentifier.equals("INT_LITERAL")
				|| currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
				|| currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {

			parseExpr();

			match("SEMICOLON");

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

		parseExpr();

		if (currentToken.tokenIdentifier.equals("IS_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("NOT_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("LESS_THAN_OPERATOR")
				|| currentToken.tokenIdentifier.equals("GREATER_THAN_OPERATOR")
				|| currentToken.tokenIdentifier.equals("LESS_THAN_OR_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("GREATHER_THAN_OR_EQUAL_OPERATOR")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			parseExpr();

		} else {

			throw new RuntimeException("Error: Expected comparison operator but got " + currentToken.tokenIdentifier);

		}

	}

	public void parseFor() {

		match("FOR_KEYWORD");

		advance();

		match("LEFT_PARANTHESIS");

		advance();

		parseExpr();

		match("IN_KEYWORD");

		advance();

		parseExpr();

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

	public void match(String expectedToken) {

		if (currentToken.tokenIdentifier.equals(expectedToken)) {

			System.out.println("Matched: " + expectedToken + " --> " + currentToken.data);

		} else {

			System.out.println("Error: Expected " + expectedToken + " but got " + currentToken.tokenIdentifier);

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
		String filename = "phase2/output.txt";
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

		Queue<Token> letTest = new LinkedList<>();

		// Populate tokens for testing (add sample tokens here)
		letTest.add(new Token("LET_KEYWORD", "let"));
		letTest.add(new Token("IDENTIFIER", "id"));
		letTest.add(new Token("COLON", ":"));
		letTest.add(new Token("MUT_KEYWORD", "mut"));
		letTest.add(new Token("I32_KEYWORD", "i32"));
		letTest.add(new Token("ASSIGNMENT_OPERATOR", "="));
		letTest.add(new Token("IDENTIFIER", "x"));
		letTest.add(new Token("SEMICOLON", ";"));

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
	}

}
