import phase3.*;
import phase2.*;
import phase1.Scanner;
import java.io.*;

//import java.util.Scanner;
import java.io.File;

class CompilerPipeline {


	public static void main(String args[]) {
		boolean noFrontend = false;
		boolean noBackend = false;

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
		
		if (args.length > 2){
			for(int i = 2; i < args.length; i++) {
				if(args[i]=="-nf") {
					noFrontend = true;
				} else if(args[i].equals("-nb")) {
					noBackend = true;
				}
			}

		}
		try {
			String[] inputArg = new String[] {args[0]};
			System.out.println("\nPhase 1 Scanner:\n");	
			Scanner.main(inputArg);
			System.out.println("\nPhase 2 Parser:\n");
			Parser.main(new String[0]);
			if(noBackend==true){
				String[] outputArg = new String[] {args[1],"true"};
				System.out.println("\nPhase 3 Compiler (no Backend Op):\n");
				phase3.Compiler.main(outputArg);
			} else {
				String[] outputArg = new String[] {args[1]};
				System.out.println("\nPhase 3 Compiler:\n");
				phase3.Compiler.main(outputArg);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
}
