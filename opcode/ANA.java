package com.microprocessoremulator.opcode;

import android.util.Log;

import com.microprocessoremulator.EmulatorFragment;
import com.microprocessoremulator.MyHex;

public class ANA {

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
            char[] accumulatorData = MyHex.fixCharArrayLength(EmulatorFragment.reg[0].toCharArray());
            char[] registerData = MyHex.fixCharArrayLength(EmulatorFragment.reg[reg].toCharArray());
            EmulatorFragment.reg[0] = andTheContent(accumulatorData, registerData);
        } else if (dataResource.length() == 4 && MyHex.invalidate(dataResource)) {
            char[] accumulatorData = MyHex.fixCharArrayLength(EmulatorFragment.reg[0].toCharArray());
            char[] memoryData = MyHex.fixCharArrayLength(EmulatorFragment.memoryAddress[Integer.parseInt(dataResource, 16)].toCharArray());
            EmulatorFragment.reg[0] = andTheContent(accumulatorData, memoryData);
        } else {
            throw new Exception("Invalid Memory Location.\nMemory Location should be from 0000 to FFFF");
        }
    }

    public static String andTheContent(char[] accmulatorData, char[] resourceData) {
        char[] andedDataArray = new char[8];
        for (int i = 0; i < 8; i++) {
            Log.d("DEBUG-MY-APP","FOR "+i+" ACCUMULATOR DATA : "+accmulatorData[i]+" and RESOURCE DATA : "+resourceData[i]);
            if (accmulatorData[i] == '1' && resourceData[i] == '1') {
                andedDataArray[i] = '1';
            } else {
                andedDataArray[i] = '0';
            }
            Log.d("DEBUG-MY-APP","FOR "+i+" RESULTANT ARRAY DATA "+andedDataArray[i]);
        }
        Log.d("DEBUG-MY-APP","Anded array is : "+andedDataArray[0]+""+andedDataArray[1]+""+andedDataArray[2]+""+andedDataArray[3]+""+andedDataArray[4]+""+andedDataArray[5]+""+andedDataArray[6]+""+andedDataArray[7]);
        Log.d("DEBUG-MY-APP","Anded data is : "+MyHex.parseCharArray(andedDataArray));
        return MyHex.parseCharArray(andedDataArray);
    }
}
