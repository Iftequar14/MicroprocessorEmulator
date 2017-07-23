package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class RAL {

    public static void execute() {
        String binData = Integer.toBinaryString(Integer.parseInt(EmulatorFragment.reg[0], 16));
        char[] data = new char[8], tempData = binData.toCharArray();
        tempData = MyHex.fixCharArrayLength(tempData);
        for (int i = 0; i < 8; i++) {
            if (i != 0) {
                data[i - 1] = tempData[i];
            } else {
                data[7] = tempData[i];
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = Integer.parseInt(data[7]+"");
            }
        }
        EmulatorFragment.reg[0] = MyHex.parseCharArray(data);
    }
}
