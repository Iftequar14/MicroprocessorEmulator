package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;

public class STA {
    public static void execute(String accumulator, String destination) throws Exception {
        if (destination.length() == 4) {
            EmulatorFragment.memoryAddress[Integer.parseInt(destination, 16)] = accumulator;
        } else {
            throw new Exception("Invalid Memory location. Memory location should be from 0000 to 0FFF");
        }
    }
}
