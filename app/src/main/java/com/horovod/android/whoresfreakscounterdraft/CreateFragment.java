package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CreateFragment extends Fragment {

    private CreateFragment createFragment;
    private DudeType dudeType;


    private TextView background;
    private TextView headerColor;
    private TextView headerTextView;
    private TextView spinnerPromptTextView;
    private Spinner propertySpinner;
    private TextView infoPromptTextView;
    private EditText descriptionEditText;
    private Button cancelButton;
    private Button saveButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.dude_create_fragment, container, false);
        createFragment = this;

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
                // nothing to do - blocking clicks on ListView through our CreateFragment
            }
        });

        Bundle args = getArguments();

        String[] spinnerItems = new String[]{};

        String dudeTypeString = "";
        if (args != null) {
            dudeTypeString = args.getString(Data.KEY_DUDETYPE);
            if (!dudeTypeString.isEmpty()) {
                if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                    headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_whore));
                    headerTextView.setText(getString(R.string.add_whore2));
                    spinnerItems = getResources().getStringArray(R.array.whores_string_array);
                    dudeType = DudeType.WHORE;
                }
                else {
                    headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_freak));
                    headerTextView.setText(getString(R.string.add_freak2));
                    spinnerItems = getResources().getStringArray(R.array.freaks_string_array);
                    dudeType = DudeType.FREAK;
                }
            }
        }

        ArrayAdapter<String> adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row, R.id.spinner_row_textview, spinnerItems, getLayoutInflater());
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
                getActivity().getSupportFragmentManager().beginTransaction().remove(createFragment).commit();
                closeKeyboard();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDescription = descriptionEditText.getText().toString();
                inputDescription = Util.clearGaps(inputDescription);
                String inputSpinner = propertySpinner.getSelectedItem().toString();

                Intent intent = new Intent(Data.KEY_CREATE_DUDE);
                intent.putExtra(Data.KEY_DUDETYPE, dudeType);
                intent.putExtra(Data.KEY_DESCRIPTION, inputDescription);
                intent.putExtra(Data.KEY_SPINNER, inputSpinner);
                getActivity().sendBroadcast(intent);
                getActivity().getSupportFragmentManager().beginTransaction().remove(createFragment).commit();
                closeKeyboard();
            }
        });

        return rootView;

    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (descriptionEditText != null) {
            imm.hideSoftInputFromWindow(descriptionEditText.getWindowToken(), 0);
        }
    }
}
