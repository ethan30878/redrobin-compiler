package phase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Compiler {

    // Flag for cmp, false by default
    private static boolean flag = false;
    public static int regVal = 0;

    /**
     * Author: Ethan Glenn
     * 
     * @param reg register to be cleared
     * @return binary instruction
     */
    public static String clrConv(String reg) {
        String opcode = "0101";
        String cmp = "0000";
        String addr = "00000000000000000000";
        return opcode + cmp + reg + addr;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String addConv(String input) {

        String returnString = "";
        String mem1 = "[ default mem 1 ]";
        String mem2 = "[ default mem 2 ]";
        String r = "[ 0000 ]";

        String opcodeADD = "[ 0001 ]";
        String opcodeSTO = "[ 1000 ]";
        String cmp = "[ 0000 ]";

        String[] splitInput = input.split(",");

        System.out.println("splitInput1: " + splitInput[1]);
        System.out.println("splitInput2: " + splitInput[2]);
        System.out.println("splitInput3: " + splitInput[3].substring(0, splitInput[3].length() - 1));
        System.out.println();

        // Check the memory location of the first value
        if (splitInput[1].startsWith("t")) {

            returnString += "{STORE} Load in Mem1 Register Value to Memory:\n" + opcodeSTO + cmp
                    + "[ Register for Mem1 ]"
                    + "[ Value at Mem1 ]" + "\n";

            mem1 = "[ Register of Mem1: " + splitInput[1] + " ]";

        } else {

            mem1 = "[ Value of: " + splitInput[1] + " ]";

        }

        if (splitInput[3].startsWith("t")) {

            r = "[ Register to Store In: " + splitInput[3].substring(0, splitInput[3].length() - 1) + " ]";

        } else {

            r = "[ Not a Valid Register ]";

        }

        if (!splitInput[3].substring(0, splitInput[3].length() - 1).equals(splitInput[2])) {

            returnString += "{STORE} Load in Mem2 Register Value to Memory:\n" + opcodeSTO + cmp
                    + "[ Register for Mem2 ]"
                    + "[ Value at Mem2 ]" + "\n";

        } else {

            returnString += "{ADD} Add Values:\n" + opcodeADD + cmp + mem2 + mem1 + "\n";
        }

        return returnString;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String subConv(String input) {
        String mem1 = "00000000000000000000";
        String mem2 = "00000000000000000000";
        String r = "0000";

        String opcode = "[ 0010 ]";
        String cmp = "[ 0000 ]";

        String[] splitInput = input.split(",");

        if (splitInput[1].startsWith("t")) {

            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))) + " ]";
        } else if (splitInput[1].matches("\\d+")) {
            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))) + " ]";

        } else {
            r = "[ Address of " + splitInput[1] + " ]";
        }

        if (splitInput[2].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))) + " ]";

        } else if (splitInput[2].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))) + " ]";
        } else {
            mem1 = "[ address of " + splitInput[2] + " ]";
        }

        if (splitInput[3].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))) + " ]";

        } else if (splitInput[3].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))) + " ]";
        } else {
            mem2 = "[ address of " + splitInput[3].substring(0, splitInput[3].length() - 1) + " ]";
        }

        return opcode + cmp + r + mem2 + "\n" + opcode + cmp + mem1 + mem2;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String mulConv(String input) {
        String mem1 = "00000000000000000000";
        String mem2 = "00000000000000000000";
        String r = "0000";

        String opcode = "[ 0011 ]";
        String cmp = "[ 0000 ]";

        String[] splitInput = input.split(",");

        if (splitInput[1].startsWith("t")) {

            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))) + " ]";
        } else if (splitInput[1].matches("\\d+")) {
            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))) + " ]";

        } else {
            r = "[ Address of " + splitInput[1] + " ]";
        }

        if (splitInput[2].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))) + " ]";

        } else if (splitInput[2].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))) + " ]";
        } else {
            mem1 = "[ address of " + splitInput[2] + " ]";
        }

        if (splitInput[3].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))) + " ]";

        } else if (splitInput[3].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))) + " ]";
        } else {
            mem2 = "[ address of " + splitInput[3].substring(0, splitInput[3].length() - 1) + " ]";
        }

        return opcode + cmp + r + mem2 + "\n" + opcode + cmp + mem1 + mem2;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String divConv(String input) {
        String mem1 = "00000000000000000000";
        String mem2 = "00000000000000000000";
        String r = "0000";

        String opcode = "[ 0100 ]";
        String cmp = "[ 0000 ]";

        String[] splitInput = input.split(",");

        if (splitInput[1].startsWith("t")) {

            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(1, splitInput[1].length()))) + " ]";
        } else if (splitInput[1].matches("\\d+")) {
            int regLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))).length();

            r = "[ " + mem1.substring(regLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[1].substring(0, splitInput[1].length()))) + " ]";

        } else {
            r = "[ Address of " + splitInput[1] + " ]";
        }

        if (splitInput[2].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(1, splitInput[2].length()))) + " ]";

        } else if (splitInput[2].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))).length();
            mem1 = "[ " + mem1.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[2].substring(0, splitInput[2].length()))) + " ]";
        } else {
            mem1 = "[ address of " + splitInput[2] + " ]";
        }

        if (splitInput[3].startsWith("t")) {

            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(1, splitInput[3].length() - 1))) + " ]";

        } else if (splitInput[3].matches("\\d+")) {
            int addressLength = Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))).length();
            mem2 = "[ " + mem2.substring(addressLength) + Integer
                    .toBinaryString(Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1))) + " ]";
        } else {
            mem2 = "[ address of " + splitInput[3].substring(0, splitInput[3].length() - 1) + " ]";
        }

        return opcode + cmp + r + mem2 + "\n" + opcode + cmp + mem1 + mem2;
    }

    /**
     * Author: Ethan Glenn
     * 
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
     * 
     * @param reg_contents  stuff in register
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
        } else if (cmp_val < 0) { // cmp <- 2
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

    static String decimalToBinary(int num) {

        Stack<Integer> st = new Stack<>();
        String binary = "";

        while (num > 0) {
            st.push(num % 2);
            num = num / 2;
        }

        while (!st.isEmpty()) {
            binary += st.pop().toString();
        }

        return binary;
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
                // binOut.add(clrConv(atom));
                System.out.println("CLR");
            } else if (atom.substring(1, 4).equals("ADD")) {
                // binOut.add(addConv(atom));
                System.out.println(addConv(atom));
            } else if (atom.substring(1, 4).equals("SUB")) {
                // binOut.add(subConv(atom));
                // System.out.println(subConv(atom));
            } else if (atom.substring(1, 4).equals("MUL")) {
                // binOut.add(mulConv(atom));
                // System.out.println(mulConv(atom));
            } else if (atom.substring(1, 4).equals("DIV")) {
                // binOut.add(divConv(atom));
                // System.out.println(divConv(atom));
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
