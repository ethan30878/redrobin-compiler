import java.util.*;

class Scanner {
    public static void main(String args[]) {

        // Instatiate dictionary and table
        Dictionary<String, String> symDictionary = new Hashtable<String, String>();
        String[][] transitionTable = new String[10][10];

        // VARIABLES
        symDictionary.put("[a-zA-Z_][a-zA-Z0-9_]*", "IDENTIFIER");
        symDictionary.put("[0-9]+", "INTEGER_LITERAL");

        // GENERAL SYMBOLS
        symDictionary.put("}", "RIGHT_CURLY_BRACKET");
        symDictionary.put("{", "LEFT_CURLY_BRACKET");
        symDictionary.put(")", "RIGHT_PARANTHESIS");
        symDictionary.put("(", "LEFT_PARANTHESIS");
        symDictionary.put("\"", "QUOTATION_MARK");
        symDictionary.put("'", "APOSTROPHE");
        symDictionary.put(";", "SEMICOLON");
        symDictionary.put(":", "COLON");

        // MATH SYMBOLS
        symDictionary.put("/", "DIVISION_OPERATOR");
        symDictionary.put("*", "MULTIPLICATION_OPERATOR");
        symDictionary.put("-", "SUBTRACTION_OPERATOR");
        symDictionary.put("+", "ADDITON_OPERATOR");

        // COMPARISON OPERATORS
        symDictionary.put("<", "LESS_THAN_OPERATOR");
        symDictionary.put("<=", "LESS_THAN_OR_EQUAL_OPERATOR");
        symDictionary.put(">", "GREATHER_THAN_OPERATOR");
        symDictionary.put(">=", "GREATHER_THAN_OR_EQUAL_OPERATOR");
        symDictionary.put("!=", "NOT_EQUAL_OPERATOR");
        symDictionary.put("=", "ASSIGNMENT_OPERATOR");
        symDictionary.put("==", "IS_EQUAL_OPERATOR");

        // KEYWORDS
        symDictionary.put("if", "IF_KEYWORD");
        symDictionary.put("else", "ELSE_KEYWORD");
        symDictionary.put("while", "WHILE_KEYWORD");
        symDictionary.put("for", "FOR_KEYWORD");
        symDictionary.put("let", "LET_KEYWORD");
        symDictionary.put("in", "IN_KEYWORD");
        symDictionary.put("mut", "MUT_KEYWORD");
        symDictionary.put("i32", "i32_KEYWORD");
        symDictionary.put("f32", "f32_KEYWORD");
        symDictionary.put("i64", "i64_KEYWORD");
        symDictionary.put("f64", "f64_KEYWORD");

        // Printing read in statement
        System.out.println("Enter a statement: ");

        // Read in statement
        String input = System.console().readLine();

        // Tokenize input
        StringTokenizer tokenizedString = new StringTokenizer(input, " \t\n\r\f+-*/%()[]{};,<>=!&|^~", true);

        // Iterate through tokens
        while (tokenizedString.hasMoreTokens()) {

            // Get next token
            String nextToken = tokenizedString.nextToken();

            // Check if token is in dictionary
            if (symDictionary.get(nextToken) != null) {

                // Print token name
                System.out.println(symDictionary.get(nextToken) + ": " + nextToken);

                continue;

            }

            // Check if token is an identifier
            if (nextToken.matches("[a-z][a-zA-Z0-9_]*")) {
                System.out.println("IDENTIFIER" + ": " + nextToken);
                continue;
            }

            // Check if token is an number
            if (nextToken.matches("[0-9]*")) {
                System.out.println("NUMBER" + ": " + nextToken);
                continue;
            }

            // Ignore whitespace
            if (nextToken.matches("[ \t\n\r\f]+")) {
                continue;
            }

            // Print invalid token
            System.out.println("INVALID_TOKEN" + ": " + nextToken);

        }
    }
}