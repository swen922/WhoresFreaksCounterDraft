package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DudeFragment extends Fragment {

    private TextView background;
    private TextView headerColor;
    private TextView headerTextView;
    private TextView indexTextView;
    private Spinner propertySpinner;
    private TextView dateTextView;
    private TextView promptTextView;
    private EditText descriptionEditText;
    private Button cancelButton;
    private Button saveButton;

    private Dude myDude;
    private List<String> spinnerItemsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.dude_edit_fragment, container, false);
        Data.dudeFragment = this;

        background = rootView.findViewById(R.id.dude_fragment_background);
        headerColor = rootView.findViewById(R.id.dude_fragment_color_header);
        headerTextView = rootView.findViewById(R.id.dude_fragment_textview_header);
        indexTextView = rootView.findViewById(R.id.dude_fragment_textview_index);
        propertySpinner = rootView.findViewById(R.id.dude_fragment_spinner_property);
        dateTextView = rootView.findViewById(R.id.dude_fragment_date_textview);
        promptTextView = rootView.findViewById(R.id.dude_fragment_prompt_info);
        descriptionEditText = rootView.findViewById(R.id.dude_fragment_edittext_info);
        cancelButton = rootView.findViewById(R.id.dude_fragment_cancel_button);
        saveButton = rootView.findViewById(R.id.dude_fragment_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing to do - prevent clicks on ListView through our DudeFragment
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

        ArrayAdapter<String> adapter;

        if (myDude != null) {
            if (myDude.getDudeType().equals(DudeType.WHORE.toString())) {
                headerTextView.setText(getResources().getString(R.string.list_item_whore));
                headerTextView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_whore));
                headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_whore));
                indexTextView.setBackground(getResources().getDrawable(R.drawable.background_fragment_index_whore));
                indexTextView.setTextColor(getResources().getColor(R.color.colorOrangeDark));

                // TODO изменить иницилизацию списка тут и ниже
                spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.whores_string_array)));
                spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_whore, R.id.spinner_row_textview_whore, spinnerItemsList, getLayoutInflater(), DudeType.WHORE);
            }
            else {
                headerTextView.setText(getResources().getString(R.string.list_item_freak));
                headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_freak));
                headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_freak));
                indexTextView.setBackground(getResources().getDrawable(R.drawable.background_fragment_index_freak));
                indexTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayDark));
                spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.freaks_string_array)));
                spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                adapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_freak, R.id.spinner_row_textview_freak, spinnerItemsList, getLayoutInflater(), DudeType.FREAK);
            }

            indexTextView.setText(String.valueOf(Data.getDudes().size() - dudeID));

            String dt = getString(R.string.dude_date_time) + " " + myDude.getDateString();
            dateTextView.setText(dt);

            propertySpinner.setAdapter(adapter);
            propertySpinner.setSelection(myDude.getSpinnerSelectedPosition());

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
                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.dudeFragment).commit();
                    closeKeyboard();
                    Data.dudeFragment = null;
                }
            });

            final int dudeIDfin = dudeID;

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputDescription = descriptionEditText.getText().toString();
                    inputDescription = Util.clearGaps(inputDescription);
                    String inputSpinner = propertySpinner.getSelectedItem().toString();
                    int spinnerPosition = getPositionOfSpinnerItem(inputSpinner);

                    /*myDude.setDescription(inputDescription);
                    descriptionEditText.setText(inputDescription);
                    myDude.setSpinnerSelectedPosition(spinnerPosition);*/

                    Intent intent = new Intent(Data.KEY_UPDATE_DUDE);
                    intent.putExtra(Data.KEY_IDNUMBER, dudeIDfin);
                    intent.putExtra(Data.KEY_DESCRIPTION, inputDescription);
                    intent.putExtra(Data.KEY_SPINNER, spinnerPosition);
                    getActivity().sendBroadcast(intent);

                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.dudeFragment).commit();
                    closeKeyboard();
                    Data.dudeFragment = null;
                }
            });

            propertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spinnerItemsList.get(position).equalsIgnoreCase(getString(R.string.spinner_edit))) {

                        //Toast.makeText(getContext(), "EDIT!!!", Toast.LENGTH_LONG).show();

                        propertySpinner.setSelection(myDude.getSpinnerSelectedPosition());

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Data.spinnerEditFragment = new SpinnerEditFragment();

                        Bundle args = new Bundle();
                        args.putString(Data.KEY_DUDETYPE, myDude.getDudeType());
                        Data.spinnerEditFragment.setArguments(args);

                        ft.add(R.id.container_main, Data.spinnerEditFragment, Data.KEY_SPINNER_EDIT);
                        ft.commit();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

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
