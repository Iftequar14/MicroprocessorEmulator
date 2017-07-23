package com.microprocessoremulator;

public class MyHex {
    public static String toHex(int number) {
        String equivalentHex;
        if (number < 16)
            equivalentHex = ("000" + Integer.toHexString(number)).toUpperCase();
        else if (number < 256)
            equivalentHex = ("00" + Integer.toHexString(number)).toUpperCase();
        else if (number < 4096)
            equivalentHex = ("0" + Integer.toHexString(number)).toUpperCase();
        else
            equivalentHex = Integer.toHexString(number).toUpperCase();
        return equivalentHex;
    }

    public static boolean invalidate(String hexData) {
        try {
            if (Integer.parseInt(hexData, 16) < 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String fixHexDataLength(String hexData) {
        if (hexData.length() == 1) {
            return (("0" + hexData).toUpperCase());
        } else {
            return (hexData.toUpperCase());
        }
    }

    public static String fixAddressLength(String address) {
        if (address != null) {
            if(address.length() == 1) {
                return (("000"+address).toUpperCase());
            } else if (address.length() == 2) {
                return (("00"+address).toUpperCase());
            } else if (address.length() == 3) {
                return (("0"+address).toUpperCase());
            } else {
                return address.toUpperCase();
            }
        }
        return address;
    }

    public static String parseCharArray(char[] hexArray) {
        int i, j, number = 0;
        for (i = 0, j = 7; i < 8 && j >= 0; i++, j--) {
            if (hexArray[j] == '1') {
                number += pow(2, i);
            }
        }
        return MyHex.fixHexDataLength(Integer.toHexString(number));
    }

    public static int pow(int exponent, int power) {
        int result = 1;
        if (power == 0)
            return 1;
        else if (power == 1)
            return exponent;
        else {
            while (power > 0) {
                result *= exponent;
                power--;
            }
        }
        return result;
    }

    public static char[] fixCharArrayLength(char[] charArray) {
        int length = charArray.length;
        char[] data = new char[8];
        switch (length) {
            case 1:
                data[0] = '0';
                data[1] = '0';
                data[2] = '0';
                data[3] = '0';
                data[4] = '0';
                data[5] = '0';
                data[6] = '0';
                data[7] = charArray[0];
                break;
            case 2:
                data[0] = '0';
                data[1] = '0';
                data[2] = '0';
                data[3] = '0';
                data[4] = '0';
                data[5] = '0';
                data[6] = charArray[0];
                data[7] = charArray[1];
                break;
            case 3:
                data[0] = '0';
                data[1] = '0';
                data[2] = '0';
                data[3] = '0';
                data[4] = '0';
                data[5] = charArray[0];
                data[6] = charArray[1];
                data[7] = charArray[2];
                break;
            case 4:
                data[0] = '0';
                data[1] = '0';
                data[2] = '0';
                data[3] = '0';
                data[4] = charArray[0];
                data[5] = charArray[1];
                data[6] = charArray[2];
                data[7] = charArray[3];
                break;
            case 5:
                data[0] = '0';
                data[1] = '0';
                data[2] = '0';
                data[3] = charArray[0];
                data[4] = charArray[1];
                data[5] = charArray[2];
                data[6] = charArray[3];
                data[7] = charArray[4];
                break;
            case 6:
                data[0] = '0';
                data[1] = '0';
                data[2] = charArray[0];
                data[3] = charArray[1];
                data[4] = charArray[2];
                data[5] = charArray[3];
                data[6] = charArray[4];
                data[7] = charArray[5];
                break;
            case 7:
                data[0] = '0';
                data[1] = charArray[0];
                data[2] = charArray[1];
                data[3] = charArray[2];
                data[4] = charArray[3];
                data[5] = charArray[4];
                data[6] = charArray[5];
                data[7] = charArray[6];
                break;
            case 8:
                return charArray;
        }
        return data;
    }
}
