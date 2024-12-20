package phase3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Compiler {

    // Variables for the compiler
    public static int fpreg = 2;
    public static int lblAdress = 0;
    public static List<List<String>> fixupTable = new ArrayList<>();
    public static List<List<String>> labelTable = new ArrayList<>();
    public static List<String> binOut = new ArrayList<>();

    /**
     * Author: Jee McCloud
     * 
     * @param input
     * @param length
     */
    public static String pad(String input, int length) {
        while (input.length() < length) {
            input = "0" + input;
        }
        return input;
    }

    /**
     * Author: Ethan Glenn
     * Sample input (im assuming): (CLR,,,,,t0)
     * - or possibly refactor for (CLR,t0,,,,)?
     * 
     * @param input
     */
    public static String clrConv(String reg) {
        String opcode = "0101";
        String cmp = "0000";

        if (reg == "") {
            reg = "0000";
        }

        // String[] splitInput = input.split(",");
        // String reg = splitInput[5].substring(0, splitInput[5].length() - 1);

        // String mem = "";

        // for (List<String> row : labelTable) {
        // if (row.get(0).equals(reg)) {
        // mem = row.get(2);
        // }
        // }

        return opcode + cmp + reg + "00000000000000000000";
    }

    /**
     * Author: Jordan Dennison && Alejandro Santiago
     * 
     * @param input input atom
     * @return binary instruction
     */
    public static void movConv(String input) {

        String mem1 = "";
        String reg = "";

        String[] splitInput = input.split(",");

        String src = splitInput[1].trim();

        String des = splitInput[3].substring(0, splitInput[3].length() - 1);

        // // Find the address for source(s)
        // for (List<String> row : labelTable) {
        // if (row.get(0).equals(src)) {

        // src = row.get(2);

        // }
        // }

        // String destAddress = "";
        // for (List<String> row : labelTable) {
        // if (row.get(0).equals(des)) {

        // destAddress = row.get(2);

        // }
        // }

        for (List<String> row : labelTable) {
            if (row.get(0).equals(splitInput[1])) {

                mem1 = row.get(2);
            }
        }

        if (mem1.equals("")) {

            // Get the label name and address
            String labelName = splitInput[1];
            String addr = String.valueOf(lblAdress + 100);
            String value = splitInput[1];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);

            mem1 = addr;
        }

        // TODO: Modified this but idk
        stoConv(mem1);
        reg = lodConv(String.valueOf(lblAdress + 96));

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

            reg = lodConv(String.valueOf(stoAddress - 4));

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

        if (mem2.equals("")) {

            // Get the label name and address
            String labelName = splitInput[2];
            String addr = String.valueOf(lblAdress + 100);
            String value = splitInput[2];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);

            mem2 = addr;
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

        return opcode + cmp + pad(reg, 4)
                + pad(decimalToBinary(Integer.parseInt(mem2)), 20);
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

            reg = lodConv(String.valueOf(stoAddress - 4));

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

        if (mem2.equals("")) {

            // Get the label name and address
            String labelName = splitInput[2];
            String addr = String.valueOf(lblAdress + 100);
            String value = splitInput[2];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);

            mem2 = addr;
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

        return opcode + cmp + pad(reg, 4)
                + pad(decimalToBinary(Integer.parseInt(mem2)), 20);
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

            reg = lodConv(String.valueOf(stoAddress - 4));

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

        if (mem2.equals("")) {

            // Get the label name and address
            String labelName = splitInput[2];
            String addr = String.valueOf(lblAdress + 100);
            String value = splitInput[2];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);

            mem2 = addr;
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

        return opcode + cmp + pad(reg, 4)
                + pad(decimalToBinary(Integer.parseInt(mem2)), 20);
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

            reg = lodConv(String.valueOf(stoAddress - 4));

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

        if (mem2.equals("")) {

            // Get the label name and address
            String labelName = splitInput[2];
            String addr = String.valueOf(lblAdress + 100);
            String value = splitInput[2];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value);
            addresses.add(addr);

            labelTable.add(addresses);

            mem2 = addr;
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

        return opcode + cmp + pad(reg, 4)
                + pad(decimalToBinary(Integer.parseInt(mem2)), 20);
    }

    /**
     * Author: Ethan Glenn
     * 
     * @param input
     * @return binary instruction
     */
    public static String jmpConv(String input) {
        String opcode = "0101";
        String cmp = "0000";

        String[] splitInput = input.split(",");
        // String reg = splitInput[5].substring(0);
        String reg = splitInput[5].trim();
        String mem = "";

        // Added to remove trailing )
        if (reg.endsWith(")")) {
            reg = reg.substring(0, reg.length() - 1);

        }

        // changed because weird index out of bounds
        for (List<String> row : labelTable) {
            if (row.get(0).equals(reg)) {
                if (row.size() == 2) {
                    mem = row.get(1);
                } else if (row.size() > 2) {
                    mem = row.get(2);
                }
                break;

            }
        }

        // return "JMP -> " + opcode + "/" + cmp + "/" +
        // decimalToBinary(Integer.parseInt(reg)) + "/" + mem;
        return opcode + cmp + "0000" + pad(decimalToBinary(Integer.parseInt(mem)), 20);
    }

    /**
     * Author: Ethan Glenn
     * TODO: Verify CMP logic(???)
     * 
     * @param input
     * @return binary instruction
     */
    public static String cmpConv(String input) {
        String opcode = "0110";
        String cmp = "0000"; // "always true", default

        String[] splitInput = input.split(",");
        String reg1 = splitInput[1];
        String reg2 = splitInput[2];
        String mem1 = "";
        String mem2 = "";

        for (List<String> row : labelTable) {
            if (row.get(0).equals(reg1))
                mem1 = row.get(2);

            if (row.get(0).equals(reg2))
                mem2 = row.get(2);
        }

        // Attempt at implementing logic
        try {
            int reg1Value = Integer.parseInt(lodConv(mem1));
            int reg2Value = Integer.parseInt(lodConv(mem2));
            int compVal = Integer.compare(reg1Value, reg2Value);

            if (compVal < 0)
                cmp = decimalToBinary(1);
            else if (compVal == 0)
                cmp = decimalToBinary(2);
            else
                cmp = decimalToBinary(3);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Assuming we're following the register + memory address format
        binOut.add(opcode + cmp + pad(decimalToBinary(Integer.parseInt(reg1)), 4)
                + pad(mem2, 20));
        return cmp;
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

        System.out.println(reg);

        binOut.add(opcode + cmp + pad(reg, 4)
                + pad((decimalToBinary(Integer.parseInt(mem))), 20));

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

        binOut.add(opcode + cmp + pad(reg, 4)
                + pad(decimalToBinary(Integer.parseInt(mem)), 20));

    }

    public static String hltConv(String input) {
        // Should just hault processor
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Author: Alejandro Santiago
     * Implements the TST instruction.
     * 
     * @param input The atom string
     * @return Machine code for TST
     */
    public static void tstConv(String input) {
        String opcodeCmp = "0110"; // CMP opcode
        String opcodeJmp = "0101"; // JMP opcode

        // System.out.println("CMP OP: " + input.split(",")[4]);

        String cmp = pad(decimalToBinary(Integer.parseInt(input.split(",")[4])), 4); // Flag for CMP
        String[] splitInput = input.split(",");

        String var = splitInput[1]; // Variable to compare
        String value = splitInput[2]; // Comparison value
        String label = splitInput[5].substring(0, splitInput[5].length() - 1); // Label to jump to

        // Find memory location of the variable
        String memVar = "";
        for (List<String> row : labelTable) {
            if (row.get(0).equals(var)) {
                memVar = row.get(2);
            }
        }

        if (memVar.equals("")) {

            // Get the label name and address
            String labelName = splitInput[2];
            String addr = String.valueOf(lblAdress + 100);
            String value2 = splitInput[2];
            lblAdress += 4;

            // Add into the array
            List<String> addresses = new ArrayList<>();
            addresses.add(labelName);
            addresses.add(value2);
            addresses.add(addr);

            labelTable.add(addresses);

            memVar = addr;
        }

        // Store the comparison value in memory
        lblAdress += 4;
        List<String> newLabel = new ArrayList<>();
        newLabel.add(value);
        newLabel.add(value);
        newLabel.add(String.valueOf(lblAdress + 100));
        labelTable.add(newLabel);

        // store the value we are comparing to, generate sto instruction
        stoConv(String.valueOf(lblAdress + 100));

        // Generate CMP instruction
        // String cmpInstruction = opcodeCmp + cmp
        // + pad(decimalToBinary(Integer.parseInt(memVar)), 4) +
        // pad(decimalToBinary(lblAdress), 20);

        String cmpInstruction = opcodeCmp + cmp +
                "0000" + pad(decimalToBinary(lblAdress), 20);

        // Generate JMP instruction based on label
        String memLabel = "";
        for (List<String> row : fixupTable) {
            if (row.get(0).equals(label)) {
                memLabel = row.get(1);
            }
        }

        String jmpInstruction = opcodeJmp + "00000000"
                + pad(decimalToBinary(Integer.parseInt(memLabel)), 20);

        binOut.add(cmpInstruction);
        binOut.add(jmpInstruction);

    }

    


     /* Validates that the instruction is a 32-bit binary string.
     */
    private static boolean isValidBinary(String instr) {
        return instr.matches("[01]{32}");
    }
    
    /**
     * Extracts the opcode from a 32-bit instruction.
     */
    public static String getOpcode(String instr) {
        return instr.substring(0, 4);
    }
    
    /**
     * Extracts the register from a 32-bit instruction.
     */
    public static String getRegister(String instr) {
        return instr.substring(8, 12);
    }
    
    /**
     * Extracts the memory address from a 32-bit instruction.
     */
    public static String getMemoryAddress(String instr) {
        return instr.substring(12, 32);
    }
    /**
    * Author: Alejandro Santiago & Ethan Glenn
    * looks at the binout and checks locations of lod and sto to remove when they
    * go to the same address. based on book example
    **/
    public static void peepholeOptimizeLoadStore() {
        List<String> optimizedBinOut = new ArrayList<>();
        String lastSTORegister = "";
        String lastSTOAddress = "";
    
        for (String instr : binOut) {
            // Validate instruction length and binary format
            if (instr.length() != 32 || !isValidBinary(instr)) {
                // Invalid instruction; add it as-is
                optimizedBinOut.add(instr);
                continue;
            }
        
            String opcode = getOpcode(instr);
            String reg = getRegister(instr);
            String mem = getMemoryAddress(instr);
        
            if (opcode.equals("1000")) { // STO
                // Update last STO information
                lastSTORegister = reg;
                lastSTOAddress = mem;
            
                optimizedBinOut.add(instr);
            }
            else if (opcode.equals("0111")) { // LOD
                // Check if this LOD is redundant (same register and address as last STO)
                if (reg.equals(lastSTORegister) && mem.equals(lastSTOAddress)) {
                    // Redundant LOD; remove the last STO by not adding it to optimizedBinOut
                    // Remove the last STO from optimizedBinOut
                    if (!optimizedBinOut.isEmpty()) {
                        String lastInstr = optimizedBinOut.get(optimizedBinOut.size() - 1);
                        String lastOpcode = getOpcode(lastInstr);
                        String lastReg = getRegister(lastInstr);
                        String lastMem = getMemoryAddress(lastInstr);
                        if (lastOpcode.equals("1000") && lastReg.equals(reg) && lastMem.equals(mem)) {
                            optimizedBinOut.remove(optimizedBinOut.size() - 1);
                            // Skip adding this LOD as well
                            lastSTORegister = "";
                            lastSTOAddress = "";
                            continue;
                        }
                    }
                }
                // If not redundant, add the LOD
                optimizedBinOut.add(instr);
            }
            else {
                // Any other instruction, add as-is and reset STO tracking
                optimizedBinOut.add(instr);
                lastSTORegister = "";
                lastSTOAddress = "";
            }
        }
    
        // Replace the original binOut with the optimized version
        binOut = optimizedBinOut;
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
                String labelName = splitInput[3].substring(0, splitInput[3].length() - 1);
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

    public static ArrayList<String> globalOptimizer(ArrayList<String> oldArray) {

        // Instantiate Arrays / Variables
        ArrayList<String> newArray = new ArrayList<String>();
        int deadCodeFlag = 0;

        // Loop through atoms
        for (String atom : oldArray) {

            // Check if the atom is a label
            if (atom.contains("LBL")) {

                // Add the atom to the return array
                deadCodeFlag = 0;
                newArray.add(atom);

                continue;

            }

            // Check if the atom is a jump or test
            if (atom.contains("JMP") || atom.contains("TST")) {

                deadCodeFlag = 1;
                newArray.add(atom);
                continue;

            }

            // Dead code checker, if so skip the atom
            if (deadCodeFlag == 1) {

                continue;

            }

            newArray.add(atom);

        }

        return newArray;
    }

    public static void main(String[] args) {
        boolean enableOptimizationBackend = true;
        int globalOp = 1;
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                if (args[i].equals("-nf")) {
                    globalOp = 0;
                } else if (args[i].equals("-nb")) {
                    enableOptimizationBackend = false;
                }
            }
        }

        // Test to read in txt file
        String filename = "atoms.txt";
        String[] fileLines = readFileToArray(filename);
        ArrayList<String> atoms = new ArrayList<String>();

        for (String line : fileLines) {
            atoms.add(line);
        }

        if (globalOp == 1) {
            atoms = globalOptimizer(atoms);
        } else {
            System.out.println("Skipping Frontend Optimization!");
        }

        // Create something to store labels and variables
        createFixupTable(atoms);

        for (String atom : atoms) {

            if (atom.substring(1, 4).equals("ADD")) {
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
            } else if (atom.substring(1, 4).equals("MOV")) {
                movConv(atom);
            } else if (atom.substring(1, 4).equals("LBL")) {
                // lblConv(atom);
            } else if (atom.substring(1, 4).equals("TST")) {
                tstConv(atom);
            }
        }

        System.out.println("Fixup Table:");

        for (List<String> item : fixupTable)
            System.out.println(item);

        System.out.println();

        System.out.println("Label Table:");

        try {
            File out = new File(args[1]);
            BufferedWriter w = new BufferedWriter(new FileWriter(out));
            // Print each string in the list
            for (List<String> item : labelTable) {
                System.out.println(item);
            }
            System.out.println();
            // Apply optimizations if enabled

            if (enableOptimizationBackend) {
                peepholeOptimizeLoadStore(); // Another example optimization
            } else {
                System.out.println("Skipping backend optimization!");
            }
            System.out.println("Machine Code:");

            Path path = Paths.get("output.bin");
            var writer = new DataOutputStream(Files.newOutputStream(path));

            // First Instruction for MiniVM
            writer.writeInt(1);

            // Print each string in the list
            for (String item : binOut) {
                System.out.println(item);
                var intCoverter = new BigInteger(item, 2).intValue();
                writer.writeInt(intCoverter);
            }

            // HLT Instruction for MiniVM
            writer.writeInt(new BigInteger("10010000000000000000000000000000", 2).intValue());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
