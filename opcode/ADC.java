package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class ADC {
    public static int requiredOperands = 1;

    public static void execute(String source) throws Exception {
        if (source.length() == 1) {
            switch (source) {
                case "A":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[0], 16) + EmulatorFragment.flags[0]));
                    break;
                case "B":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[1], 16) + EmulatorFragment.flags[0]));
                    break;
                case "C":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[2], 16) + EmulatorFragment.flags[0]));
                    break;
                case "D":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[3], 16) + EmulatorFragment.flags[0]));
                    break;
                case "E":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[4], 16) + EmulatorFragment.flags[0]));
                    break;
                case "H":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[5], 16) + EmulatorFragment.flags[0]));
                    break;
                case "L":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[6], 16) + EmulatorFragment.flags[0]));
                    break;
                case "M":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.memoryAddress[Integer.parseInt(EmulatorFragment.reg[5] + EmulatorFragment.reg[6], 16)]) + EmulatorFragment.flags[0]));
                    break;
                default:
                    throw new Exception("Invalid Register '" + source + "'");
            }
        } else if (source.length() == 4 && MyHex.invalidate(source)) {
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.memoryAddress[Integer.parseInt(source, 16)], 16) + EmulatorFragment.flags[0]));
        } else {
            throw new Exception("Invalid Memory Address Location '" + source + "'.\nMemory Address Location should starts from 0000 to FFFF");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }
}
