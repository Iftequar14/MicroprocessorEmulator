package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class LXI {
    public static int requiredOperands = 2;

    public static void execute(String register, String memoryAddressLocation) throws Exception {
        if (memoryAddressLocation.length() == 4 && MyHex.invalidate(memoryAddressLocation)) {
            if (register.equals("B")) {
                int hx = Integer.parseInt(memoryAddressLocation,16);
                EmulatorFragment.reg[1] = Integer.toHexString(hx/256);
                EmulatorFragment.reg[2] = Integer.toHexString(hx%256);
            } else if (register.equals("D")) {
                int hx = Integer.parseInt(memoryAddressLocation,16);
                EmulatorFragment.reg[3] = Integer.toHexString(hx/256);
                EmulatorFragment.reg[4] = Integer.toHexString(hx%256);
            } else if (register.equals("H")) {
                int hx = Integer.parseInt(memoryAddressLocation,16);
                EmulatorFragment.reg[5] = Integer.toHexString(hx/256);
                EmulatorFragment.reg[6] = Integer.toHexString(hx%256);
            } else {
                throw new Exception("Invalid Register '" + register + "'");
            }
        } else {
            throw new Exception("Invalid memory address location\nMemory Address location should be from 0000 to FFFF");
        }
    }
}
