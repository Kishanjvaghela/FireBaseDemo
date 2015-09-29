package com.example.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.firebasedemo.dailog.UserDialog;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getList());
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.setAndroidContext(this);

    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("Save String");
        list.add("Save User Object");
        list.add("Save User List");
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, MessageActivity.class);
                break;
            case 1:
                intent = new Intent(this, SavingDataActivity.class);
                break;
            case 2:
                intent = new Intent(this, SavingDataListActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
