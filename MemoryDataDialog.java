package com.microprocessoremulator;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryDataDialog extends DialogFragment {

    TextView address;
    EditText data;
    Button previous, next, done;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View dialog = inflater.inflate(R.layout.memory_data_dialog, null);
        previous = (Button) dialog.findViewById(R.id.bPrevious);
        next = (Button) dialog.findViewById(R.id.bgoCancel);
        done = (Button) dialog.findViewById(R.id.bGo);
        address = (TextView) dialog.findViewById(R.id.dialogAddressId);
        data = (EditText) dialog.findViewById(R.id.dialogDataId);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        final int[] memAddress = {MemoryFragment.currentMemoryLocation};

        String hexMemAddress = null;
        if (memAddress[0] < 16)
            hexMemAddress = ("000" + Integer.toHexString(memAddress[0])).toUpperCase();
        else if (memAddress[0] < 256)
            hexMemAddress = ("00" + Integer.toHexString(memAddress[0])).toUpperCase();
        else if (memAddress[0] < 4096)
            hexMemAddress = ("0" + Integer.toHexString(memAddress[0])).toUpperCase();

        address.setText(hexMemAddress);
        if (EmulatorFragment.memoryAddress[memAddress[0]] == null)
            data.setText("00");
        else
            data.setText(EmulatorFragment.memoryAddress[memAddress[0]]);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memAddress[0] == 0) {
                    Toast.makeText(getActivity(), "Starting Memory Address", Toast.LENGTH_SHORT).show();
                } else {
                    if (MyHex.invalidate(String.valueOf(data.getText()))) {
                        EmulatorFragment.memoryAddress[memAddress[0]] = MyHex.fixHexDataLength(String.valueOf(data.getText()));
                        memAddress[0]--;
                        refactorDialog(memAddress[0]);
                    } else {
                        data.setFocusable(true);
                        Toast.makeText(getActivity(), "Invalid Hexadecimal Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memAddress[0] == 4095) {
                    Toast.makeText(getActivity(), "Last Memory Address", Toast.LENGTH_SHORT).show();
                } else {
                    if (MyHex.invalidate(String.valueOf(data.getText()))) {
                        EmulatorFragment.memoryAddress[memAddress[0]] = MyHex.fixHexDataLength(String.valueOf(data.getText()));
                        memAddress[0]++;
                        refactorDialog(memAddress[0]);
                    } else {
                        data.setFocusable(true);
                        Toast.makeText(getActivity(), "Invalid Hexadecimal Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyHex.invalidate(String.valueOf(data.getText()))) {
                    EmulatorFragment.memoryAddress[memAddress[0]] = MyHex.fixHexDataLength(String.valueOf(data.getText()));
                    dismiss();
                } else {
                    data.setFocusable(true);
                    Toast.makeText(getActivity(), "Invalid Hexadecimal Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dialog;

    }

    private void refactorDialog(int memAddres) {
        TextView addr = (TextView) getDialog().findViewById(R.id.dialogAddressId);
        EditText data = (EditText) getDialog().findViewById(R.id.dialogDataId);
        addr.setText(MyHex.toHex(memAddres));
        if (EmulatorFragment.memoryAddress[memAddres] == null)
            data.setText("00");
        else
            data.setText(EmulatorFragment.memoryAddress[memAddres]);
        data.selectAll();
    }

}
