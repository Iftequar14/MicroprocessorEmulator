package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class DAD {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        if (register.equals("B")) {
            EmulatorFragment.reg[6] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[6], 16) + Integer.parseInt(EmulatorFragment.reg[2], 16));
            if (EmulatorFragment.reg[6].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[6] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[6], 16)) - 256));
            }
            EmulatorFragment.reg[5] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[5], 16) + Integer.parseInt(EmulatorFragment.reg[1], 16) + EmulatorFragment.flags[0]);
            if (EmulatorFragment.reg[5].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[5] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[5], 16)) - 256));
            }
        } else if (register.equals("D")) {
            EmulatorFragment.reg[6] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[6], 16) + Integer.parseInt(EmulatorFragment.reg[4], 16));
            if (EmulatorFragment.reg[6].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[6] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[6], 16)) - 256));
            }
            EmulatorFragment.reg[5] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[5], 16) + Integer.parseInt(EmulatorFragment.reg[3], 16) + EmulatorFragment.flags[0]);
            if (EmulatorFragment.reg[5].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[5] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[5], 16)) - 256));
            }
        } else if (register.equals("H")) {
            EmulatorFragment.reg[6] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[6], 16) + Integer.parseInt(EmulatorFragment.reg[6], 16));
            if (EmulatorFragment.reg[6].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[6] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[6], 16)) - 256));
            }
            EmulatorFragment.reg[5] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[5], 16) + Integer.parseInt(EmulatorFragment.reg[5], 16) + EmulatorFragment.flags[0]);
            if (EmulatorFragment.reg[5].length() > 2) {
                EmulatorFragment.flags[0] = 1;
                EmulatorFragment.reg[5] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[5], 16)) - 256));
            }
        } else {
            throw new Exception("Invalid Register '" + register + "'");
        }
    }
}
