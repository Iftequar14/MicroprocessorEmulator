package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class SUB {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        if (register.length() == 1) {
            switch (register) {
                case "A":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[0]) + 1));
                    break;
                case "B":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[1]) + 1));
                    break;
                case "C":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[2]) + 1));
                    break;
                case "D":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[3]) + 1));
                    break;
                case "E":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[4]) + 1));
                    break;
                case "H":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[5]) + 1));
                    break;
                case "L":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.reg[6]) + 1));
                    break;
                case "M":
                    EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.memoryAddress[Integer.parseInt(EmulatorFragment.reg[5] + EmulatorFragment.reg[6], 16)]) + 1));
                    break;
                default:
                    throw new Exception("Invalid register '" + register + "'");
            }
        } else if (register.length() == 4) {
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + complement(EmulatorFragment.memoryAddress[Integer.parseInt(register, 16)]) + 1));
        } else {
            throw new Exception("Invalid Memory Address Location '" + register + "'.\nMemory Address Location should starts from 0000 to FFFF");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }

    public static int complement(String data) {
        char[] tempData = MyHex.fixCharArrayLength(Integer.toBinaryString(Integer.parseInt(data, 16)).toCharArray());
        for(int i=0;i<8;i++) {
            if(tempData[i] == '0') {
                tempData[i] = '1';
            } else {
                tempData[i] = '0';
            }
        }
        return Integer.parseInt(MyHex.parseCharArray(tempData), 16);
    }
}
