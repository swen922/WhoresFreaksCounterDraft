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
import android.view.Menu;
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
    private BroadcastReceiver spinnerEditReceiver;

    private FragmentManager fragmentManager;


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


        final Loader loader = new Loader(getApplicationContext());
        loader.readBaseFromJSON();
        loader.readWhoreSpinnerFromJSON();
        loader.readFreakSpinnerFromJSON();

        /*for (int i = 0; i < 80; i++) {
            Date date1 = new Date();
            double tmp = Math.random();
            int a = (int) (tmp * 10);
            int b = a % 2;
            if (b == 0) {
                Data.addDudeFirst(new Dude(DudeType.WHORE.toString(), date1, "Описание " + (i + 1), 0));
            }
            else {
                Data.addDudeFirst(new Dude(DudeType.FREAK.toString(), date1, "Описание " + (i + 1), 1));
            }
        }*/

        updateCounters();

        adapter = new DudeAdapter(this, R.layout.list_item, Data.getDudes(), fragmentManager);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addFreakTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Data.createFragment = new CreateFragment();
                Bundle args = new Bundle();
                args.putString(Data.KEY_DUDETYPE, DudeType.FREAK.toString());
                Data.createFragment.setArguments(args);
                ft.add(R.id.container_main, Data.createFragment, null);
                ft.commit();
            }
        });

        addWhoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Data.createFragment = new CreateFragment();
                Bundle args = new Bundle();
                args.putString(Data.KEY_DUDETYPE, DudeType.WHORE.toString());
                Data.createFragment.setArguments(args);
                ft.add(R.id.container_main, Data.createFragment, null);
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
                    Data.createDude(DudeType.WHORE.toString(), descr, spinner);
                }
                else {
                    Data.createDude(DudeType.FREAK.toString(), descr, spinner);
                }
                loader.writeBaseToJSON();
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
                    loader.writeBaseToJSON();
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
                        loader.writeBaseToJSON();
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (size - id), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (size - id), Toast.LENGTH_LONG).show();
                }
                updateCounters();
            }
        };
        spinnerEditReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.i("INSIDE MainActivity", "spinnerEditReceiver");

                adapter.notifyDataSetChanged();
            }
        };

        IntentFilter intentFilterCreate = new IntentFilter(Data.KEY_CREATE_DUDE);
        registerReceiver(createReceiver, intentFilterCreate);
        IntentFilter intentFilterEdit = new IntentFilter(Data.KEY_UPDATE_DUDE);
        registerReceiver(editReceiver, intentFilterEdit);
        IntentFilter intentFilterDelete = new IntentFilter(Data.KEY_DELETE_DUDE);
        registerReceiver(deleteReceiver, intentFilterDelete);
        IntentFilter spinnerEditFilter = new IntentFilter(Data.KEY_SPINNER_EDIT);
        registerReceiver(spinnerEditReceiver, spinnerEditFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(createReceiver);
        unregisterReceiver(editReceiver);
        unregisterReceiver(deleteReceiver);
        unregisterReceiver(spinnerEditReceiver);
    }

    // override OnBackPressed() for to close DudeFragment first, if it's opened now
    @Override
    public void onBackPressed() {

        if (Data.spinnerEditItemFragment != null) {
            fragmentManager.beginTransaction().remove(Data.spinnerEditItemFragment).commit();
            Data.spinnerEditItemFragment = null;
        }
        else if (Data.spinnerEditFragment != null) {
            fragmentManager.beginTransaction().remove(Data.spinnerEditFragment).commit();
            Data.spinnerEditFragment = null;
        }
        else if (Data.createFragment != null) {
            fragmentManager.beginTransaction().remove(Data.createFragment).commit();
            Data.createFragment = null;
        }
        else if (Data.dudeFragment != null) {
            fragmentManager.beginTransaction().remove(Data.dudeFragment).commit();
            Data.dudeFragment = null;
        }
        else {
            super.onBackPressed();
        }
    }

    private void updateCounters() {
        if (!Data.getDudes().isEmpty()) {
            int freaks = 0;
            for (Dude dude : Data.getDudes()) {
                if (dude.getDudeType().equals(DudeType.FREAK.toString())) {
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
