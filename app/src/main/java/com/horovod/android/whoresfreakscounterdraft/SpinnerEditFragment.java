package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerEditFragment extends Fragment {

    private TextView background;
    private TextView topColorYexyView;
    private TextView headerTextView;
    private TextView itemEditText0;
    private TextView itemEditText1;
    private TextView itemEditText2;
    private TextView itemEditText3;
    private TextView itemEditText4;
    private TextView itemEditText5;
    private TextView itemEditText6;
    private Button cancelButton;
    private Button saveButton;

    private List<String> spinnerItemsList;
    private String dudeTypeString = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.spinner_edit_fragment, container, false);
        Data.spinnerEditFragment = this;

        background = rootView.findViewById(R.id.spinner_edit_fragment_background);
        topColorYexyView = rootView.findViewById(R.id.spinner_edit_color_top);
        headerTextView = rootView.findViewById(R.id.spinner_edit_textview_header);

        itemEditText0 = rootView.findViewById(R.id.spinner_edit_fragment_edittext0);
        itemEditText1 = rootView.findViewById(R.id.spinner_edit_fragment_edittext1);
        itemEditText2 = rootView.findViewById(R.id.spinner_edit_fragment_edittext2);
        itemEditText3 = rootView.findViewById(R.id.spinner_edit_fragment_edittext3);
        itemEditText4 = rootView.findViewById(R.id.spinner_edit_fragment_edittext4);
        itemEditText5 = rootView.findViewById(R.id.spinner_edit_fragment_edittext5);
        itemEditText6 = rootView.findViewById(R.id.spinner_edit_fragment_edittext6);
        cancelButton = rootView.findViewById(R.id.spinner_edit_fragment_cancel_button);
        saveButton = rootView.findViewById(R.id.spinner_edit_fragment_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing to do - prevent clicks on background View through our SpinnerEditFragment
            }
        });



        Bundle args = getArguments();
        if (args != null) {
            dudeTypeString = args.getString(Data.KEY_DUDETYPE);
        }
        if (dudeTypeString != null && !dudeTypeString.isEmpty()) {
            if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_whore));
                topColorYexyView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_whore));
                if (Data.getWhoresSpinner().isEmpty()) {
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.whores_string_array)));
                }
                else {
                    spinnerItemsList = new ArrayList<>(Data.getWhoresSpinner());
                }
            }
            else {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_freak));
                headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_freak));
                if (Data.getWhoresSpinner().isEmpty()) {
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.freaks_string_array)));
                }
                else {
                    spinnerItemsList = new ArrayList<>(Data.getFreaksSpinner());
                }
            }

            itemEditText0.setText(spinnerItemsList.get(0));
            itemEditText1.setText(spinnerItemsList.get(1));
            itemEditText2.setText(spinnerItemsList.get(2));
            itemEditText3.setText(spinnerItemsList.get(3));
            itemEditText4.setText(spinnerItemsList.get(4));
            itemEditText5.setText(spinnerItemsList.get(5));
            itemEditText6.setText(spinnerItemsList.get(6));

            View.OnClickListener editListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    int position = 0;
                    position = spinnerItemsList.indexOf(textView.getText().toString());
                    openEditItemFragment(position);
                }
            };

            itemEditText0.setOnClickListener(editListener);
            itemEditText1.setOnClickListener(editListener);
            itemEditText2.setOnClickListener(editListener);
            itemEditText3.setOnClickListener(editListener);
            itemEditText4.setOnClickListener(editListener);
            itemEditText5.setOnClickListener(editListener);
            itemEditText6.setOnClickListener(editListener);

        }

        return rootView;
    }


    private void openEditItemFragment(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Data.spinnerEditItemFragment = new SpinnerEditItemFragment();

        Bundle args = new Bundle();
        args.putInt(Data.KEY_IDNUMBER, position);
        args.putString(Data.KEY_DUDETYPE, dudeTypeString);
        Data.spinnerEditItemFragment.setArguments(args);

        ft.add(R.id.container_main, Data.spinnerEditItemFragment, Data.KEY_SPINNER_EDIT_ITEM);
        ft.commit();
    }


}
