package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PropertySpinnerAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private String[] objects;
    private int textViewID;

    public PropertySpinnerAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
        this.textViewID = textViewResourceId;
    }

    public PropertySpinnerAdapter(Context context, int resource, int textViewResourceId, String[] objects, LayoutInflater inflater) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
        this.inflater = inflater;
        this.textViewID = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView textView = row.findViewById(textViewID);
        textView.setText(objects[position]);
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row_dropdown, parent, false);
        TextView textView = row.findViewById(R.id.spinner_row_textview_dropdown);
        textView.setText(objects[position]);
        return row;
    }
}
