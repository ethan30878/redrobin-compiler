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

    // Variables for the compiler
    public static int fpreg = 0;
    public static int lblAdress = 100;
    public static List<List<String>> fixupTable = new ArrayList<>();
    public static List<List<String>> labelTable = new ArrayList<>();
    public static List<String> binOut = new ArrayList<>();

    /**
     * Author: Ethan Glenn
     * 
     * @param reg register to be cleared
     * @return binary instruction
     */
    public static String clrConv(String input) {
        String opcode = "0101";
        String cmp = "0000";
        String reg = input.substring(7, 11);
        String addr = input.substring(11, input.length() - 1);
        return opcode + cmp + reg + addr;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static void movConv(String input) {

        String[] splitInput = input.split(",");

        String mem1 = splitInput[5].substring(0, splitInput[5].length() - 1);
        String reg = "";

        String opcode = "1000";
        String cmp = "0000";

        // Find the address
        for (List<String> row : labelTable) {
            if (row.get(0).equals(mem1)) {

                mem1 = row.get(2);

                reg = lodConv(mem1);

            }
        }

        reg = decimalToBinary(Integer.parseInt(reg));

        binOut.add("MOV -> " + opcode + "/" + cmp + "/" + reg + "/" + splitInput[1]);
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String addConv(String input) {

        String opcode = "0001";
        String cmp = "0000";
        String mem1 = "";
        String mem2 = "";
        String reg = "";

        String[] splitInput = input.split(",");

        // Check the first input
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[1]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[1]);
            addresses.add(splitInput[1]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            reg = lodConv(String.valueOf(stoAddress));

        } catch (NumberFormatException e) {

            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[1])) {

                    mem1 = row.get(2);

                    reg = lodConv(mem1);

                }
            }

            // If it is a variable name, we need to check the table for its value
        }

        // Check the second input
        // TODO: IF INPUT IS 0, JUST RETURN
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[2]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[2]);
            addresses.add(splitInput[2]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            mem2 = String.valueOf(stoAddress);

        } catch (NumberFormatException e) {
            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[2])) {

                    mem2 = row.get(2);
                }
            }
        }

        // Make sure storage location is in the table
        String mem3 = "";
        for (List<String> row : labelTable) {
            if (row.get(0).equals(splitInput[3].substring(0, splitInput[3].length() - 1))) {

                mem3 = row.get(2);
            }
        }

        if (mem3.equals("")) {
            // Get the label name and address
            String labelName = splitInput[3].substring(0, splitInput[3].length() - 1);
            String addr = String.valueOf(lblAdress + 100);
            String value = "Result of ADD";
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);
        }

        return "ADD -> " + opcode + "/" + cmp + "/" + reg + "/" + decimalToBinary(Integer.parseInt(mem2));
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String subConv(String input) {

        String opcode = "0010";
        String cmp = "0000";
        String mem1 = "";
        String mem2 = "";
        String reg = "";

        String[] splitInput = input.split(",");

        // Check the first input
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[1]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[1]);
            addresses.add(splitInput[1]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            reg = lodConv(String.valueOf(stoAddress));

        } catch (NumberFormatException e) {
            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[1])) {

                    mem1 = row.get(2);

                    reg = lodConv(mem1);

                }
            }

            // If it is a variable name, we need to check the table for its value
        }

        // Check the second input
        // TODO: IF INPUT IS 0, JUST RETURN
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[2]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[2]);
            addresses.add(splitInput[2]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            mem2 = String.valueOf(stoAddress);

        } catch (NumberFormatException e) {
            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[2])) {

                    mem2 = row.get(2);
                }
            }
        }

        // Make sure storage location is in the table
        String mem3 = "";
        for (List<String> row : labelTable) {
            if (row.get(0).equals(splitInput[3].substring(0, splitInput[3].length() - 1))) {

                mem3 = row.get(2);
            }
        }

        if (mem3.equals("")) {
            // Get the label name and address
            String labelName = splitInput[3].substring(0, splitInput[3].length() - 1);
            String addr = String.valueOf(lblAdress + 100);
            String value = "Result of SUB";
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);
        }

        return "SUB -> " + opcode + "/" + cmp + "/" + reg + "/" + decimalToBinary(Integer.parseInt(mem2));
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String mulConv(String input) {
        String opcode = "0011";
        String cmp = "0000";
        String mem1 = "";
        String mem2 = "";
        String reg = "";

        String[] splitInput = input.split(",");

        // Check the first input
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[1]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[1]);
            addresses.add(splitInput[1]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            reg = lodConv(String.valueOf(stoAddress));

        } catch (NumberFormatException e) {

            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[1])) {

                    mem1 = row.get(2);

                    reg = lodConv(mem1);

                }
            }

            // If it is a variable name, we need to check the table for its value
        }

        // Check the second input
        // TODO: IF INPUT IS 1, JUST RETURN
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[2]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[2]);
            addresses.add(splitInput[2]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            mem2 = String.valueOf(stoAddress);

        } catch (NumberFormatException e) {

            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[2])) {

                    mem2 = row.get(2);
                }
            }
        }

        // Make sure storage location is in the table
        String mem3 = "";
        for (List<String> row : labelTable) {
            if (row.get(0).equals(splitInput[3].substring(0, splitInput[3].length() - 1))) {

                mem3 = row.get(2);
            }
        }

        if (mem3.equals("")) {
            // Get the label name and address
            String labelName = splitInput[3].substring(0, splitInput[3].length() - 1);
            String addr = String.valueOf(lblAdress + 100);
            String value = "Result of MUL";
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);
        }

        return "MUL -> " + opcode + "/" + cmp + "/" + reg + "/" + decimalToBinary(Integer.parseInt(mem2));
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static String divConv(String input) {
        String opcode = "0100";
        String cmp = "0000";
        String mem1 = "";
        String mem2 = "";
        String reg = "";

        String[] splitInput = input.split(",");

        // Check the first input
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[1]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[1]);
            addresses.add(splitInput[1]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            reg = lodConv(String.valueOf(stoAddress));

        } catch (NumberFormatException e) {

            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[1])) {

                    mem1 = row.get(2);

                    reg = lodConv(mem1);

                }
            }

            // If it is a variable name, we need to check the table for its value
        }

        // Check the second input
        // TODO: IF INPUT IS 1, JUST RETURN
        try {
            // If it is an integer, we need to store it in the table?
            // Or convert it straight to binary if that works? (Reaser did not explain)

            Integer.parseInt(splitInput[2]);

            lblAdress += 4;
            List<String> addresses = new ArrayList<>();
            addresses.add(splitInput[2]);
            addresses.add(splitInput[2]);
            int stoAddress = lblAdress + 100;
            addresses.add(String.valueOf(stoAddress));
            labelTable.add(addresses);

            stoConv(String.valueOf(stoAddress));

            mem2 = String.valueOf(stoAddress);

        } catch (NumberFormatException e) {
            // Find the address
            for (List<String> row : labelTable) {
                if (row.get(0).equals(splitInput[2])) {

                    mem2 = row.get(2);
                }
            }
        }

        // Make sure storage location is in the table
        String mem3 = "";
        for (List<String> row : labelTable) {
            if (row.get(0).equals(splitInput[3].substring(0, splitInput[3].length() - 1))) {

                mem3 = row.get(2);
            }
        }

        if (mem3.equals("")) {
            // Get the label name and address
            String labelName = splitInput[3].substring(0, splitInput[3].length() - 1);
            String addr = String.valueOf(lblAdress + 100);
            String value = "Result of DIV";
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);
        }

        return "DIV -> " + opcode + "/" + cmp + "/" + reg + "/" + decimalToBinary(Integer.parseInt(mem2));
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

        return "JMP -> " + opcode + "/" + cmp + "/" + reg + "/" + addr;
    }

    /**
     * Author: Ethan Glenn
     * 
     * @param input
     * @return binary instruction
     */
    public static String cmpConv(String input) {
        String opcode = "0110";
        String cmp = "0000";
        String reg = cmp.substring(7, 11);
        String address = cmp.substring(11, cmp.length() - 1);

        return "CMP -> " + opcode + "/" + cmp + "/" + reg + "/" + address;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param mem memory location to be loaded
     * @return register memory location
     */
    public static String lodConv(String mem) {

        String opcode = "0111";
        String cmp = "0000";

        fpreg += 1;
        String reg = decimalToBinary(fpreg);

        binOut.add("LOD -> " + opcode + "/" + cmp + "/" + reg + "/" + decimalToBinary(Integer.parseInt(mem)));

        return reg;
    }

    /**
     * Author: Jordan Dennison
     * 
     * @param mem memory location to be stored
     */
    public static void stoConv(String mem) {

        String opcode = "1000";
        String cmp = "0000";

        fpreg += 1;
        String reg = decimalToBinary(fpreg);

        binOut.add("STO -> " + opcode + "/" + cmp + "/" + reg + "/" + mem);

    }

    public static String hltConv(String input) {
        // Should just hault processor
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

        for (String atom : atoms) {

            String[] splitInput = atom.split(",");

            if (atom.substring(1, 4).equals("LBL")) {

                // Get the label name and address
                String labelName = splitInput[5].substring(0, splitInput[5].length() - 1);
                String addr = String.valueOf(lblAdress);
                lblAdress += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(addr);
                labelTable.add(addresses);

            } else if (atom.substring(1, 4).equals("MOV")) {

                // Get the label name and address
                String labelName = splitInput[5].substring(0, splitInput[5].length() - 1);
                String addr = String.valueOf(lblAdress + 100);
                String value = splitInput[1];
                lblAdress += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(value);
                addresses.add(addr);

                labelTable.add(addresses);

            } else if (atom.substring(1, 4).equals("TST")) {

                // Get the label name and address
                String labelName = splitInput[5].substring(0, splitInput[5].length() - 1);
                String addr = String.valueOf(lblAdress);
                lblAdress += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(addr);

                fixupTable.add(addresses);

            } else if (atom.substring(1, 4).equals("JMP")) {

                // Get the label name and address
                String labelName = splitInput[5].substring(0, splitInput[5].length() - 1);
                String addr = String.valueOf(lblAdress);
                lblAdress += 4;

                // Add into the array
                List<String> addresses = new ArrayList<>();
                addresses.add(labelName);
                addresses.add(addr);

                fixupTable.add(addresses);

            } else {

                lblAdress += 4;

            }
        }
    }

    public static void main(String[] args) {

        // Test to read in txt file
        String filename = "phase3/test.txt";
        String[] fileLines = readFileToArray(filename);
        ArrayList<String> atoms = new ArrayList<String>();

        for (String line : fileLines) {
            atoms.add(line);
        }

        // Create something to store labels and variables
        createFixupTable(atoms);

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
                // binOut.add(cmpConv(atom));
            } else if (atom.substring(1, 4).equals("LOD")) {
                binOut.add(lodConv(atom));
            } else if (atom.substring(1, 4).equals("STO")) {
                // binOut.add(stoConv(atom));
            } else if (atom.substring(1, 4).equals("HLT")) {
                binOut.add(hltConv(atom));
            } else if (atom.substring(1, 4).equals("MOV")) {
                movConv(atom);
            } else if (atom.substring(1, 4).equals("LBL")) {
                // lblConv(atom);
            }
        }

        System.out.println("Fixup Table:");

        for (List<String> item : fixupTable)
            System.out.println(item);

        System.out.println();

        System.out.println("Label Table:");

        for (List<String> item : labelTable)
            System.out.println(item);

        System.out.println();

        System.out.println("Machine Code:");

        // Print each string in the list
        for (String item : binOut) {
            System.out.println(item);
        }
        System.out.println();

    }
}
