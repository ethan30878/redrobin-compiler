package phase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Compiler {

    // Flag for cmp, false by default
    private static boolean flag = false;

    /**
     * Author: Ethan Glenn
     * @param reg register to be cleared
     * @return binary instruction
     */
    public static String clrConv(String reg) {
        String opcode = "0101";
        String cmp = "0000"; 
        String addr = "00000000000000000000"; 
        return opcode + cmp + reg + addr;
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

    /**
     * Author: Ethan Glenn
     * @param input atom string to be converted
     * @return binary instruction
     */
    public static String jmpConv(String input) {
        String opcode = "0101";

        // May be unnecessary here, Reaser explained it like these are always all 0s
        String cmp = "0000"; // Unconditional JMP case (default)
        String reg = "0000";

        String lbl = input.substring(9, 10);
        String addr = "ADDRESS_OF__" + lbl + "__HERE"; // 20-char placeholder 

        return opcode + cmp + reg + addr;
    }

    /**
     * Author: Ethan Glenn
     * @param reg_contents stuff in register
     * @param addr_contents stuff in memory
     * @return binary instruction
     */
    public static String cmpConv(String reg_contents, String addr_contents) {
        String opcode = "0110";
        String cmp = "0";

        // ???
        int cmp_val = reg_contents.compareTo(addr_contents);

        if (cmp_val > 0) { // cmp <- 3
            cmp += "011";
        } else if (cmp_val < 0 ) { // cmp <- 2
            cmp += "010";
        } else { // cmp <- 1
            cmp += "001";
        }
        
        if (cmp != null)
            flag = true;

        return opcode + cmp + reg_contents + addr_contents;
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
        ArrayList<String> binOut = new ArrayList<String>();

        for (String line : fileLines) {
            atoms.add(line);
        }

        for (String atom : atoms) {

            // TODO: Convert atoms to their respective instructions
            // ex: MOV, TST, LBL (?), CMP are not atoms, but instructions

            if (atom.substring(1, 4).equals("CLR")) {
                binOut.add(clrConv(atom));
            } else if (atom.substring(1, 4).equals("ADD")) {
                binOut.add(addConv(atom));
            } else if (atom.substring(1, 4).equals("SUB")) {
                binOut.add(subConv(atom));
            } else if (atom.substring(1, 4).equals("MUL")) {
                binOut.add(mulConv(atom));
            } else if (atom.substring(1, 4).equals("DIV")) {
                binOut.add(divConv(atom));
            } else if (atom.substring(1, 4).equals("JMP")) {
                binOut.add(jmpConv(atom));
            } else if (atom.substring(1, 4).equals("CMP")) {
                binOut.add(cmpConv(atom));
            } else if (atom.substring(1, 4).equals("LOD")) {
                binOut.add(lodConv(atom));
            } else if (atom.substring(1, 4).equals("STO")) {
                binOut.add(stoConv(atom));
            } else if (atom.substring(1, 4).equals("HLT")) {
                binOut.add(hltConv(atom));
            }
        }

    }
}
