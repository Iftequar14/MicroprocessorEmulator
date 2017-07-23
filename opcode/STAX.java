package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

public class STAX {
    public static int requiredOperands = 1;

    public static void execute(String register) throws Exception {
        if(register.equals("B")) {
            EmulatorFragment.memoryAddress[Integer.parseInt((EmulatorFragment.reg[1] + EmulatorFragment.reg[2]))] = EmulatorFragment.reg[0];
        } else if (register.equals("D")) {
            EmulatorFragment.memoryAddress[Integer.parseInt((EmulatorFragment.reg[3] + EmulatorFragment.reg[4]))] = EmulatorFragment.reg[0];
        } else if (register.equals("H")) {
            EmulatorFragment.memoryAddress[Integer.parseInt((EmulatorFragment.reg[5] + EmulatorFragment.reg[6]))] = EmulatorFragment.reg[0];
        } else {
            throw new Exception("Invalid Register '"+register+"'");
        }
    }
}
