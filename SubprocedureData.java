package com.microprocessoremulator;

import java.util.ArrayList;

/**
 * Created by MOHD IMTIAZ on 28-Sep-16.
 */
public class SubprocedureData {
    ArrayList<String> address = new ArrayList<>();
    int idStartingAddresses;

    public SubprocedureData(String address, int commonId) {
        this.address.add(address);
        idStartingAddresses = commonId+11;
    }

    public void updateAddress(ArrayList<String> address) {
        this.address = address;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public int getSize() {
        return address.size();
    }
}
