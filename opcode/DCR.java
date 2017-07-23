package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

public class DCR {

    public static void execute(String register) throws Exception {
        int reg = 0;
        switch (register) {
            case "A":
                reg = 0;
                EmulatorFragment.reg[0] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) - 1);
                break;
            case "B":
                reg = 1;
                EmulatorFragment.reg[1] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[1], 16) - 1);
                break;
            case "C":
                reg = 2;
                EmulatorFragment.reg[2] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[2], 16) - 1);
                break;
            case "D":
                reg = 3;
                EmulatorFragment.reg[3] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[3], 16) - 1);
                break;
            case "E":
                reg = 4;
                EmulatorFragment.reg[4] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[4], 16) - 1);
                break;
            case "H":
                reg = 5;
                EmulatorFragment.reg[5] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[5], 16) - 1);
                break;
            case "L":
                reg = 6;
                EmulatorFragment.reg[6] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[6], 16) - 1);
                break;
            default:
                throw new Exception("Invalid Register '" + register + "'");
        }
        if (EmulatorFragment.reg[reg].equals("ffffffff")) {
            EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 1;
            EmulatorFragment.reg[reg] = "00";
        }
    }
}
