package com.microprocessoremulator.opcode;

import android.util.Log;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class CPI {
    public static void execute(String immediateData) throws Exception {
        if (immediateData.length() == 2 && MyHex.invalidate(immediateData)) {
            int accumulatorValue, immediateValue;
            accumulatorValue = Integer.parseInt(EmulatorFragment.reg[0], 16);
            immediateValue = Integer.parseInt(immediateData, 16);
            if (accumulatorValue < immediateValue) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = 1;
            } else if (accumulatorValue == immediateValue) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 1;
            } else if (accumulatorValue > immediateValue) {
                EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY] = EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO] = 0;
            }
        } else {
            throw new Exception("Invalid Immediate Data '" + immediateData + "'.Immediate data should be from 00 to FF.");
        }
        Log.d("DEBUG-MY-APP", "FLAG ZERO : " + EmulatorFragment.flags[EmulatorFragment.FLAG_ZERO]);
        Log.d("DEBUG-MY-APP","FLAG CARRY : "+EmulatorFragment.flags[EmulatorFragment.FLAG_CARRY]);
    }
}
