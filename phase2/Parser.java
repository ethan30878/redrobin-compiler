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


	
    // Let's get parsing
    // test
	
	public static void main (String[] args) {
		class Token {
	        String tokenIdentifier;
	        String Data;
		
		
	        public Token(String tokenIdentifier, String Data) {
	        	this.tokenIdentifier = tokenIdentifier;
	        	this.Data = Data;
	        }
		}
		int index = 0;
		Queue<Token> tokens = new LinkedList<>();
		
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("../phase1/output.txt"));
			String line = reader.readLine();
			//Hardcoding deletion of the first two lines. Fix this later
			line = reader.readLine();
			line = reader.readLine();

			while (line != null) {
				System.out.println(line);
				String tokenIdentifier = line;
				// read next line
				line = reader.readLine();
				String finalData = null;
				if (line != null && line.startsWith("IDENTIFIER Data:")) {
					//Add data to token
					String[] data = line.split("\\:");
					finalData = data[1];
				}
				Token a = new Token(tokenIdentifier, finalData);
				tokens.add(a);
				line = reader.readLine();
				
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator iT = tokens.iterator();
	      System.out.println("Contents of the queue are :");
	      
	      while(iT.hasNext()) {
	         System.out.println(iT.next());  
	      }
//	        public void print() {
//	                if (tokenIdentifier.equals("INT_LITERAL") || tokenIdentifier.equals("FLOAT_LITERAL") || tokenIdentifier.equals("IDENTIFIER")) {
//	                        System.out.println(tokenIdentifier + " Data:  " + Data);
//	                } else {
//	                        System.out.println(tokenIdentifier);
//	                }
//	        }
//		
		//Read output of tokens from Scanner

	
	}
	public void GetTokens(){

	}

	public void Match(){
//		If token we expect equals current token
//			Retrieve next token
//		Else throw syntax error
	}
	public void MatchIdentifier(){

	}

	public void MatchIntLiteral(){

	}
	public String Peek(){
//		Checks if stream has a next token
//		If so then return next token
//		Otherwise we can probably throw an error
		return "asdf";
	}


	public String NewTempValue(){
//		index=0;
//		Increment index everytime function is called
//		Return unique value like t0 using index
		return "asdf";
	}

}
