package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class SHLD {
    public static int requiredOperands = 1;

    public static void execute(String memoryLocation) throws Exception {
        if (memoryLocation.length() == 4 && MyHex.invalidate(memoryLocation)) {
            int memLocation = Integer.parseInt(memoryLocation, 16);
            EmulatorFragment.memoryAddress[memLocation] = EmulatorFragment.reg[6];
            EmulatorFragment.memoryAddress[++memLocation] = EmulatorFragment.reg[5];
        } else {
            throw new Exception("Invalid Memory Address Location '" + memoryLocation + "'\nMemory Address Location should starts from 0000 to FFFF");
        }
    }
}
