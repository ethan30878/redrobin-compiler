package phase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

        for (String line : fileLines) {
            atoms.add(line);
        }

        for (String atom : atoms) {

            if (atom.substring(1, 4).equals("CLR")) {
                System.out.println("Label Found");
                clrConv(atom);
            } else if (atom.substring(1, 4).equals("ADD")) {
                System.out.println("ADD Found");
                addConv(atom);
            } else if (atom.substring(1, 4).equals("SUB")) {
                subConv(atom);
            } else if (atom.substring(1, 4).equals("MUL")) {
                mulConv(atom);
            } else if (atom.substring(1, 4).equals("DIV")) {
                divConv(atom);
            } else if (atom.substring(1, 4).equals("JMP")) {
                jmpConv(atom);
            } else if (atom.substring(1, 4).equals("CMP")) {
                cmpConv(atom);
            } else if (atom.substring(1, 4).equals("LOD")) {
                lodConv(atom);
            } else if (atom.substring(1, 4).equals("STO")) {
                stoConv(atom);
            } else if (atom.substring(1, 4).equals("HLT")) {
                hltConv(atom);
            }
        }

    }
}
