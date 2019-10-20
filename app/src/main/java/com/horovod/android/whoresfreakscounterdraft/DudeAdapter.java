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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DudeAdapter extends ArrayAdapter<Dude> {

    private Context context;
    private int resourceID;
    private FragmentManager fragmentManager;
    private final String KEY_FRAGMENT_EDIT = "KEY_FRAGMENT_EDIT";

    public DudeAdapter(Context context, int resource, List<Dude> objects, FragmentManager fm) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.fragmentManager = fm;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        DudeAdapter.ViewHolder viewHolder;
        Dude dude = getItem(position);
        int spinnerSelectedPosition = dude.getSpinnerSelectedPosition();
        String spinnerSelected;
        String descr = dude.getDescription();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new DudeAdapter.ViewHolder();
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.colorPosition = (TextView) convertView.findViewById(R.id.list_item_color_idnumber);
            viewHolder.positionTextView = (TextView) convertView.findViewById(R.id.list_item_textView_idnumber);
            viewHolder.headerTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description_header);
            viewHolder.colorDescription = (TextView) convertView.findViewById(R.id.list_item_color_description);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description);
            viewHolder.deleteCrossImageView = (ImageView) convertView.findViewById(R.id.list_item_delete_cross);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (DudeAdapter.ViewHolder) convertView.getTag();
        }

        if (dude.getDudeType().equals(DudeType.WHORE.toString())) {
            spinnerSelected = getContext().getResources().getStringArray(R.array.whores_string_array)[spinnerSelectedPosition];
            viewHolder.colorPosition.setBackground(context.getResources().getDrawable(R.drawable.list_item_color_counter_whore));
            viewHolder.positionTextView.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            viewHolder.colorDescription.setBackground(context.getResources().getDrawable(R.drawable.list_item_color_whore));
            viewHolder.headerTextView.setTextColor(context.getResources().getColor(R.color.colorOrangeDark));
            viewHolder.headerTextView.setText(context.getResources().getString(R.string.list_item_whore));
            viewHolder.deleteCrossImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_close_red_24dp));
        }
        else {
            spinnerSelected = getContext().getResources().getStringArray(R.array.freaks_string_array)[spinnerSelectedPosition];
            viewHolder.colorPosition.setBackground(context.getResources().getDrawable(R.drawable.list_item_color_counter_freak));
            viewHolder.positionTextView.setTextColor(context.getResources().getColor(R.color.colorBlueGrayLight));
            viewHolder.colorDescription.setBackground(context.getResources().getDrawable(R.drawable.list_item_color_freak));
            viewHolder.headerTextView.setTextColor(context.getResources().getColor(R.color.colorBlueGrayDark));
            viewHolder.headerTextView.setText(context.getResources().getString(R.string.list_item_freak));
            viewHolder.deleteCrossImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_close_black_24dp));
        }

        int size = Data.getDudes().size();
        viewHolder.positionTextView.setText(String.valueOf(size - position));
        viewHolder.descriptionTextView.setText(spinnerSelected + "\n" + descr);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                DudeFragment dudeFragment = new DudeFragment();
                Bundle args = new Bundle();
                args.putInt(Data.KEY_IDNUMBER, position);
                dudeFragment.setArguments(args);

                int count = fragmentManager.getBackStackEntryCount();

                if (count == 0) {
                    ft.add(R.id.container_main, dudeFragment, KEY_FRAGMENT_EDIT);
                    ft.addToBackStack(KEY_FRAGMENT_EDIT);
                }
                else {
                    ft.replace(R.id.container_main, dudeFragment, KEY_FRAGMENT_EDIT);
                }
                ft.commit();
            }
        });

        viewHolder.deleteCrossImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogDelete dialog = new AlertDialogDelete();
                dialog.setCancelable(false);
                Bundle args = new Bundle();
                args.putInt(Data.KEY_IDNUMBER, position);
                dialog.setArguments(args);
                dialog.show(fragmentManager, Data.KEY_DELETE_DUDE);

            }
        });

        return convertView;

    }


    public class ViewHolder {
        TextView colorPosition;
        TextView positionTextView;
        TextView colorDescription;
        TextView descriptionTextView;
        TextView headerTextView;
        ImageView deleteCrossImageView;
    }
}
