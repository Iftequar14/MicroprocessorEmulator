package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

/**
 * Created by MOHD IMTIAZ on 11-Oct-16.
 */
public class CMA {
    public static void execute() {
        EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(SUB.complement(EmulatorFragment.reg[0])));
    }
}
