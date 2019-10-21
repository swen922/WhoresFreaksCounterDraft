package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateFragment extends Fragment {

    private TextView background;
    private TextView headerColor;
    private TextView headerTextView;
    private TextView spinnerPromptTextView;
    private Spinner propertySpinner;
    private TextView infoPromptTextView;
    private EditText descriptionEditText;
    private Button cancelButton;
    private Button saveButton;

    private String createDudeType;
    private List<String> spinnerItemsList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.dude_create_fragment, container, false);
        Data.createFragment = this;

        background = rootView.findViewById(R.id.dude_create_background);
        headerColor = rootView.findViewById(R.id.dude_create_color_header);
        headerTextView = rootView.findViewById(R.id.dude_create_textview_header);
        spinnerPromptTextView = rootView.findViewById(R.id.dude_create_spinner_prompt);
        propertySpinner = rootView.findViewById(R.id.dude_create_spinner_property);
        infoPromptTextView = rootView.findViewById(R.id.dude_create_prompt_info);
        descriptionEditText = rootView.findViewById(R.id.dude_create_edittext_info);
        cancelButton = rootView.findViewById(R.id.dude_create_cancel_button);
        saveButton = rootView.findViewById(R.id.dude_create_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing to do - prevent clicks on ListView through our CreateFragment
            }
        });

        Bundle args = getArguments();
        ArrayAdapter<String> adapter = null;

        if (args != null) {
            createDudeType = args.getString(Data.KEY_DUDETYPE);
            if (createDudeType != null && !createDudeType.isEmpty()) {
                if (createDudeType.equalsIgnoreCase(DudeType.WHORE.toString())) {
                    headerTextView.setText(getResources().getString(R.string.add_whore2));
                    headerTextView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                    background.setBackground(getResources().getDrawable(R.drawable.background_fragment_whore));
                    headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_whore));

                    // TODO изменить иницилизацию списка тут и ниже
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.whores_string_array)));
                    spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                    adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_whore, R.id.spinner_row_textview_whore, spinnerItemsList, getLayoutInflater(), DudeType.WHORE);
                }
                else {
                    headerTextView.setText(getResources().getString(R.string.add_freak2));
                    headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                    background.setBackground(getResources().getDrawable(R.drawable.background_fragment_freak));
                    headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_freak));
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.freaks_string_array)));
                    spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                    adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_freak, R.id.spinner_row_textview_freak, spinnerItemsList, getLayoutInflater(), DudeType.FREAK);
                }
            }
        }
        propertySpinner.setAdapter(adapter);


        descriptionEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                infoPromptTextView.setVisibility(View.INVISIBLE);
                return false;

            }
        });


        descriptionEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    String description = descriptionEditText.getText().toString();
                    if (description == null || description.isEmpty()) {
                        infoPromptTextView.setVisibility(View.VISIBLE);
                    }
                    else {
                        infoPromptTextView.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    infoPromptTextView.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(Data.createFragment).commit();
                closeKeyboard();
                Data.createFragment = null;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDescription = descriptionEditText.getText().toString();
                inputDescription = Util.clearGaps(inputDescription);
                String inputSpinner = propertySpinner.getSelectedItem().toString();
                int spinnerPosition = getPositionOfSpinnerItem(inputSpinner);

                Intent intent = new Intent(Data.KEY_CREATE_DUDE);
                if (createDudeType != null && !createDudeType.isEmpty()) {
                    if (createDudeType.equalsIgnoreCase(DudeType.WHORE.toString())) {
                        intent.putExtra(Data.KEY_DUDETYPE, DudeType.WHORE.toString());
                    }
                    else {
                        intent.putExtra(Data.KEY_DUDETYPE, DudeType.FREAK.toString());
                    }
                    intent.putExtra(Data.KEY_DESCRIPTION, inputDescription);
                    intent.putExtra(Data.KEY_SPINNER, spinnerPosition);
                    getActivity().sendBroadcast(intent);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.createFragment).commit();
                    closeKeyboard();
                    Data.createFragment = null;
                }
            }
        });
        return rootView;
    }

    private int getPositionOfSpinnerItem(String line) {
        int selected = spinnerItemsList.indexOf(line);
        if (selected >= 0) {
            return selected;
        }
        return 0;
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (descriptionEditText != null) {
            imm.hideSoftInputFromWindow(descriptionEditText.getWindowToken(), 0);
        }
    }
}
