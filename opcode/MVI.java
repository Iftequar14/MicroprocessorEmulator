package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class MVI {
    public static int requiredOperands = 2;
    public static void execute(String destination, String data) throws Exception {
        if(!MyHex.invalidate(data))
            throw new Exception("Invalid Hexa-Decimal Value '"+data+"'");
        if(data.length()>2){
            throw new Exception("Provide Hexa-Decimal Value between 00 to FF");
        }
        data = MyHex.fixHexDataLength(data);
        if (destination.length() == 1) {
            switch (destination) {
                case "A":
                    EmulatorFragment.reg[0] = data;
                    break;
                case "B":
                    EmulatorFragment.reg[1] = data;
                    break;
                case "C":
                    EmulatorFragment.reg[2] = data;
                    break;
                case "D":
                    EmulatorFragment.reg[3] = data;
                    break;
                case "E":
                    EmulatorFragment.reg[4] = data;
                    break;
                case "H":
                    EmulatorFragment.reg[5] = data;
                    break;
                case "L":
                    EmulatorFragment.reg[6] = data;
                    break;
                default:
                    throw new Exception("There is no such register '" + destination + "'");
            }
        } else if (destination.length() == 4) {
            EmulatorFragment.memoryAddress[Integer.parseInt(destination, 16)] = data;
        } else {
            throw new Exception("Invalid memory address '" + destination + "'");
        }
    }
}
