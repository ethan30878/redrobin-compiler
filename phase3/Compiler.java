package phase3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Compiler {

    // Flag for cmp, false by default
    private static boolean flag = false;
    public static int regVal = 0;
    public static List<List<String>> fixupTable = new ArrayList<>();

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
    public static String movConv(String input) {

        String[] splitInput = input.split(",");

        System.out.println("splitInput1: " + splitInput[1]);
        System.out.println("splitInput2: " + splitInput[2]);
        System.out.println("splitInput3: " + splitInput[3].substring(0, splitInput[3].length() - 1));
        System.out.println();

        return "Mov Temp";
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String addConv(String input) {

        String returnString = "";

        String opcodeADD = "[ 0001 ]";
        String opcodeSTO = "[ 1000 ]";
        String cmp = "[ 0000 ]";

        String[] splitInput = input.split(",");

        // System.out.println("splitInput1: " + splitInput[1]);
        // System.out.println("splitInput2: " + splitInput[2]);
        // System.out.println("splitInput3: " + splitInput[3].substring(0,
        // splitInput[3].length() - 1));
        // System.out.println();

        // Check the first input
        try {
            int input1 = Integer.parseInt(splitInput[1]);
            System.out.println(
                    input1 + " is a valid integer number");

            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

        } catch (NumberFormatException e) {
            System.out.println(
                    splitInput[1] + " is a variable name");

            // Find the address
            for (List<String> row : fixupTable) {
                if (row.get(0).equals(splitInput[1])) {
                    System.out.println("FOUND: " + row);
                }
            }

            // If it is a variable name, we need to check the table for its value
        }

        // Check the second input
        try {
            int input2 = Integer.parseInt(splitInput[2]);
            System.out.println(
                    input2 + " is a valid integer number");

            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

        } catch (NumberFormatException e) {
            System.out.println(
                    splitInput[2] + " is a variable name");

            // Find the address
            for (List<String> row : fixupTable) {
                if (row.get(0).equals(splitInput[2])) {
                    System.out.println("FOUND: " + row);
                }
            }
        }

        // Check the third input
        try {
            int input3 = Integer.parseInt(splitInput[3].substring(0, splitInput[3].length() - 1));
            System.out.println(
                    input3 + " is a valid integer number");

            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

        } catch (NumberFormatException e) {
            System.out.println(
                    splitInput[3].substring(0, splitInput[3].length() - 1) + " is a variable name");

            // If it is a variable name, we need to check the table for its value
            // Find the address
            for (List<String> row : fixupTable) {
                if (row.get(0).equals(splitInput[3].substring(0, splitInput[3].length() - 1))) {
                    System.out.println("FOUND: " + row);
                }
            }

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

        return "NEED TO FIX";
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String mulConv(String input) {
        return "NEED TO FIX";
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String divConv(String input) {
        return "NEED TO FIX";
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

    public static void createFixupTable(ArrayList<String> atoms) {

        int address = 200;

        for (String atom : atoms) {

            String[] splitInput = atom.split(",");

            if (atom.substring(1, 4).equals("LBL")) {

                // Get the label name and address
                String labelName = atom.substring(9, 11);
                String addr = String.valueOf(address);
                address += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(addr);
                fixupTable.add(addresses);
            }

            if (atom.substring(1, 4).equals("MOV")) {

                // Get the label name and address
                String labelName = splitInput[5].substring(0, splitInput[5].length() - 1);
                String addr = String.valueOf(address);
                String value = splitInput[1];
                address += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(value);
                addresses.add(addr);

                fixupTable.add(addresses);
            }
        }
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

        // Create something to store labels and variables
        createFixupTable(atoms);

        System.out.println("Fixup Table:");

        for (List<String> item : fixupTable)
            System.out.println(item);

        System.out.println();

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
            } else if (atom.substring(1, 4).equals("MOV")) {
                // binOut.add(movConv(atom));
                // System.out.println("MOV");
            }
        }

    }
}
