package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerEditItemFragment extends Fragment {

    private TextView background;
    private TextView topColorYexyView;
    private TextView headerTextView;
    private TextView itemEditText;
    private Button cancelButton;
    private Button saveButton;

    private boolean hasTouching = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.spinner_edit_item_fragment, container, false);
        Data.spinnerEditItemFragment = this;

        background = rootView.findViewById(R.id.spinner_edit_item_fragment_background);
        topColorYexyView = rootView.findViewById(R.id.spinner_edit_item_color_top);
        headerTextView = rootView.findViewById(R.id.spinner_edit_item_textview_header);
        itemEditText = rootView.findViewById(R.id.spinner_edit_item_fragment_edittext);
        cancelButton = rootView.findViewById(R.id.spinner_edit_item_fragment_cancel_button);
        saveButton = rootView.findViewById(R.id.spinner_edit_item_fragment_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nothin to do - prevent clicks on background View through our SpinnerEditItemFragment
            }
        });

        Bundle args = getArguments();
        String dudeTypeString = "";
        int itemPosition = 0;
        String oldItemString = "";
        if (args != null) {
            dudeTypeString = args.getString(Data.KEY_DUDETYPE);
            itemPosition = args.getInt(Data.KEY_IDNUMBER, 0);
        }

        if (dudeTypeString != null && !dudeTypeString.isEmpty()) {
            if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_whore));
                topColorYexyView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_whore));
                if (Data.getWhoresSpinner().isEmpty()) {
                    List<String> tmpList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.whores_string_array)));
                    oldItemString = tmpList.get(itemPosition);
                }
                else {
                    oldItemString = Data.getWhoresSpinner().get(itemPosition);
                }

            }
            else {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_freak));
                headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_freak));
                if (Data.getFreaksSpinner().isEmpty()) {
                    List<String> tmpList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.freaks_string_array)));
                    oldItemString = tmpList.get(itemPosition);
                }
                else {
                    oldItemString = Data.getFreaksSpinner().get(itemPosition);
                }
            }

            itemEditText.setText(oldItemString);

            itemEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (!hasTouching) {
                        itemEditText.setText("");
                        hasTouching = true;
                    }
                    return false;
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.spinnerEditItemFragment).commit();
                    closeKeyboard();
                    Data.spinnerEditItemFragment = null;
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TODO


                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.spinnerEditItemFragment).commit();
                    closeKeyboard();
                    Data.spinnerEditItemFragment = null;
                }
            });

        }

        return rootView;
    }


    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (itemEditText != null) {
            imm.hideSoftInputFromWindow(itemEditText.getWindowToken(), 0);
        }
    }


}
