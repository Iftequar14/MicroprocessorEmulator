package com.microprocessoremulator.opcode;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class MOV {
    public static int requiredOperands = 2;

    public static void execute(String registerDestination, String registerSource) throws Exception {
        boolean isSourceRegister, isDestinationRegister;
        int source = 0, destination = 0;
        if (registerDestination.length() == 1) {
            isDestinationRegister = true;
            switch (registerDestination) {
                case "A":
                    destination = 0;
                    break;
                case "B":
                    destination = 1;
                    break;
                case "C":
                    destination = 2;
                    break;
                case "D":
                    destination = 3;
                    break;
                case "E":
                    destination = 4;
                    break;
                case "H":
                    destination = 5;
                    break;
                case "L":
                    destination = 6;
                    break;
                default:
                    throw new Exception("There is no such register '" + registerDestination + "'");
            }
        } else if (registerDestination.length() == 4) {
            isDestinationRegister = false;
            if (MyHex.invalidate(registerDestination)) {
                destination = Integer.parseInt(registerDestination, 16);
            } else {
                throw new Exception("Invalid memory location. Memory Location should be from 0000 to 0FFF");
            }
        } else {
            throw new Exception("Invalid memory location. Memory Location should be from 0000 to 0FFF");
        }

        if (registerSource.length() == 1) {
            isSourceRegister = true;
            switch (registerSource) {
                case "A":
                    source = 0;
                    break;
                case "B":
                    source = 1;
                    break;
                case "C":
                    source = 2;
                    break;
                case "D":
                    source = 3;
                    break;
                case "E":
                    source = 4;
                    break;
                case "H":
                    source = 5;
                    break;
                case "L":
                    source = 6;
                    break;
                case "M":
                    isSourceRegister = false;
                    source = Integer.parseInt((EmulatorFragment.reg[5]+EmulatorFragment.reg[6]), 16);
                    break;
                default:
                    throw new Exception("There is no such register '" + registerSource + "'");
            }
        } else if (registerSource.length() == 4) {
            isSourceRegister = false;
            if (MyHex.invalidate(registerSource)) {
                source = Integer.parseInt(registerSource, 16);
            } else
                throw new Exception("Invalid memory location. Memory Location should be from 0000 to 0FFF");
        } else {
            throw new Exception("Invalid memory location. Memory Location should be from 0000 to 0FFF");
        }

        /*if (EmulatorFragment.reg[source] == null) {
            EmulatorFragment.reg[source] = "00";
        }
        if (EmulatorFragment.memoryAddress[source] == null) {
            EmulatorFragment.memoryAddress[source] = "00";
        }*/

        if (isDestinationRegister && isSourceRegister) {
            EmulatorFragment.reg[destination] = EmulatorFragment.reg[source];
        } else if (isDestinationRegister && !(isSourceRegister)) {
            EmulatorFragment.reg[destination] = EmulatorFragment.memoryAddress[source];
        } else if (!(isDestinationRegister) && isSourceRegister) {
            EmulatorFragment.memoryAddress[destination] = EmulatorFragment.reg[source];
        } else {
            EmulatorFragment.memoryAddress[destination] = EmulatorFragment.memoryAddress[source];
        }
    }
}
