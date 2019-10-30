package com.horovod.android.whoresfreakscounterdraft;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateFragment extends Fragment {


    private TextView background;
    private TextView backgroundLeft;
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
    private ArrayAdapter<String> spinnerAdapter;

    private String selectedSpinnerItem = "";
    private int selectedSpinnerPosition = 0;

    private BroadcastReceiver spinnerEditReceiver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.dude_create_fragment, container, false);
        Data.createFragment = this;

        background = rootView.findViewById(R.id.dude_create_background);
        //headerColor = rootView.findViewById(R.id.dude_create_color_header);
        //headerTextView = rootView.findViewById(R.id.dude_create_textview_header);
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

        if (args != null) {
            createDudeType = args.getString(Data.KEY_DUDETYPE);
            if (createDudeType != null && !createDudeType.isEmpty()) {
                if (createDudeType.equalsIgnoreCase(DudeType.WHORE.toString())) {
                    background.setBackground(getResources().getDrawable(R.drawable.background_fragment_whore));

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        headerColor = rootView.findViewById(R.id.dude_create_color_header);
                        headerTextView = rootView.findViewById(R.id.dude_create_textview_header);
                        headerTextView.setText(getResources().getString(R.string.add_whore2));
                        headerTextView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                        headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_whore));
                    }
                    else {
                        backgroundLeft = rootView.findViewById(R.id.land_dude_create_background_left);
                        backgroundLeft.setBackground(getResources().getDrawable(R.drawable.land_background_fragment_left_whore));
                        //headerColor.setBackground(getResources().getDrawable(R.drawable.land_background_fragment_top_whore));
                    }

                    initSpinner(createDudeType);
                }
                else {
                    background.setBackground(getResources().getDrawable(R.drawable.background_fragment_freak));

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        headerColor = rootView.findViewById(R.id.dude_create_color_header);
                        headerTextView = rootView.findViewById(R.id.dude_create_textview_header);
                        headerTextView.setText(getResources().getString(R.string.add_freak2));
                        headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                        headerColor.setBackground(getResources().getDrawable(R.drawable.background_fragment_top_freak));
                    }
                    else {
                        backgroundLeft = rootView.findViewById(R.id.land_dude_create_background_left);
                        backgroundLeft.setBackground(getResources().getDrawable(R.drawable.land_background_fragment_left_freak));
                        //headerColor.setBackground(getResources().getDrawable(R.drawable.land_background_fragment_top_freak));
                    }

                    initSpinner(createDudeType);
                }

                propertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (spinnerItemsList.get(position).equalsIgnoreCase(getString(R.string.spinner_edit))) {

                            closeKeyboard();

                            if (selectedSpinnerItem.isEmpty()) {
                                propertySpinner.setSelection(0);
                            }
                            else {
                                propertySpinner.setSelection(selectedSpinnerPosition);
                            }

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Data.spinnerEditFragment = new SpinnerEditFragment();

                            Bundle args = new Bundle();
                            args.putString(Data.KEY_DUDETYPE, createDudeType);
                            if (!selectedSpinnerItem.isEmpty()) {
                                args.putInt(Data.KEY_PREVIOUS_ITEM, selectedSpinnerPosition);
                            }
                            Data.spinnerEditFragment.setArguments(args);

                            ft.add(R.id.container_main, Data.spinnerEditFragment, null);
                            ft.commit();
                        }
                        else {
                            selectedSpinnerItem = propertySpinner.getSelectedItem().toString();
                            selectedSpinnerPosition = getPositionOfSpinnerItem(propertySpinner.getSelectedItem().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                descriptionEditText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        infoPromptTextView.setVisibility(View.INVISIBLE);
                        return false;

                    }
                });

                /*descriptionEditText.setOnKeyListener(new View.OnKeyListener() {
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
                });*/

                descriptionEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String descr = descriptionEditText.getText().toString();
                        if (descr != null && !descr.isEmpty()) {
                            infoPromptTextView.setVisibility(View.INVISIBLE);
                        }
                        else {
                            infoPromptTextView.setVisibility(View.VISIBLE);
                        }
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
                });

                spinnerEditReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        selectedSpinnerPosition = intent.getIntExtra(Data.KEY_PREVIOUS_ITEM, 0);
                        initSpinner(createDudeType);
                        propertySpinner.setSelection(selectedSpinnerPosition);

                    }
                };
                IntentFilter spinnerEditFilter = new IntentFilter(Data.KEY_SPINNER_EDIT);
                getActivity().registerReceiver(spinnerEditReceiver, spinnerEditFilter);

            }
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

    private void initSpinner(String dudeTypeString) {

        /*** В методе надо вызывать проверку привязки нашего фрашмента к фрагмент-менеджеру,
         * иначе приложение валится с исключением
         * Caused by: java.lang.IllegalStateException: onGetLayoutInflater()
         * cannot be executed until the Fragment is attached to the FragmentManager.
         * то есть строчки
         * Activity activity = getActivity();
         *         if (isAdded() && activity != null)*/

        Activity activity = getActivity();

        if (isAdded() && activity != null) {

            if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                if (Data.getWhoresSpinner().isEmpty()) {
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.whores_string_array)));
                }
                else {
                    spinnerItemsList = new ArrayList<>(Data.getWhoresSpinner());
                }
                spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                spinnerAdapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_whore, R.id.spinner_row_textview_whore, spinnerItemsList, getLayoutInflater(), DudeType.WHORE);
            }
            else {
                if (Data.getFreaksSpinner().isEmpty()) {
                    spinnerItemsList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.freaks_string_array)));
                }
                else {
                    spinnerItemsList = new ArrayList<>(Data.getFreaksSpinner());
                }
                spinnerItemsList.add(getResources().getString(R.string.spinner_edit));
                spinnerAdapter = new PropertySpinnerAdapter(getContext(), R.layout.spinner_row_freak, R.id.spinner_row_textview_freak, spinnerItemsList, getLayoutInflater(), DudeType.FREAK);
            }
            propertySpinner.setAdapter(spinnerAdapter);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(spinnerEditReceiver);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String descr = descriptionEditText.getText().toString();
        if (descr != null && !descr.isEmpty()) {
            infoPromptTextView.setVisibility(View.INVISIBLE);
        }
    }


}
