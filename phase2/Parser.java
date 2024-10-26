package phase2;

public class Parser {
	int index = 0;
    // Let's get parsing
    // test
	class Token {
        String tokenIdentifier;
        String Data;

        public Token(String tokenIdentifier, String Data) {
                this.tokenIdentifier = tokenIdentifier;
                this.Data = Data;
        }

        public void print() {
                if (tokenIdentifier.equals("INT_LITERAL") || tokenIdentifier.equals("FLOAT_LITERAL") || tokenIdentifier.equals("IDENTIFIER")) {
                        System.out.println(tokenIdentifier + " Data:  " + Data);
                } else {
                        System.out.println(tokenIdentifier);
                }
        }
	public static void main (String[] args) {
//		stmt();
		
		
	}
//	tokens[][] = new String[]][];
	
	public void GetToken(){
//		Check to see if our stream is empty
//		Retrieve next token from stream and make current token
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
	}


	public String NewTempValue(){
		index=0;
//		Increment index everytime function is called
//		Return unique value like t0 using index
	}

}
