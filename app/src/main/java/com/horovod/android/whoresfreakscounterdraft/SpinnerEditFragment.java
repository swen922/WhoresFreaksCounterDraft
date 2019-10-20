package com.horovod.android.whoresfreakscounterdraft;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class SpinnerEditFragment extends Fragment {

    private TextView background;
    private TextView topColorYexyView;
    private TextView headerTextView;
    private EditText itemEditText0;
    private EditText itemEditText1;
    private EditText itemEditText2;
    private EditText itemEditText3;
    private EditText itemEditText4;
    private EditText itemEditText5;
    private EditText itemEditText6;
    private Button cancelButton;
    private Button saveButton;

    private DudeType myDuddeType;
    private List<String> spinnerItemsList;


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
                // nothing to do - prevent clicks on ListView through our SpinnerEditFragment
            }
        });



        Bundle args = getArguments();
        String dudeTypeString = "";
        if (args != null) {
            dudeTypeString = args.getString(Data.KEY_DUDETYPE);
        }
        if (dudeTypeString != null && !dudeTypeString.isEmpty()) {
            if (dudeTypeString.equalsIgnoreCase(DudeType.WHORE.toString())) {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_whore));
                headerTextView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                background.setBackground(getResources().getDrawable(R.drawable.background_fragment_index_whore));

                // TODO в том числе во фрагментах редактирования и создания инициализацию адаптера

            }
            else {
                topColorYexyView.setBackground(getContext().getResources().getDrawable(R.drawable.background_fragment_spinner_edit_freak));

                // TODO в том числе во фрагментах редактирования и создания инициализацию адаптера


            }
        }



        return rootView;

    }
}
