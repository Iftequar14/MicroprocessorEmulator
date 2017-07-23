package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class LDA {
    public static int requiredOperands = 1;
    public static void execute(String memoryLocation) throws Exception {
        if(MyHex.invalidate(memoryLocation)) {
            EmulatorFragment.reg[0] = EmulatorFragment.memoryAddress[Integer.parseInt(memoryLocation, 16)];
        } else {
            throw new Exception("Invalid memory address location '"+memoryLocation+"'");
        }
    }
}
