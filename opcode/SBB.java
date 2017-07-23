package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

/**
 * Created by MOHD IMTIAZ on 11-Oct-16.
 */
public class SBB {
    public static void execute(String register) throws Exception {
        int borrow = EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY];
        if (register.length() == 1) {
            switch (register) {
                case "A":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[0]) + 1 - borrow));
                    break;
                case "B":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[1]) + 1 - borrow));
                    break;
                case "C":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[2]) + 1 - borrow));
                    break;
                case "D":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[3]) + 1 - borrow));
                    break;
                case "E":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[4]) + 1 - borrow));
                    break;
                case "H":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[5]) + 1 - borrow));
                    break;
                case "L":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.reg[6]) + 1 - borrow));
                    break;
                case "M":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.memoryAddress[Integer.parseInt(EmulatorFragment.reg[5] + EmulatorFragment.reg[6], 16)]) + 1 - borrow));
                    break;
                default:
                    throw new Exception("Invalid register '" + register + "'");
            }
        } else if (register.length() == 4) {
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(EmulatorFragment.memoryAddress[Integer.parseInt(register, 16)]) + 1 - borrow));
        } else {
            throw new Exception("Invalid Memory Address Location '" + register + "'.\nMemory Address Location should starts from 0000 to FFFF");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }
}
