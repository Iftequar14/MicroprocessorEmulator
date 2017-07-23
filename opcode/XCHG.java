package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

public class XCHG {
    public static int requiredOperands = 1;

    public static void execute() {
        String tempData1,tempData2;
        tempData1 = EmulatorFragment.reg[3];
        tempData2 = EmulatorFragment.reg[4];
        EmulatorFragment.reg[3] = EmulatorFragment.reg[5];
        EmulatorFragment.reg[4] = EmulatorFragment.reg[6];
        EmulatorFragment.reg[5] = tempData1;
        EmulatorFragment.reg[6] = tempData2;
    }
}
