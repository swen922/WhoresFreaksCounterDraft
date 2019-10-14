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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Dude> adapter;
    private BroadcastReceiver receiver;
    private BroadcastReceiver deleteReceiver;
    FragmentManager fragmentManager;


    // TODO видимо, в концепции андроида правильнее читать из XML каждый раз (?)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        listView = findViewById(R.id.listview_main);
        //listItems = new ArrayList<>();
        adapter = new DudeAdapter(this, R.layout.list_item, Data.getDudes(), fragmentManager);

        for (int i = 0; i < 30; i++) {
            Date date1 = new Date();
            double tmp = Math.random();
            int a = (int) (tmp * 10);
            int b = a % 2;
            if (b == 0) {
                Data.addDudeLast(new Dude(DudeType.WHORE, date1, "Описание " + (i + 1)));
            }
            else {
                Data.addDudeLast(new Dude(DudeType.FREAK, date1, "Описание " + (i + 1)));
            }
        }

        //listItems.addAll(Data.getDudes().values());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(Data.KEY_IDNUMBER, -1);

                if (id >= 0) {
                    Dude dude = Data.getDude(id);
                    String newDescription = intent.getStringExtra(Data.KEY_DESCRIPTION);
                    dude.setDescription(newDescription);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        deleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(Data.KEY_IDNUMBER, -1);

                if (id >= 0) {
                    boolean result = Data.removeDude(id);
                    if (result) {
                        Toast.makeText(getApplicationContext(), getString(R.string.delete_success) + " " + (id + 1), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (id + 1), Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.delete_fail) + " " + (id + 1), Toast.LENGTH_LONG).show();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Data.KEY_UPDATE_DUDES);
        registerReceiver(receiver, intentFilter);
        IntentFilter intentFilterDelete = new IntentFilter(Data.KEY_DELETE_DUDE);
        registerReceiver(deleteReceiver, intentFilterDelete);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(deleteReceiver);
    }

    // override OnBackPressed() for to close DudeFragment first, if it's opened now
    @Override
    public void onBackPressed() {

        int count = fragmentManager.getBackStackEntryCount();

        if (count > 0) {
            while(count > 0){
                getSupportFragmentManager().beginTransaction().remove(Data.dudeFragment).commit();
                fragmentManager.popBackStack();
                count--;
            }
        }
        else {
            super.onBackPressed();
        }
    }
}
