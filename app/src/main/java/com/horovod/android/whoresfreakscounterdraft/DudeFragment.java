package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DudeFragment extends Fragment {

    private DudeFragment dudeFragment;

    private TextView background;
    //private ImageView dudeImageView;
    private TextView headerTextView;
    private Spinner propertySpinner;
    private TextView promptTextView;
    private EditText descriptionEditText;
    private Button cancelButton;
    private Button saveButton;

    private Dude myDude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.dude_edit_fragment, container, false);
        dudeFragment = this;
        Data.dudeFragment = this;

        /*container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing to do - blocking clicks on ListView through our DudeFragment
            }
        });*/

        background = rootView.findViewById(R.id.dude_fragment_background);
        //dudeImageView = rootView.findViewById(R.id.dude_fragment_picture);
        headerTextView = rootView.findViewById(R.id.dude_fragment_textview_header);
        propertySpinner = rootView.findViewById(R.id.dude_fragment_spinner_property);
        promptTextView = rootView.findViewById(R.id.dude_fragment_prompt_info);
        descriptionEditText = rootView.findViewById(R.id.dude_fragment_edittext_info);
        cancelButton = rootView.findViewById(R.id.dude_fragment_cancel_button);
        saveButton = rootView.findViewById(R.id.dude_fragment_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing to do - blocking clicks on ListView through our DudeFragment
            }
        });

        Bundle args = getArguments();

        int dudeID = -1;
        if (args != null) {
            dudeID = args.getInt(Data.KEY_IDNUMBER, -1);
            if (dudeID >= 0) {
                myDude = Data.getDude(dudeID);
            }
        }

        if (myDude != null) {
            if (myDude.getDudeType().equals(DudeType.WHORE)) {
                headerTextView.setText(getResources().getString(R.string.list_item_whore));
                headerTextView.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_whore));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_whore));
            }
            else {
                headerTextView.setText(getResources().getString(R.string.list_item_freak));
                headerTextView.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_freak));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_freak));
            }

            String[] spinnerItems = getResources().getStringArray(R.array.whores_string_array);
            ArrayAdapter<String> adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row, R.id.spinner_row_textview, spinnerItems, getLayoutInflater());
            propertySpinner.setAdapter(adapter);

            descriptionEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    promptTextView.setVisibility(View.INVISIBLE);
                    return false;

                }
            });

            descriptionEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        String description = descriptionEditText.getText().toString();
                        if (description == null || description.isEmpty()) {
                            promptTextView.setVisibility(View.VISIBLE);
                        }
                        else {
                            promptTextView.setVisibility(View.INVISIBLE);
                        }
                    }
                    else {
                        promptTextView.setVisibility(View.INVISIBLE);
                    }

                    return false;
                }
            });

            String description = myDude.getDescription();
            if (description == null || description.isEmpty()) {
                promptTextView.setVisibility(View.VISIBLE);
            }
            else {
                descriptionEditText.setText(myDude.getDescription());
                promptTextView.setVisibility(View.INVISIBLE);
            }

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptionEditText.setText(myDude.getDescription());
                    getActivity().getSupportFragmentManager().beginTransaction().remove(dudeFragment).commit();
                    closeKeyboard();
                }
            });

            final int dudeIDfin = dudeID;

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = descriptionEditText.getText().toString();

                    input = Util.clearGaps(input);

                    myDude.setDescription(input);
                    descriptionEditText.setText(input);

                    Intent intent = new Intent(Data.KEY_UPDATE_DUDES);
                    intent.putExtra(Data.KEY_IDNUMBER, dudeIDfin);
                    intent.putExtra(Data.KEY_DESCRIPTION, input);
                    getActivity().sendBroadcast(intent);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(dudeFragment).commit();
                    closeKeyboard();
                }
            });
        }

        return rootView;
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (descriptionEditText != null) {
            imm.hideSoftInputFromWindow(descriptionEditText.getWindowToken(), 0);
        }
    }

}
