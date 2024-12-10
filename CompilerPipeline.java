import phase3.*;
import phase2.*;
import phase1.*;

import java.util.Scanner;
import java.io.File;

class CompilerPipeline {


	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter input file: ");
			String inputFile = sc.nextLine();
			File f = new File(inputFile);
			if(f.exists() && !f.isDirectory()) {
				break;
			}else {
				System.out.println("File does not exist. Please Try again: ");
			}

		}
		System.out.println("Enter output file: ");
		String outputFile = sc.nextLine();
	}
}
