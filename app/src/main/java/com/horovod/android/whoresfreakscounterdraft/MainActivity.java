package com.horovod.android.whoresfreakscounterdraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<ItemList> adapter;
    private List<ItemList> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview_main);
        listItems = new ArrayList<>();
        adapter = new ItemAdapter(this, R.layout.list_item, listItems);
        listView.setAdapter(adapter);

        Date date1 = new Date();
        listItems.add(new ItemList(DudeType.WHORE, 1, date1, "Прст"));
        Date date2 = new Date();
        listItems.add(new ItemList(DudeType.FREAK, 2, date2, "Нркан"));
        adapter.notifyDataSetChanged();

    }
}
