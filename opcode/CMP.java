package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class CMP {

    public static void execute(String dataResource) throws Exception {
        if (dataResource.length() == 1) {
            int reg;
            switch (dataResource) {
                case "B":
                    reg = 1;
                    break;
                case "C":
                    reg = 2;
                    break;
                case "D":
                    reg = 3;
                    break;
                case "E":
                    reg = 4;
                    break;
                case "H":
                    reg = 5;
                    break;
                case "L":
                    reg = 6;
                    break;
                default:
                    throw new Exception("Invalid Register '" + dataResource + "'");
            }
            int accumulatorData,registerData;
            accumulatorData = Integer.parseInt(EmulatorFragment.reg[0], 16);
            registerData = Integer.parseInt(EmulatorFragment.reg[reg], 16);
            if (accumulatorData < registerData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = 1;
            } else if (accumulatorData == registerData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 1;
            } else if (accumulatorData > registerData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 0;
            }
        } else if (dataResource.length() == 4 && MyHex.invalidate(dataResource)) {
            int accumulatorData,memoryData;
            accumulatorData = Integer.parseInt(EmulatorFragment.reg[0], 16);
            memoryData = Integer.parseInt(EmulatorFragment.memoryAddress[Integer.parseInt(dataResource, 16)], 16);
            if (accumulatorData < memoryData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = 1;
            } else if (accumulatorData == memoryData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 1;
            } else if (accumulatorData > memoryData) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 0;
            }
        } else {
            throw new Exception("Invalid Memory Location.\nMemory Location should be from 0000 to FFFF");
        }
    }
}
