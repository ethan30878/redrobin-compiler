import phase3.*;
import phase2.*;
import phase1.Scanner;
import java.io.*;

//import java.util.Scanner;
import java.io.File;

class CompilerPipeline {


	public static void main(String args[]) {

		if(args.length < 2) {
			System.out.println("Not enough inputs provided. Please see the help menu.");	
			//print help menu
			System.exit(0);
		}		
		File input = new File(args[0]);
		if(!input.exists() || input.isDirectory()) {
			System.out.println("Input file does not exist. Please Try again. ");
			System.exit(0);
		}
		System.out.println(args[0]);
		
		if (args.length >2){
			//Parse additional args
			//

		}
		try {
			String[] inputArg = new String[] {args[0]};
			Scanner.main(inputArg);
			Parser.main(new String[]);
			String[] outputArg = new String[] {args[1]};
			Compiler.main(outputArg);
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
}
