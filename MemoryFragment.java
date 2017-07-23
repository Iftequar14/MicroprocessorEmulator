package com.microprocessoremulator;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MemoryFragment extends Fragment {

    public static int currentMemoryLocation = 0;
    ListView memoryListview;
    String[] addr = new String[65536];

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        for (int i = 0; i < 65536; i++) {
            if (i < 16)
                addr[i] = ("000" + Integer.toHexString(i)).toUpperCase();
            else if (i < 256)
                addr[i] = ("00" + Integer.toHexString(i)).toUpperCase();
            else if (i < 4096)
                addr[i] = ("0" + Integer.toHexString(i)).toUpperCase();
            else
                addr[i] = Integer.toHexString(i).toUpperCase();
        }

        memoryListview = (ListView) view.findViewById(R.id.memoryListView);
        MyAdapter adapter = new MyAdapter(getActivity());
        memoryListview.setAdapter(adapter);

        return view;
    }

    public void search() {
        //Toast.makeText(getContext(), "Search code is in Construction.", Toast.LENGTH_SHORT).show();
        GOTODialog dialog = new GOTODialog();
        dialog.show(getActivity().getFragmentManager(), "GOTO");
    }

    public void gotoLocation(int location) {
        memoryListview.setSelection(location);
    }

    class MyAdapter extends ArrayAdapter {
        Context context;

        public MyAdapter(Context context) {
            super(context, R.layout.memory_location_single_row, R.id.mAddress, addr);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            MyHolder holder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.memory_location_single_row, parent, false);
                holder = new MyHolder(row);
                row.setTag(holder);
            } else {
                holder = (MyHolder) row.getTag();
            }

            row.setId(position);
            holder.mAddr.setText(addr[position]);
            if (EmulatorFragment.memoryAddress[position] == null)
                holder.mData.setText("00");
            else
                holder.mData.setText(MyHex.fixHexDataLength(EmulatorFragment.memoryAddress[position]));

            holder.mData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentMemoryLocation = position;
                    FragmentManager manager = getActivity().getFragmentManager();
                    MemoryDataDialog dialog = new MemoryDataDialog();
                    dialog.setCancelable(false);
                    dialog.show(manager, "MyDialog");
                }
            });
            return row;
        }
    }

    class MyHolder {
        TextView mAddr, mData;

        public MyHolder(View v) {
            mAddr = (TextView) v.findViewById(R.id.mAddress);
            mData = (TextView) v.findViewById(R.id.mData);
        }
    }
}
