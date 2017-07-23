package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class LHLD {
    public static int requiredOperands = 1;

    public static void execute(String memoryAddressLocation) throws Exception {
        if (memoryAddressLocation.length() == 4 && MyHex.invalidate(memoryAddressLocation)) {
            int memoryLocation = Integer.parseInt(memoryAddressLocation, 16);
            EmulatorFragment.reg[6] = EmulatorFragment.memoryAddress[memoryLocation];
            EmulatorFragment.reg[5] = EmulatorFragment.memoryAddress[++memoryLocation];
        } else {
            throw new Exception("Invalid Memory Address Location.\nMemory Address Location should be from 0000 to FFFF");
        }
    }
}
