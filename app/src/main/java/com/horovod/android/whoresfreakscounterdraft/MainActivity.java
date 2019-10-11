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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Dude> adapter;
    private List<Dude> listItems;
    private BroadcastReceiver receiver;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        listView = findViewById(R.id.listview_main);
        listItems = new ArrayList<>();
        adapter = new DudeAdapter(this, R.layout.list_item, listItems, fragmentManager);
        listView.setAdapter(adapter);

        Date date1 = new Date();
        Data.putDude(1, new Dude(DudeType.WHORE, 1, date1, "Описание 1"));
        Date date2 = new Date();
        Data.putDude(2, new Dude(DudeType.FREAK, 2, date2, "Описание 2"));
        Date date3 = new Date();
        Data.putDude(5, new Dude(DudeType.FREAK, 5, date3, ""));
        Date date4 = new Date();
        Data.putDude(12, new Dude(DudeType.WHORE, 12, date4, "Описание 4"));
        Date date5 = new Date();
        Data.putDude(25, new Dude(DudeType.WHORE, 25, date5, "Описание 5"));
        Date date6 = new Date();
        Data.putDude(130, new Dude(DudeType.FREAK, 130, date6, ""));
        Date date7 = new Date();
        Data.putDude(131, new Dude(DudeType.WHORE, 131, date7, null));
        Date date8 = new Date();
        Data.putDude(132, new Dude(DudeType.WHORE, 132, date8, "Описание 8"));

        listItems.addAll(Data.getDudes().values());
        adapter.notifyDataSetChanged();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(Data.KEY_IDNUMBER, -1);

                if (id >= 0) {
                    Dude dude = Data.getDude(id);
                    String newDescription = intent.getStringExtra(Data.KEY_DESCRIPTION);
                    if (newDescription != null && !newDescription.isEmpty()) {
                        dude.setDescription(newDescription);
                        Data.putDude(id, dude);
                        listItems.clear();
                        listItems.addAll(Data.getDudes().values());
                        listItems.sort(new Comparator<Dude>() {
                            @Override
                            public int compare(Dude o1, Dude o2) {
                                int o1id = o1.getIdNumber();
                                int o2id = o2.getIdNumber();
                                return o1id > o2id ? 1 : o1id < o2id ? -1 : 0;
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Data.KEY_UPDATE_DUDES);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
