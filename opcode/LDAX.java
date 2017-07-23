package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

public class LDAX {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        String registerPairMemoryLocation;
        if (register.equals("B")) {
            registerPairMemoryLocation = EmulatorFragment.reg[1]+EmulatorFragment.reg[2];
            EmulatorFragment.reg[0] = EmulatorFragment.memoryAddress[Integer.parseInt(registerPairMemoryLocation, 16)];
        } else if (register.equals("D")) {
            registerPairMemoryLocation = EmulatorFragment.reg[3]+EmulatorFragment.reg[4];
            EmulatorFragment.reg[0] = EmulatorFragment.memoryAddress[Integer.parseInt(registerPairMemoryLocation, 16)];
        } else {
            throw new Exception("Invalid regitser '" + register + "'");
        }
    }
}
