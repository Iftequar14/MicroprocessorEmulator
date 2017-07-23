package com.microprocessoremulator;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewSubprocedureDialog extends DialogFragment {

    DialogCommunicator dialogCommunicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dialogCommunicator = (DialogCommunicator) activity;
    }

    EditText address;
    Button done, cancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_subprocedure_dialog, null);

        address = (EditText) view.findViewById(R.id.etMemoryAddress);
        done = (Button) view.findViewById(R.id.bGo);
        cancel = (Button) view.findViewById(R.id.bMDCancel);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = true;
                String memAddress = address.getText().toString();
                if (MyHex.invalidate(memAddress)) {
                    for (int i = 0; i < SubprogramFragment.subprogramsAddress.size(); i++) {
                        if (SubprogramFragment.subprogramsAddress.get(i).equals(memAddress)) {
                            checked = false;
                            break;
                        }
                    }
                    if (checked) {
                        Log.d("DEBUG-MY-APP", "Returning data back from dialog...");
                        dialogCommunicator.respond(memAddress, true);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Entered Memory Address is in use. Try enter different memory address.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid Memory Address", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    interface DialogCommunicator {
        void respond(String memoryAddress, boolean result);
    }
}
