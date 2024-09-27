import java.util.*;

class Scanner {


        public static void main(String args[]) {

                 class Token {
                        String tokenIdentifier;
                        String Data;

                        public Token(String tokenIdentifier, String Data) {
                                this.tokenIdentifier = tokenIdentifier;
                                this.Data = Data;
                        }
                        public void print(){
                                if(tokenIdentifier.equals("INT_LITERAL") || tokenIdentifier.equals("FLOAT_LITERAL") || tokenIdentifier.equals("IDENTIFIER")) {
                                        System.out.println(tokenIdentifier + " Data:  " + Data);
                                } else {
                                        System.out.println(tokenIdentifier);
                                }

                        }
                }
                // Instatiate dictionary and table
                Dictionary<String, Integer> symDictionary = new Hashtable<String, Integer>();
                Dictionary<Integer, String> finalStates = new Hashtable<Integer, String>();
                Queue<Token> tokens = new LinkedList<>();


                // symDictionary.put(" ", 0);
                symDictionary.put("a", 0);
                symDictionary.put("b", 1);
                symDictionary.put("c", 2);
                symDictionary.put("d", 3);
                symDictionary.put("e", 4);
                symDictionary.put("f", 5);
                symDictionary.put("g", 6);
                symDictionary.put("h", 7);
                symDictionary.put("i", 8);
                symDictionary.put("j", 9);
                symDictionary.put("k", 10);
                symDictionary.put("l", 11);
                symDictionary.put("m", 12);
                symDictionary.put("n", 13);
                symDictionary.put("o", 14);
                symDictionary.put("p", 15);
                symDictionary.put("q", 16);
                symDictionary.put("r", 17);
                symDictionary.put("s", 18);
                symDictionary.put("t", 19);
                symDictionary.put("u", 20);
                symDictionary.put("v", 21);
                symDictionary.put("w", 22);
                symDictionary.put("x", 23);
                symDictionary.put("y", 24);
                symDictionary.put("z", 25);
                symDictionary.put("<", 26);
                symDictionary.put(">", 27);
                symDictionary.put("=", 28);
                symDictionary.put("!", 29);
                symDictionary.put("}", 30);
                symDictionary.put("{", 31);
                symDictionary.put("(", 32);
                symDictionary.put(")", 33);
                symDictionary.put("'", 34);
                symDictionary.put(";", 35);
                symDictionary.put(":", 36);
                symDictionary.put("/", 37);
                symDictionary.put("*", 38);
                symDictionary.put("-", 39);
                symDictionary.put("+", 40);
                symDictionary.put("1", 41);
                symDictionary.put("2", 42);
                symDictionary.put("3", 43);
                symDictionary.put("4", 44);
                symDictionary.put("5", 45);
                symDictionary.put("6", 46);
                symDictionary.put("7", 47);
                symDictionary.put("8", 48);
                symDictionary.put("9", 49);
                symDictionary.put("0", 50);
                symDictionary.put(".", 51);
                symDictionary.put(" ", 52);

                // Final State Dictionary

                finalStates.put(4, "IDENTIFIER");
                finalStates.put(46, "IDENTIFIER");
                finalStates.put(1, "IDENTIFIER");

                finalStates.put(6, "IDENTIFIER");
                finalStates.put(7, "IDENTIFIER");
                finalStates.put(8, "IDENTIFIER");

                finalStates.put(12, "IDENTIFIER");
                finalStates.put(13, "IDENTIFIER");
                finalStates.put(48, "IDENTIFIER");
                finalStates.put(10, "IDENTIFIER");

                finalStates.put(50, "INT_LITERAL");
                finalStates.put(51, "FLOAT_LITERAL");

                finalStates.put(15, "IDENTIFIER");
                finalStates.put(16, "IDENTIFIER");
                finalStates.put(17, "IDENTIFIER");
                finalStates.put(18, "IDENTIFIER");

                finalStates.put(20, "IDENTIFIER");
                finalStates.put(21, "IDENTIFIER");

                finalStates.put(23, "IDENTIFIER");
                finalStates.put(24, "IDENTIFIER");




                // GENERAL SYMBOLS
                finalStates.put(26, "RIGHT_CURLY_BRACKET");
                finalStates.put(27, "LEFT_CURLY_BRACKET");
                finalStates.put(28, "RIGHT_PARANTHESIS");
                finalStates.put(29, "LEFT_PARANTHESIS");
                finalStates.put(45, "IDENTIFIER");

                finalStates.put(31, "ASPOSTROPHE");
                finalStates.put(32, "SEMICOLON");


                // MATH SYMBOLS
                finalStates.put(33, "DIVISION_OPERATOR");
                finalStates.put(34, "MULTIPLICATION_OPERATOR");
                finalStates.put(35, "SUBTRACTION_OPERATOR");
                finalStates.put(36, "ADDITON_OPERATOR");

                // COMPARISON OPERATORS
                finalStates.put(43, "LESS_THAN_OPERATOR");
                finalStates.put(44, "LESS_THAN_OR_EQUAL_OPERATOR");
                finalStates.put(41, "GREATHER_THAN_OPERATOR");
                finalStates.put(42, "GREATHER_THAN_OR_EQUAL_OPERATOR");
                finalStates.put(40, "NOT_EQUAL_OPERATOR");
                finalStates.put(37, "ASSIGNMENT_OPERATOR");
                finalStates.put(38, "IS_EQUAL_OPERATOR");

                // KEYWORDS
                finalStates.put(5, "IF_KEYWORD");
                finalStates.put(9, "ELSE_KEYWORD");
                finalStates.put(19, "WHILE_KEYWORD");
                finalStates.put(14, "FOR_KEYWORD");
                finalStates.put(22, "LET_KEYWORD");
                finalStates.put(3, "IN_KEYWORD");
                finalStates.put(25, "MUT_KEYWORD");
                finalStates.put(2, "i32_KEYWORD");
                finalStates.put(11, "f32_KEYWORD");
                finalStates.put(47, "i64_KEYWORD");
                finalStates.put(49, "f64_KEYWORD");
                int[][] transitionTable = {

                        //from state 0
{ 45, 45, 45, 45, 6, 12, 45, 45, 4, 45,
        45, 20, 23, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 15, 45, 45, 45, 43, 41, 37, 39,
        26, 27, 28, 29, 31, 32, 99, 33, 34, 35,
        36, 50, 50, 50, 50, 50, 50, 50, 50, 50,
        50, 99, 99 },
        //from state 1
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 2, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 2
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 3
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 4
        { 45, 45, 45, 45, 45, 5, 45, 45, 45, 45,
        45, 45, 45, 3, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 1, 99, 99, 46, 99, 99, 99,
        99, 99, 99 },
        //from state 5
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 6
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 7, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 7
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 8, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 8
        { 45, 45, 45, 45, 9, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 9
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 10
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 11, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 11
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 12
        { 50, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 13, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 10, 99, 99, 48, 99, 99, 99,
        99, 99, 99 },
        //from state 13
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 14, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 14
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 15
        { 45, 45, 45, 45, 45, 45, 45, 16, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 16
        { 45, 45, 45, 45, 45, 45, 45, 45, 17, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 17
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 18, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 18
        { 45, 45, 45, 45, 19, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 19
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 20
        { 45, 45, 45, 45, 21, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 21
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 22,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 22
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 23
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        24, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 24
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 25,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 25
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 26
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 27
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 28
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 29
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
         //from state 30
         { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
         99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
         99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
         99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
         99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
         99, 99, 99 },
        //from state 31
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 32
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 33
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 34
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 35
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 36
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 37
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 38, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 38
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 39
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 40, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 40
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 41
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 42, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 42
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 43
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 44, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 44
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99 },
        //from state 45
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 46
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 47, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 47
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 48
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 49, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 49
        { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 99, 99 },
        //from state 50
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 50, 50, 50, 50, 50, 50, 50, 50, 50,
        50, 51, 99 },
        //from state 51
        { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 51, 51, 51, 51, 51, 51, 51, 51, 51,
        51, 99, 99 }

                };

                // Printing read in statement
                System.out.println("Enter a statement: ");

                // Read in statement
                String input = System.console().readLine();

                // Instantiate index for the transition table
                int output = 0;
                int currentState = 0;
                String currentChar = "";
                int bookmark = 0;

                String data = "";



                //input = input.replaceAll("\\s", "");

                // Scanner Loop
                for (int i = 0; i < input.length(); i++) {

                        currentChar = String.valueOf(input.charAt(i));

                        int columnIndex = symDictionary.get(currentChar);





                        // Reference transition table
                        output = transitionTable[currentState][columnIndex];



                        if (output == 99){

                                if(finalStates.get(currentState) == null){

                                        if(currentChar.equals(" ")){
                                                currentState = 0;
                                                output = 0;
                                                continue;
                                        }
                                        System.out.println("\n Invalid State Detected");


                                        break;
                                }

                                if(finalStates.get(currentState).equals("INT_LITERAL")|| finalStates.get(currentState).equals("FLOAT_LITERAL")){
                                        if(!(currentChar.equals(" ")|| currentChar.equals(";")|| currentChar.equals(")")|| currentChar.equals("(")|| currentChar.equals("{")|| currentChar.equals("}"))){
                                                System.out.println("\n Invalid State Detected");


                                                break;
                                        }
                                }

                                data = input.substring(bookmark, i);
                                Token toAdd = new Token(finalStates.get(currentState),data);

                                tokens.add(toAdd);

                                bookmark = i;
                                if(currentChar.equals(" ")){
                                        currentState = 0;
                                        output = 0;


                                } else{
                                        currentState = transitionTable[0][symDictionary.get(currentChar)];
                                }
                                continue;
                        }
                        currentState = output;


                }
                if(finalStates.get(currentState) != null) {

                        if(bookmark == 0){
                                data = input;
                        } else {
                                data = input.substring(bookmark).trim();
                        }
                        if((finalStates.get(currentState).equals("INT_LITERAL") || finalStates.get(currentState).equals("FLOAT_LITERAL"))&& symDictionary.get(currentChar) < 26){
                               System.out.println(symDictionary.get(currentChar));
                                if(symDictionary.get(currentChar) < 26) {
                                        System.out.println("\n Invalid State Detected");

                                }
                        } else {

                                Token toAdd = new Token(finalStates.get(currentState), data);
                                tokens.add(toAdd);
                        }
                } else{
                        if(symDictionary.get(currentChar) < 26){
                                System.out.println("\n Invalid State Detected");
                        }

                }
                System.out.println();
                while(!tokens.isEmpty()) {
                        tokens.remove().print();
                }
        }

}