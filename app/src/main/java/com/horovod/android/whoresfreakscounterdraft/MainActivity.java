package com.horovod.android.whoresfreakscounterdraft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView freaksCounter;
    private TextView whoresCounter;
    private TextView addFreakTextView;
    private TextView addWhoreTextView;
    private ListView listView;

    private ArrayAdapter<Dude> adapter;
    private BroadcastReceiver createReceiver;
    private BroadcastReceiver editReceiver;
    private BroadcastReceiver deleteReceiver;
    FragmentManager fragmentManager;

    private final String KEY_FRAGMENT_CREATE = "KEY_FRAGMENT_CREATE";


    // TODO видимо, в концепции андроида правильнее читать из XML каждый раз (?)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        listView = findViewById(R.id.listview_main);
        freaksCounter = findViewById(R.id.textview_counter_freaks);
        whoresCounter = findViewById(R.id.textview_counter_whores);
        addFreakTextView = findViewById(R.id.textview_add_freak);
        addWhoreTextView = findViewById(R.id.textview_add_whore);

        adapter = new DudeAdapter(this, R.layout.list_item, Data.getDudes(), fragmentManager);

        for (int i = 0; i < 80; i++) {
            Date date1 = new Date();
            double tmp = Math.random();
            int a = (int) (tmp * 10);
            int b = a % 2;
            if (b == 0) {
                Data.addDudeFirst(new Dude(DudeType.WHORE, date1, "Описание " + (i + 1), 0));
            }
            else {
                Data.addDudeFirst(new Dude(DudeType.FREAK, date1, "Описание " + (i + 1), 1));
            }
        }

        updateCounters();

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addFreakTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                CreateFragment createFragment = new CreateFragment();
                Bundle args = new Bundle();
                args.putString(Data.KEY_DUDETYPE, DudeType.FREAK.toString());
                createFragment.setArguments(args);

                int count = fragmentManager.getBackStackEntryCount();

                if (count == 0) {
                    ft.add(R.id.container_main, createFragment, KEY_FRAGMENT_CREATE);
                    ft.addToBackStack(KEY_FRAGMENT_CREATE);
                }
                else {
                    ft.replace(R.id.container_main, createFragment, KEY_FRAGMENT_CREATE);
                }
                ft.commit();
            }
        });

        addWhoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                CreateFragment createFragment = new CreateFragment();
                Bundle args = new Bundle();
                args.putString(Data.KEY_DUDETYPE, DudeType.WHORE.toString());
                createFragment.setArguments(args);

                int count = fragmentManager.getBackStackEntryCount();

                if (count == 0) {
                    ft.add(R.id.container_main, createFragment, KEY_FRAGMENT_CREATE);
                    ft.addToBackStack(KEY_FRAGMENT_CREATE);
                }
                else {
                    ft.replace(R.id.container_main, createFragment, KEY_FRAGMENT_CREATE);
                }
                ft.commit();
            }
        });


        createReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String createDudeType = intent.getStringExtra(Data.KEY_DUDETYPE);
                String descr = intent.getStringExtra(Data.KEY_DESCRIPTION);
                int spinner = intent.getIntExtra(Data.KEY_SPINNER, 0);

                if (createDudeType.equalsIgnoreCase(DudeType.WHORE.toString())) {
                    Data.createDude(DudeType.WHORE, descr, spinner);
                }
                else {
                    Data.createDude(DudeType.FREAK, descr, spinner);
                }
                adapter.notifyDataSetChanged();
                updateCounters();
            }
        };

        editReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(Data.KEY_IDNUMBER, -1);

                if (id >= 0) {
                    Dude dude = Data.getDude(id);
                    String newDescription = intent.getStringExtra(Data.KEY_DESCRIPTION);
                    int newSpinner = intent.getIntExtra(Data.KEY_SPINNER, 0);
                    dude.setDescription(newDescription);
                    dude.setSpinnerSelectedPosition(newSpinner);
                    adapter.notifyDataSetChanged();
                    updateCounters();
                }
            }
        };
        deleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(Data.KEY_IDNUMBER, -1);
                int size = Data.getDudes().size();

                if (id >= 0) {
                    boolean result = Data.removeDude(id);
                    if (result) {
                        Toast.makeText(getApplicationContext(), getString(R.string.delete_success) + " " + (size - id), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (size - id), Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (size - id), Toast.LENGTH_LONG).show();
                }
                updateCounters();
            }
        };
        IntentFilter intentFilterCreate = new IntentFilter(Data.KEY_CREATE_DUDE);
        registerReceiver(createReceiver, intentFilterCreate);
        IntentFilter intentFilterEdit = new IntentFilter(Data.KEY_UPDATE_DUDE);
        registerReceiver(editReceiver, intentFilterEdit);
        IntentFilter intentFilterDelete = new IntentFilter(Data.KEY_DELETE_DUDE);
        registerReceiver(deleteReceiver, intentFilterDelete);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(createReceiver);
        unregisterReceiver(editReceiver);
        unregisterReceiver(deleteReceiver);
    }

    // override OnBackPressed() for to close DudeFragment first, if it's opened now
    @Override
    public void onBackPressed() {

        int count = fragmentManager.getBackStackEntryCount();

        if (count > 0) {
            while(count > 0){
                if (Data.spinnerEditFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(Data.spinnerEditFragment).commit();
                    fragmentManager.popBackStack();
                    count--;
                    Data.spinnerEditFragment = null;
                }
                else if (Data.createFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(Data.createFragment).commit();
                    fragmentManager.popBackStack();
                    count--;
                    Data.createFragment = null;
                }
                else if (Data.dudeFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(Data.dudeFragment).commit();
                    fragmentManager.popBackStack();
                    count--;
                    Data.dudeFragment = null;
                }
            }
        }
        else {
            super.onBackPressed();
        }
    }

    private void updateCounters() {
        if (!Data.getDudes().isEmpty()) {
            int freaks = 0;
            for (Dude dude : Data.getDudes()) {
                if (dude.getDudeType().equals(DudeType.FREAK)) {
                    freaks++;
                }
            }
            freaksCounter.setText(String.valueOf(freaks));
            whoresCounter.setText(String.valueOf(Data.getDudes().size() - freaks));
        }
        else {
            freaksCounter.setText(String.valueOf(0));
            whoresCounter.setText(String.valueOf(0));
        }
    }

}
