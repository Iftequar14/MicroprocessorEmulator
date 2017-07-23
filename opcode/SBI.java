package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

/**
 * Created by MOHD IMTIAZ on 11-Oct-16.
 */
public class SBI {
    public static void execute(String data) throws Exception {
        if (data.length() == 2 && MyHex.invalidate(data)) {
            int borrow = EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY];
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + SUB.complement(data) + 1 - borrow));
        } else if (data.length() != 2) {
            throw new Exception("Hexa-Decimal data should of 2 bytes");
        } else {
            throw new Exception("Invalid Hexa-Decimal data '" + data + "'.\nEnter a value in range 00 to FF");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }
}
