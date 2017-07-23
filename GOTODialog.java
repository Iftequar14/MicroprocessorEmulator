package com.microprocessoremulator;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GOTODialog extends DialogFragment {

    SeekCommunication communication;
    public GOTODialog() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communication = (SeekCommunication) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_gotodialog,null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button go = (Button) view.findViewById(R.id.bGo);
        Button cancel = (Button) view.findViewById(R.id.bgoCancel);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) view.findViewById(R.id.dialogETGoto);
                String data = String.valueOf(et.getText());
                if(MyHex.invalidate(data) && et.getText().length() == 4) {
                    communication.gotoLocation(Integer.parseInt(data, 16));
                    dismiss();
                } else if (et.getText().length() != 4) {
                    Toast.makeText(getActivity(), "Enter Memory Location of 4 Bytes", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to find "+data+" Memory Location", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    interface SeekCommunication {
        public void gotoLocation(int location);
    }
}
