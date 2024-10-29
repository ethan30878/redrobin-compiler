
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
	private Token currentToken;
	int index = 0;

	public Parser(Queue<Token> tokens) {
		this.tokens = tokens;
		System.out.println("Parser Tokens: " + this.tokens);
	}

	private void advance() {
		currentToken = tokens.poll();
	}

	public void parse() {

		// Get the first token
		currentToken = tokens.poll();

		// Start parsing the expression
		parseExpression();

	}

	private void parseExpression() {

		// While there are still tokens to parse
		while (currentToken != null) {

			// Parse the terms
			if (currentToken.tokenIdentifier.equals("LET_KEYWORD")) {
				System.out.println("LET_KEYWORD");
				advance();
				parseDecl();
				continue;
			}
		}

	}

	private void parseDecl() {

		match("IDENTIFIER");

		advance();

		match("COLON");

		advance();

		if (currentToken.tokenIdentifier.equals("I32_KEYWORD") || currentToken.tokenIdentifier.equals("I64_KEYWORD")
				|| currentToken.tokenIdentifier.equals("F32_KEYWORD")
				|| currentToken.tokenIdentifier.equals("F64_KEYWORD")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier);

			advance();

			match("ASSIGNMENT_OPERATOR");

			advance();

			// parseExpression();

			// advance();

			match("SEMICOLON");

		} else {
			throw new RuntimeException("Error: Expected type keyword but got " + currentToken.tokenIdentifier);
		}

	}

	public void parseAssign() {

		match("IDENTIFIER");

		advance();

		match("ASSIGNMENT_OPERATOR");

		advance();

		// parseExpression();

		// advance();

		match("SEMICOLON");

	}

	public void parseBase() {

		if (currentToken.tokenIdentifier.equals("INT_LITERAL") || currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
				|| currentToken.tokenIdentifier.equals("IDENTIFIER")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			// TODO: There is another else if in the DOC
		} else {

			throw new RuntimeException(
					"Error: Expected int literal or identifier but got " + currentToken.tokenIdentifier);

		}

	}

	// TODO: Need a better name for this method
	public void parseExpr() {

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

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			parseBranch();

		} else if (currentToken.tokenIdentifier.equals("IDENTIFIER")
				|| currentToken.tokenIdentifier.equals("INT_LITERAL")
				|| currentToken.tokenIdentifier.equals("FLOAT_LITERAL")
				|| currentToken.tokenIdentifier.equals("LEFT_PARANTHESIS")) {

			// parseExpr();

			// advance();

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

		// parseExpr();

		// advance();

		if (currentToken.tokenIdentifier.equals("IS_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("NOT_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("LESS_THAN_OPERATOR")
				|| currentToken.tokenIdentifier.equals("GREATER_THAN_OPERATOR")
				|| currentToken.tokenIdentifier.equals("LESS_THAN_OR_EQUAL_OPERATOR")
				|| currentToken.tokenIdentifier.equals("GREATHER_THAN_OR_EQUAL_OPERATOR")) {

			System.out.println("Matched: " + currentToken.tokenIdentifier + " --> " + currentToken.data);

			advance();

			// parseExpr();

			// advance();

		} else {

			throw new RuntimeException("Error: Expected comparison operator but got " + currentToken.tokenIdentifier);

		}

	}

	public void parseFor() {

		match("FOR_KEYWORD");

		advance();

		match("LEFT_PARANTHESIS");

		advance();

		// parseExpr();

		match("IN_KEYWORD");

		advance();

		// parseExpr();

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

		if (currentToken.tokenIdentifier == expectedToken) {

			System.out.println("Matched: " + expectedToken + " --> " + currentToken.data);

		} else {

			System.out.println("Error: Expected " + expectedToken + " but got " + currentToken.tokenIdentifier);

		}

	}

	public void matchIdentifier() {

	}

	public void matchIntLiteral() {

	}

	public static void main(String[] args) {
		Queue<Token> tokens = new LinkedList<>();

		// Populate tokens for testing (add sample tokens here)
		tokens.add(new Token("LET_KEYWORD", "let"));
		tokens.add(new Token("IDENTIFIER", "id"));
		tokens.add(new Token("COLON", ":"));
		tokens.add(new Token("I32_KEYWORD", "i32"));
		tokens.add(new Token("ASSIGNMENT_OPERATOR", "="));
		// tokens.add(new Token("IDENTIFIER", "x"));
		tokens.add(new Token("SEMICOLON", ";"));

		Parser parser = new Parser(tokens);
		parser.parse();
	}

}
