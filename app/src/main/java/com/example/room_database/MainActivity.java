package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //initialize varibles
    EditText edit;
    Button add, reset;
    RecyclerView recycler;

    List<MainData> datalist = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variable

        edit =findViewById(R.id.edit);
        add = findViewById(R.id.add);
        reset = findViewById(R.id.reset);
        recycler = findViewById(R.id.recycler);

        //initialize database
        database =RoomDB.getInstance(this);

        //store database value in datalist
        datalist =database.mainDao().getALL();

        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);

        //set layout manager
        recycler.setLayoutManager(linearLayoutManager);

        //initialize adapter
        adapter =  new MainAdapter(MainActivity.this, datalist);

        //set adapter
        recycler.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //det string from edit text
                String sText =edit.getText().toString().trim();

                //check condition
                if(!sText.equals("")){

                    //when text is not empty
                    //initialize main data
                    MainData mainData = new MainData();

                    //set text on main data
                    mainData.setText(sText);

                    //insert text in database
                    database.mainDao().insert(mainData);

                    //clear edit text
                    edit.setText("");

                    //notify when data is inserted
                    datalist.clear();
                    datalist.addAll(database.mainDao().getALL());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //delete all data from database
                database.mainDao().reset(datalist);

                //notify when all data deleted
                datalist.clear();
                datalist.addAll(database.mainDao().getALL());
                adapter.notifyDataSetChanged();
            }
        });
    }
}