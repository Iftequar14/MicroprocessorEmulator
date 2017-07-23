package com.microprocessoremulator;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microprocessoremulator.opcode.ACI;
import com.microprocessoremulator.opcode.ADC;
import com.microprocessoremulator.opcode.ADD;
import com.microprocessoremulator.opcode.ADI;
import com.microprocessoremulator.opcode.ANA;
import com.microprocessoremulator.opcode.CMA;
import com.microprocessoremulator.opcode.CMC;
import com.microprocessoremulator.opcode.CMP;
import com.microprocessoremulator.opcode.CPI;
import com.microprocessoremulator.opcode.DAD;
import com.microprocessoremulator.opcode.DCR;
import com.microprocessoremulator.opcode.DCX;
import com.microprocessoremulator.opcode.INR;
import com.microprocessoremulator.opcode.INX;
import com.microprocessoremulator.opcode.LDA;
import com.microprocessoremulator.opcode.LDAX;
import com.microprocessoremulator.opcode.LHLD;
import com.microprocessoremulator.opcode.LXI;
import com.microprocessoremulator.opcode.MOV;
import com.microprocessoremulator.opcode.MVI;
import com.microprocessoremulator.opcode.RAL;
import com.microprocessoremulator.opcode.RAR;
import com.microprocessoremulator.opcode.SBB;
import com.microprocessoremulator.opcode.SBI;
import com.microprocessoremulator.opcode.SHLD;
import com.microprocessoremulator.opcode.STA;
import com.microprocessoremulator.opcode.STAX;
import com.microprocessoremulator.opcode.STC;
import com.microprocessoremulator.opcode.SUB;
import com.microprocessoremulator.opcode.SUI;
import com.microprocessoremulator.opcode.XCHG;

import java.util.ArrayList;
import java.util.List;

public class EmulatorFragment extends Fragment {

    int totalAddresses = 0;
    int subprocedureExecution = 0;
    String startingAddress;
    EditText word;
    TextView result, sno;
    List<String> lineStartingAddress = new ArrayList<String>();
    LinearLayout snoLayout;

    public static int[] flags = new int[5];
    public static String[] reg = new String[7];
    public static String[] memoryAddress = new String[65536];

    public static final int FLAG_CARRY = 0;
    public static final int FLAG_PARITY = 1;
    public static final int FLAG_AUXILIARY_CARRY = 2;
    public static final int FLAG_ZERO = 3;
    public static final int FLAG_SIGN = 4;

