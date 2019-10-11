package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DudeAdapter extends ArrayAdapter<Dude> {

    private Context context;
    private int resourceID;
    private FragmentManager fragmentManager;
    private final String KEY_FRAGMENT = "dudeFragment";

    public DudeAdapter(Context context, int resource, List<Dude> objects, FragmentManager fm) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.fragmentManager = fm;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        DudeAdapter.ViewHolder viewHolder;
        Dude dude = getItem(position);
        final int idNumber = dude.getIdNumber();
        String idString = String.valueOf(dude.getIdNumber());
        String dateString = dude.getDateString();
        String descr = dude.getDescription();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new DudeAdapter.ViewHolder();
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.idNumberTextView = (TextView) convertView.findViewById(R.id.list_item_textView_idnumber);
            viewHolder.descriptionHeaderTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description_header);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description);
            viewHolder.gradientDescription = (TextView) convertView.findViewById(R.id.list_item_gradient_description);
            viewHolder.colorDescriptionTop = (TextView) convertView.findViewById(R.id.list_item_color_description_top);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (DudeAdapter.ViewHolder) convertView.getTag();
        }

        if (dude.getDudeType().equals(DudeType.WHORE)) {
            viewHolder.gradientDescription.setBackground(context.getResources().getDrawable(R.drawable.back_gradient_whore1));
            viewHolder.colorDescriptionTop.setBackgroundColor(context.getResources().getColor(R.color.color_pink1));
            viewHolder.descriptionHeaderTextView.setText(context.getResources().getString(R.string.list_item_whore));
        }
        else {
            viewHolder.gradientDescription.setBackground(context.getResources().getDrawable(R.drawable.back_gradient_freak1));
            viewHolder.colorDescriptionTop.setBackgroundColor(context.getResources().getColor(R.color.color_sky1));
            viewHolder.descriptionHeaderTextView.setText(context.getResources().getString(R.string.list_item_freak));
        }

        viewHolder.idNumberTextView.setText(idString);
        viewHolder.descriptionTextView.setText(descr);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                DudeFragment dudeFragment = new DudeFragment();
                Bundle args = new Bundle();
                args.putInt(Data.KEY_ARGS_INDEX, idNumber);
                dudeFragment.setArguments(args);

                int count = fragmentManager.getBackStackEntryCount();

                if (count == 0) {
                    ft.add(R.id.container_main, dudeFragment, KEY_FRAGMENT);
                    ft.addToBackStack(KEY_FRAGMENT);
                }
                else {
                    ft.replace(R.id.container_main, dudeFragment, KEY_FRAGMENT);
                }
                ft.commit();
            }
        });

        return convertView;

    }


    public class ViewHolder {
        TextView idNumberTextView;
        TextView dateTextView;
        TextView descriptionHeaderTextView;
        TextView descriptionTextView;
        TextView gradientDescription;
        TextView colorDescriptionTop;
    }
}
