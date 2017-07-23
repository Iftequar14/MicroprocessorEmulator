package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class RAR {

    public static void execute() throws Exception {
        String binData = Integer.toBinaryString(Integer.parseInt(EmulatorFragment.reg[0], 16));
        char[] data = new char[8], tempData = binData.toCharArray();
        tempData = MyHex.fixCharArrayLength(tempData);
        for (int i = 0; i < 8; i++) {
            if (i != 7) {
                data[i + 1] = tempData[i];
            } else {
                data[0] = tempData[i];
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = Integer.parseInt(data[0]+"");
            }
        }
        EmulatorFragment.reg[0] = MyHex.parseCharArray(data);
    }
}
