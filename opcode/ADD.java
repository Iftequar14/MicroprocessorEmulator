package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class ADD {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        if (register.length() == 1) {
            switch (register) {
                case "A":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[0], 16)));
                    break;
                case "B":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[1], 16)));
                    break;
                case "C":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[2], 16)));
                    break;
                case "D":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[3], 16)));
                    break;
                case "E":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[4], 16)));
                    break;
                case "H":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[5], 16)));
                    break;
                case "L":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.reg[6], 16)));
                    break;
                case "M":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.memoryAddress[Integer.parseInt(EmulatorFragment.reg[5] + EmulatorFragment.reg[6], 16)])));
                    break;
                default:
                    throw new Exception("Invalid register '" + register + "'");
            }
        } else if (register.length() == 4) {
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(EmulatorFragment.memoryAddress[Integer.parseInt(register, 16)], 16)));
        } else {
            throw new Exception("Invalid Memory Address Location '" + register + "'.\nMemory Address Location should starts from 0000 to FFFF");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }
}
