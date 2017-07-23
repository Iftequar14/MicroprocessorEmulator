package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class INX {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        int tempReg;
        switch (register) {
            case "B":
                tempReg = Integer.parseInt(EmulatorFragment.reg[1] + EmulatorFragment.reg[2], 16) + 1;
                if (tempReg > 65535) {
                    EmulatorFragment.flags[0] = 1;
                    EmulatorFragment.reg[1] = MyHex.fixHexDataLength(Integer.toHexString((tempReg / 256) - 256));
                    EmulatorFragment.reg[2] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                } else {
                    EmulatorFragment.flags[0] = 0;
                    EmulatorFragment.reg[1] = MyHex.fixHexDataLength(Integer.toHexString(tempReg / 256));
                    EmulatorFragment.reg[2] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                }
                break;
            case "D":
                tempReg = Integer.parseInt(EmulatorFragment.reg[3] + EmulatorFragment.reg[4], 16) + 1;
                if (tempReg + "".length() > 65535) {
                    EmulatorFragment.flags[0] = 1;
                    EmulatorFragment.reg[3] = MyHex.fixHexDataLength(Integer.toHexString((tempReg / 256) - 256));
                    EmulatorFragment.reg[4] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                } else {
                    EmulatorFragment.flags[0] = 0;
                    EmulatorFragment.reg[3] = MyHex.fixHexDataLength(Integer.toHexString(tempReg / 256));
                    EmulatorFragment.reg[4] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                }
                break;
            case "H":
                tempReg = Integer.parseInt(EmulatorFragment.reg[5] + EmulatorFragment.reg[6], 16) + 1;
                if (tempReg + "".length() > 65535) {
                    EmulatorFragment.flags[0] = 1;
                    EmulatorFragment.reg[5] = MyHex.fixHexDataLength(Integer.toHexString((tempReg / 256) - 256));
                    EmulatorFragment.reg[6] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                } else {
                    EmulatorFragment.flags[0] = 0;
                    EmulatorFragment.reg[5] = MyHex.fixHexDataLength(Integer.toHexString(tempReg / 256));
                    EmulatorFragment.reg[6] = MyHex.fixHexDataLength(Integer.toHexString(tempReg % 256));
                }
                break;
            default:
                throw new Exception("Invalid Register '" + register + "'");
        }
    }
}
