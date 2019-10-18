package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PropertySpinnerAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private List<String> objectsList;
    private int textViewID;
    private DudeType myDudeType;


    public PropertySpinnerAdapter(Context context, int resource, int textViewResourceId, List<String> obj, LayoutInflater inflater, DudeType myDudeType) {
        super(context, resource, textViewResourceId, obj);
        this.inflater = inflater;
        this.objectsList = new ArrayList<>(obj);
        this.textViewID = textViewResourceId;
        this.myDudeType = myDudeType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        TextView textView = null;
        if (myDudeType.equals(DudeType.WHORE)) {
            row = inflater.inflate(R.layout.spinner_row_whore, parent, false);
            textView = row.findViewById(textViewID);
        }
        else {
            row = inflater.inflate(R.layout.spinner_row_freak, parent, false);
            textView = row.findViewById(textViewID);
        }
        textView.setText(objectsList.get(position));
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row;
        TextView textView;
        if (myDudeType.equals(DudeType.WHORE)) {
            row = inflater.inflate(R.layout.spinner_row_whore_dropdown, parent, false);
            textView = row.findViewById(R.id.spinner_row_textview_dropdown_whore);
        }
        else {
            row = inflater.inflate(R.layout.spinner_row_freak_dropdown, parent, false);
            textView = row.findViewById(R.id.spinner_row_textview_dropdown_freak);
        }
        textView.setText(objectsList.get(position));
        return row;
    }
}
