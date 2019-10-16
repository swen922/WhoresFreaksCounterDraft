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
    private final String KEY_FRAGMENT = "dudeFragment";

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
        String spinnerString = dude.getSpinnerSelected();
        String descr = dude.getDescription();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new DudeAdapter.ViewHolder();
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.positionNumberTextView = (TextView) convertView.findViewById(R.id.list_item_textView_idnumber);
            viewHolder.headerTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description_header);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.list_item_textView_description);
            viewHolder.gradientDescription = (TextView) convertView.findViewById(R.id.list_item_gradient_description);
            viewHolder.closeCrossImageView = (ImageView) convertView.findViewById(R.id.list_item_close_cross);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (DudeAdapter.ViewHolder) convertView.getTag();
        }

        if (dude.getDudeType().equals(DudeType.WHORE)) {
            viewHolder.gradientDescription.setBackground(context.getResources().getDrawable(R.drawable.background_whore1));
            viewHolder.headerTextView.setText(context.getResources().getString(R.string.list_item_whore));
        }
        else {
            viewHolder.gradientDescription.setBackground(context.getResources().getDrawable(R.drawable.background_freak1));
            viewHolder.headerTextView.setText(context.getResources().getString(R.string.list_item_freak));
        }

        viewHolder.positionNumberTextView.setText(String.valueOf(position + 1));
        viewHolder.descriptionTextView.setText(spinnerString + "\n" + descr);

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
                    ft.add(R.id.container_main, dudeFragment, KEY_FRAGMENT);
                    ft.addToBackStack(KEY_FRAGMENT);
                }
                else {
                    ft.replace(R.id.container_main, dudeFragment, KEY_FRAGMENT);
                }
                ft.commit();
            }
        });

        viewHolder.closeCrossImageView.setOnClickListener(new View.OnClickListener() {
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

        //Log.i("LOGGGGINGGG |||| ", viewHolder.headerTextView.getText().toString() + " - " + viewHolder.positionNumberTextView.getText().toString());

        return convertView;

    }


    public class ViewHolder {
        TextView positionNumberTextView;
        ImageView closeCrossImageView;
        TextView headerTextView;
        TextView descriptionTextView;
        TextView gradientDescription;
    }
}