    public EmulatorFragment() {
        for (int i = 0; i < 65536; i++)
            memoryAddress[i] = "00";
        clearReisters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        GlobalFont.replaceDefaultFont(getActivity(), "DEFAULT", "fonts/storopia.ttf");
        final View view = inflater.inflate(R.layout.fragment_emulator, container, false);
        snoLayout = (LinearLayout) view.findViewById(R.id.serialNumberLayout);
        LinearLayout mContainer = (LinearLayout) view.findViewById(R.id.layoutContainer);

        startingAddress = "8000";
        sno = new TextView(getActivity());
        sno.setId(totalAddresses);
        lineStartingAddress.add(startingAddress);
        sno.setText(startingAddress);
        sno.setTextSize(16f);
        snoLayout.addView(sno);
        totalAddresses++;

        word = (EditText) view.findViewById(R.id.etWord);
        word.setMaxHeight(mContainer.getHeight());
        word.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String data, lines[];
                data = String.valueOf(word.getText());
                lines = data.split("\n");
                int len = lines.length;
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (lineStartingAddress.size() == len && !(lines[len-1].equals(""))) {
                            sno = new TextView(getActivity());
                            sno.setId(totalAddresses);
                            int instSize = getSizeOfInstruction(lines[len - 1].split(" "));
                            String addr = Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(len - 1), 16)).toUpperCase();
                            sno.setText(addr);
                            sno.setTextSize(16f);
                            snoLayout.addView(sno);
                            totalAddresses++;
                            lineStartingAddress.add(addr);
                            return false;
                        } else {
                            return true;
                        }
                    }

                    if (totalAddresses > len) {
                        for (int i = totalAddresses; i > len; i--) {
                            TextView tv = (TextView) snoLayout.findViewById((totalAddresses - 1));
                            snoLayout.removeView(tv);
                            lineStartingAddress.remove(--totalAddresses);
                        }
                    }
                    return false;
                }
                //Refactoring Serial Number...
                refactor();
                return false;
            }
        });

        result = (TextView) view.findViewById(R.id.tvResult);
        return view;
    }

    public boolean executeMethod(String[] content) {
        String[] data1 = content;
        for (int i = 0; i < data1.length; i++) {
            String[] datas = data1[i].split(" ");
            String opcode = datas[0];
            String[] operands = new String[2];
            if (datas.length == 2)
                operands = datas[1].split(",");
            try {
                if (opcode.equals(""))
                    throw new Exception("Nothing to Execute");
                switch (opcode) {
                    case "MVI":
                        if (operands[0] != null && operands[1] != null) {
                            MVI.execute(operands[0], operands[1]);
                        } else {
                            throw new Exception("Please provide appropriate operands.");
                        }
                        break;
                    case "ADD":
                        if (operands[0] != null) {
                            ADD.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "ADC":
                        if (operands[0] != null) {
                            ADC.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "ADI":
                        if (operands[0] != null) {
                            ADI.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "ACI":
                        if (operands[0] != null) {
                            ACI.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "DAD":
                        if (operands[0] != null) {
                            DAD.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "SUB":
                        if (operands[0] != null) {
                            SUB.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "SBB":
                        if (operands[0] != null) {
                            SBB.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "SUI":
                        if (operands[0] != null) {
                            SUI.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "SBI":
                        if (operands[0] != null) {
                            SBI.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "INR":
                        if (operands[0] != null) {
                            INR.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "INX":
                        if (operands[0] != null) {
                            INX.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "DCR":
                        if (operands[0] != null) {
                            DCR.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "DCX":
                        if (operands[0] != null) {
                            DCX.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "CMP":
                        if (operands[0] != null) {
                            CMP.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "CPI":
                        if (operands[0] != null) {
                            CPI.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "CMA":
                        CMA.execute();
                        break;
                    case "CMC":
                        CMC.execute();
                        break;
                    case "STC":
                        STC.execute();
                        break;
                    case "ANA":
                        if (operands[0] != null) {
                            ANA.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "RAR":
                        RAR.execute();
                        break;
                    case "RAL":
                        RAL.execute();
                        break;
                    case "JMP":
                        if (operands[0] != null && MyHex.invalidate(operands[0])) {
                            if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                for (int j = 0; j < lineStartingAddress.size(); j++) {
                                    if (operands[0].equals(lineStartingAddress.get(j))) {
                                        i = (j - 1);
                                        break;
                                    } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                        i = j;
                                        break;
                                    }
                                }
                            } else {
                                boolean found = false;
                                for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                    if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                        found = true;
                                        subprocedureExecution++;
                                        Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                        if (!executeMethod(((SubprogramFragment) page).getSubprocedureData(x))) {
                                            ((SubprogramFragment) page).setResult(x);
                                            Snackbar.make(getView(), "EXECUTED PARTIALLY", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            result.setText("Executed partially because execution didn't returned from " + operands[0] + " subprocedure");
                                            result.setVisibility(View.VISIBLE);
                                            return false;
                                        }
                                        break;
                                    }
                                }
                                if (!found) {
                                    throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                }
                            }
                        } else {
                            throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                        }
                        break;
                    case "JC":
                        if (flags[FLAG_CARRY] == 1) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JNC":
                        if (flags[FLAG_CARRY] == 0) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JP":
                        if (flags[FLAG_SIGN] == 0) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JM":
                        if (flags[FLAG_SIGN] == 1) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JZ":
                        String[] temp = data1[i-1].split(" ");
                        if (getRegisterValue(temp[1]) == 0) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JNZ":
                        String[] tmp = data1[i-1].split(" ");
                        if (getRegisterValue(tmp[1]) != 0) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JPE":
                        if (flags[FLAG_PARITY] == 0) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "JOE":
                        if (flags[FLAG_PARITY] == 1) {
                            if (operands[0] != null && MyHex.invalidate(operands[0])) {
                                if (Integer.parseInt(operands[0], 16) >= Integer.parseInt(lineStartingAddress.get(0), 16) && Integer.parseInt(operands[0], 16) <= Integer.parseInt(lineStartingAddress.get(lineStartingAddress.size() - 1), 16)) {
                                    for (int j = 0; j < lineStartingAddress.size(); j++) {
                                        if (operands[0].equals(lineStartingAddress.get(j))) {
                                            i = (j - 1);
                                            break;
                                        } else if (Integer.parseInt(operands[0], 16) > Integer.parseInt(lineStartingAddress.get(j), 16) && Integer.parseInt(operands[0], 16) < Integer.parseInt(lineStartingAddress.get(j + 1), 16)) {
                                            i = j;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean found = false;
                                    for (int x = 0; x < SubprogramFragment.subprogramsAddress.size(); x++) {
                                        if (SubprogramFragment.subprogramsAddress.get(x).equals(operands[0])) {
                                            found = true;
                                            subprocedureExecution++;
                                            Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                                            executeMethod(((SubprogramFragment) page).getSubprocedureData(x));
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        throw new Exception("Unable to find " + operands[0] + " Subprogram");
                                    }
                                }
                            } else {
                                throw new Exception("Unable to recognise given memory address '" + operands[0] + "'");
                            }
                        }
                        break;
                    case "RET":
                        subprocedureExecution--;
                        return true;
                    case "MOV":
                        if (operands[0] != null && operands[1] != null) {
                            MOV.execute(operands[0], operands[1]);
                        } else {
                            throw new Exception("Please provide appropriate operands.");
                        }
                        break;
                    case "STA":
                        if (operands[0] != null) {
                            STA.execute(reg[0], operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "STAX":
                        if (operands[0] != null) {
                            STAX.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "SHLD":
                        if (operands[0] != null) {
                            SHLD.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "LDA":
                        if (operands[0] != null) {
                            LDA.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "LDAX":
                        if (operands[0] != null) {
                            LDAX.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "LHLD":
                        if (operands[0] != null) {
                            LHLD.execute(operands[0]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "LXI":
                        if (operands[0] != null && operands[1] != null) {
                            LXI.execute(operands[0], operands[1]);
                        } else {
                            throw new Exception("No Operands Provided.");
                        }
                        break;
                    case "XCHG":
                        XCHG.execute();
                        break;
                    case "HLT":
                        i = data1.length + 1;
                        break;
                    default:
                        throw new Exception("Unable to identify '" + opcode + "' opcode");
                }
            } catch (Exception e) {
                result.setText("ERROR OCCURRED AT LINE  : " + (i + 1) + "\nREASON : " + e.getMessage());
                result.setVisibility(View.VISIBLE);
                return false;
            }
            if( i==data1.length-1 && subprocedureExecution != 0 && (!(opcode.equals("RET")))) {
                return false;
            }
        }
        return true;
    }

    private int getRegisterValue(String registerName) {
        registerName = registerName.toUpperCase();
        switch (registerName) {
            case "A":
                return Integer.parseInt(reg[0],16);
            case "B":
                return Integer.parseInt(reg[1],16);
            case "C":
                return Integer.parseInt(reg[2],16);
            case "D":
                return Integer.parseInt(reg[3],16);
            case "E":
                return Integer.parseInt(reg[4],16);
            case "H":
                return Integer.parseInt(reg[5],16);
            case "L":
                return Integer.parseInt(reg[6],16);
            default:
                return 0;
        }
    }

    public void execute() {
        //For hiding keyboard...
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
        refactor();
        clearReisters();
        subprocedureExecution=0;
        Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
        ((SubprogramFragment) page).clearResult();
        result.setText("");
        result.setVisibility(View.INVISIBLE);
        String data = String.valueOf(word.getText());
        String[] data1 = data.split("\n");
        if (executeMethod(data1)) {
            Snackbar.make(getView(), "EXECUTED WITHOUT ANY ERRORS.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            //Display the errors or warnings...
            //result.setText();
        }
    }

    private void refactor() {
        String lines[] = String.valueOf(word.getText()).split("\n");
        int len = lines.length;
        for (int i = 1; i < len; i++) {
            sno = (TextView) snoLayout.findViewById(i);
            if (sno == null) {
                sno = new TextView(getActivity());
                sno.setId(totalAddresses);
                int instSize = getSizeOfInstruction(lines[i - 1].split(" "));
                String addr = Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(i - 1), 16)).toUpperCase();
                sno.setText(addr);
                sno.setTextSize(16f);
                snoLayout.addView(sno);
                totalAddresses++;
                lineStartingAddress.add(addr);
            } else {
                int instSize = getSizeOfInstruction(lines[i - 1].split(" "));
                String addr = Integer.toHexString(instSize + Integer.parseInt(lineStartingAddress.get(i - 1), 16)).toUpperCase();
                sno.setText(addr);
                lineStartingAddress.set(i, addr);
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

    private void clearReisters() {
        for (int j = 0; j < 7; j++)
            reg[j] = "00";
        clearFlags();
    }

    private void clearFlags() {
        for (int j = 0; j < 5; j++)
            flags[j] = 0;
    }
}
