package com.microprocessoremulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SubprogramFragment extends Fragment {

    View view;
    TextView mSubprocedure;
    boolean subprocedures = false;
    ArrayList<View> subprograms = new ArrayList<>();
    public static ArrayList<SubprocedureData> subprogramsAddressArray = new ArrayList<>();
    public static ArrayList<String> subprogramsAddress = new ArrayList<>();
    LinearLayout layout;

    public static int subprogramCounter = 0;

    public SubprogramFragment() {
        // Required empty public constructor
        subprogramsAddressArray.clear();
        subprogramsAddress.clear();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subprogram, container, false);
        layout = (LinearLayout) view.findViewById(R.id.subproceduresLayout);
        mSubprocedure = (TextView) view.findViewById(R.id.tvNoSubprocedure);
        return view;
    }

   public void createNewSubprocedure() {
       android.app.FragmentManager manager = getActivity().getFragmentManager();
       NewSubprocedureDialog dialog = new NewSubprocedureDialog();
       dialog.show(manager, "dialog");
   }

    public void addSubprocedureView(String memoryAddress, boolean result) {
        memoryAddress = MyHex.fixAddressLength(memoryAddress);
        if (result && memoryAddress != null) {
            int commonId = subprogramCounter * 100;
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View subprocedureView = inflater.inflate(R.layout.fragment_subprogram_layout, null);
            subprocedureView.setId(commonId);

            TextView title = (TextView) subprocedureView.findViewById(R.id.subprogramTitle);
            //title.setId(commonId+1);
            title.setText(memoryAddress);
            LinearLayout subSno = (LinearLayout) subprocedureView.findViewById(R.id.subprogramSno);
            subSno.setId(commonId + 3);
            TextView snoAddr = new TextView(getActivity());
            snoAddr.setId(commonId + 11);
            snoAddr.setText(memoryAddress);
            snoAddr.setTextSize(16f);
            subSno.addView(snoAddr);
            EditText editor = (EditText) subprocedureView.findViewById(R.id.subprogramCode);
            editor.setId(commonId + 4);
            editor.setOnKeyListener(new MyOnKeyListener());


            if (!subprocedures) {
                mSubprocedure.setVisibility(View.GONE);
                subprocedures = true;
            }
            subprogramCounter++;
            subprogramsAddress.add(memoryAddress);
            subprograms.add(subprocedureView);
            subprogramsAddressArray.add(new SubprocedureData(memoryAddress, commonId));
            layout.addView(subprocedureView);
        }
    }

    public String[] getSubprocedureData(int location) {
        EditText et = (EditText) subprograms.get(location).findViewById((location*100)+4);
        return String.valueOf(et.getText()).split("\n");
    }

    public void setResult(int id) {
        TextView result = (TextView) subprograms.get(id).findViewById(R.id.subprogramResult);
        result.setText("Warning : RET should be the last instruction in Subprocedure");
    }

    public void clearResult() {
        for (int i=0;i<subprograms.size();i++){
            TextView tv = (TextView) subprograms.get(i).findViewById(R.id.subprogramResult);
            tv.setText("");
        }
    }

    class MyOnKeyListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int editorId = v.getId();
            View tempView = subprograms.get((editorId - 4) / 100);
            SubprocedureData tempData = subprogramsAddressArray.get((editorId - 4) / 100);
            LinearLayout snoLayout = (LinearLayout) tempView.findViewById(editorId - 1);
            ArrayList<String> lineStartingAddress = tempData.getAddress();
            TextView sno;
            int totalAddresses = tempData.getSize();
            String data, lines[];
            data = String.valueOf(((EditText) v).getText());
            lines = data.split("\n");
            int len = lines.length;
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (lineStartingAddress.size() == len && !(lines[len-1].equals(""))) {
                        sno = new TextView(getActivity());
                        sno.setId(tempData.idStartingAddresses + tempData.getSize());
                        int instSize = getSizeOfInstruction(lines[len - 1].split(" "));
                        String addr = MyHex.fixAddressLength(Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(len - 1), 16)));
                        sno.setText(addr);
                        sno.setTextSize(16f);
                        snoLayout.addView(sno);
                        //totalAddresses++;
                        lineStartingAddress.add(addr);
                        tempData.updateAddress(lineStartingAddress);
                        return false;
                    } else {
                        return true;
                    }
                }
                if (totalAddresses > len) {
                    for (int i = totalAddresses; i > len; i--) {
                        TextView tv = (TextView) snoLayout.findViewById(((tempData.idStartingAddresses + totalAddresses) - 1));
                        snoLayout.removeView(tv);
                        lineStartingAddress.remove(--totalAddresses);
                    }
                    tempData.updateAddress(lineStartingAddress);
                }
            }
            refactorSubprogram((editorId - 4));
            return false;
        }
    }

    public void refactorSubprogram(int viewId) {
        View view = subprograms.get(viewId/100);
        EditText et = (EditText) view.findViewById(viewId + 4);
        int len = String.valueOf(et.getText()).split("\n").length;
        TextView sno;
        SubprocedureData tempData = subprogramsAddressArray.get(viewId / 100);
        LinearLayout snoLayout = (LinearLayout) view.findViewById(viewId + 3);
        ArrayList<String> lineStartingAddress = tempData.getAddress();
        int totalAddresses = tempData.getSize();
        String data, lines[];
        data = String.valueOf(((EditText) view.findViewById(viewId + 4)).getText());
        lines = data.split("\n");
        //int editorId =  .getId();

        for (int i = tempData.idStartingAddresses + 1; i < (len + tempData.idStartingAddresses); i++) {
            sno = (TextView) snoLayout.findViewById(i);
            lineStartingAddress = tempData.getAddress();
            if (sno == null) {
                sno = new TextView(getActivity());
                sno.setId(totalAddresses);
                int instSize = getSizeOfInstruction(lines[i - tempData.idStartingAddresses - 1].split(" "));
                String addr = MyHex.fixAddressLength(Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(i - tempData.idStartingAddresses - 1), 16)));
                sno.setText(addr);
                sno.setTextSize(16f);
                snoLayout.addView(sno);
                totalAddresses++;
                lineStartingAddress.add(addr);
            } else {
                int instSize = getSizeOfInstruction(lines[i - tempData.idStartingAddresses -1].split(" "));
                String addr = MyHex.fixAddressLength(Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(i - tempData.idStartingAddresses - 1), 16)));
                sno.setText(addr);
                lineStartingAddress.set((i - tempData.idStartingAddresses), addr);
            }
        }
    }

    private int getSizeOfInstruction(String[] instruction) {

        switch (instruction[0]) {
            case "ADD":
            case "ADC":
            case "DAD":
            case "SUB":
            case "SBB":
            case "INR":
            case "INX":
            case "DCR":
            case "DCX":
            case "CMP":
            case "ANA":
            case "RAR":
            case "RAL":
            case "MOV":
            case "STAX":
            case "LDAX":
            case "XCHG":
            case "HLT":
            case "CMA":
            case "CMC":
            case "STC":
                return 1;

            case "MVI":
            case "ADI":
            case "ACI":
            case "CPI":
            case "SUI":
            case "SBI":
                return 2;

            case "STA":
            case "SHLD":
            case "LDA":
            case "LHLD":
            case "LXI":
            case "JMP":
            case "JC":
            case "JNC":
            case "JP":
            case "JM":
            case "JZ":
            case "JNZ":
            case "JPE":
            case "JPO":
                return 3;

            default:
                return 0;
        }
    }
}
