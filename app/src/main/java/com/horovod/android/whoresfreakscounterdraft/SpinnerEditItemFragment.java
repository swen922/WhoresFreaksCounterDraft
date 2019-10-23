package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerEditItemFragment extends Fragment {

    private TextView background;
    private TextView topColorYexyView;
    private TextView headerTextView;
    private EditText itemEditText;
    private TextView revertItemTextView;

    private Button cancelButton;
    private Button saveButton;

    private boolean hasTouching = false;
    private String dudeTypeString = "";
    private int itemPosition = 0; // нужен, чтобы отправить обратно в SpinnerEditFragment
    private String currentItem = "";


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
        revertItemTextView = rootView.findViewById(R.id.spinner_edit_item_fragment_revert);
        cancelButton = rootView.findViewById(R.id.spinner_edit_item_fragment_cancel_button);
        saveButton = rootView.findViewById(R.id.spinner_edit_item_fragment_save_button);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nothin to do - prevent clicks on background View through our SpinnerEditItemFragment
            }
        });

        Bundle args = getArguments();

        if (args != null) {
            dudeTypeString = args.getString(Data.KEY_DUDETYPE);
            itemPosition = args.getInt(Data.KEY_IDNUMBER, 0);
            currentItem = args.getString(Data.KEY_SPINNER_EDIT_ITEM);
        }

        if (dudeTypeString != null && !dudeTypeString.isEmpty()) {
            if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_whore));
                topColorYexyView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_whore));
                revertItemTextView.setTextColor(getResources().getColor(R.color.colorPrimaryMedium));
            }
            else {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_top_freak));
                headerTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_spinner_edit_freak));
                revertItemTextView.setTextColor(getResources().getColor(R.color.colorBlueGrayPrimary));
            }

            itemEditText.setText(currentItem);

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

            revertItemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String baseItem = getResources().getStringArray(R.array.whores_string_array)[itemPosition];
                    itemEditText.setText(baseItem);
                    hasTouching = false;
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.spinnerEditItemFragment).commit();
                    closeKeyboard();
                    hasTouching = false;
                    Data.spinnerEditItemFragment = null;
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemEditText.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), getResources().getString(R.string.empty_spinner_item), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(Data.KEY_SPINNER_UPDATE_ITEM);
                        intent.putExtra(Data.KEY_IDNUMBER, itemPosition);
                        String newItem = itemEditText.getText().toString();
                        newItem = Util.clearGaps(newItem);
                        intent.putExtra(Data.KEY_SPINNER_ITEM, newItem);
                        getActivity().sendBroadcast(intent);
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().remove(Data.spinnerEditItemFragment).commit();
                    closeKeyboard();
                    hasTouching = false;
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
