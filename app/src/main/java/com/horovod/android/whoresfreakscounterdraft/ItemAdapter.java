package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<ItemList> {

    private Context context;
    private int resourceID;

    public ItemAdapter(Context context, int resource, List<ItemList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;

        Log.i("LOGGGINGGGG ===== ", "resourceID = " + resourceID );
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        ItemList itemList = getItem(position);
        String idString = String.valueOf(itemList.getIdNumber());
        String descr = itemList.getDescription();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(resourceID, parent, false);
        TextView idTextView = row.findViewById(R.id.list_item_textView_1);
        TextView descriptionTextView = row.findViewById(R.id.list_item_textView_3);

        idTextView.setText(idString);
        descriptionTextView.setText(descr);

        return row;
    }

}
