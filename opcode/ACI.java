package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class ACI {
    public static int requiredOperands = 1;

    public static void execute(String data) throws Exception {
        if (data.length() == 2 && MyHex.invalidate(data)){
            EmulatorFragment.reg[0] = Integer.toHexString(Integer.parseInt(EmulatorFragment.reg[0], 16) + Integer.parseInt(data, 16) + EmulatorFragment.flags[0]);
        } else {
            throw new Exception("Invalid Hexa-Decimal Data '"+data+"'\nData should be of Max 2 Bytes(00 to FF)");
        }
        if (EmulatorFragment.reg[0].length() > 2) {
            EmulatorFragment.flags[0] = 1;
            EmulatorFragment.reg[0] = MyHex.fixHexDataLength(Integer.toHexString((Integer.parseInt(EmulatorFragment.reg[0], 16)) - 256));
        }
    }
}
