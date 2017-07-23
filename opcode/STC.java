package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

/**
 * Created by MOHD IMTIAZ on 11-Oct-16.
 */
public class STC {
    public static void execute() {
        EmulatorFragment.reg[2] = "01";
    }
}
