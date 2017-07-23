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

public class ExitDialog extends DialogFragment {

    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog =  inflater.inflate(R.layout.exit_dialog, container, false);
        Button yes,no;
        yes = (Button) dialog.findViewById(R.id.bYes);
        no = (Button) dialog.findViewById(R.id.bNo);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.dialogResult(true);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                communicator.dialogResult(false);
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    interface  Communicator {
        public void dialogResult(boolean exit);
    }
}
