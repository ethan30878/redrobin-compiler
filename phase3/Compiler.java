package phase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Compiler {

    public static String clrConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String addConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String subConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String mulConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String divConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String jmpConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String cmpConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String lodConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String stoConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String hltConv(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void decimalToBinary(int num) {

        Stack<Integer> st = new Stack<>();
        String binary = "";

        while (num > 0) {

            st.push(num % 2);
            num = num / 2;
        }

        while (!(st.isEmpty())) {

            binary += st.pop().toString();
        }

        System.out.println(binary);
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
        String filename = "phase3/test.txt";
        String[] fileLines = readFileToArray(filename);
        ArrayList<String> atoms = new ArrayList<String>();
        ArrayList<String> binOut = new ArrayList<String>();

        decimalToBinary(45);

        for (String line : fileLines) {
            atoms.add(line);
        }

        for (String atom : atoms) {

            if (atom.substring(1, 4).equals("CLR")) {
                // binOut.add(clrConv(atom));
                System.out.println("CLR");
            } else if (atom.substring(1, 4).equals("ADD")) {
                // binOut.add(addConv(atom));
                System.out.println("ADD");
            } else if (atom.substring(1, 4).equals("SUB")) {
                // binOut.add(subConv(atom));
                System.out.println("SUB");
            } else if (atom.substring(1, 4).equals("MUL")) {
                // binOut.add(mulConv(atom));
                System.out.println("MUL");
            } else if (atom.substring(1, 4).equals("DIV")) {
                // binOut.add(divConv(atom));
                System.out.println("DIV");
            } else if (atom.substring(1, 4).equals("JMP")) {
                // binOut.add(jmpConv(atom));
                System.out.println("JMP");
            } else if (atom.substring(1, 4).equals("CMP")) {
                // binOut.add(cmpConv(atom));
                System.out.println("CMP");
            } else if (atom.substring(1, 4).equals("LOD")) {
                // binOut.add(lodConv(atom));
                System.out.println("LOD");
            } else if (atom.substring(1, 4).equals("STO")) {
                // binOut.add(stoConv(atom));
                System.out.println("STO");
            } else if (atom.substring(1, 4).equals("HLT")) {
                // binOut.add(hltConv(atom));
                System.out.println("HLT");
            }
        }

    }
}
