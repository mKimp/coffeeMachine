package com.example.coffeemachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//First app
public class MainActivity extends AppCompatActivity {

    String LOG_TAG = MainActivity.class.getSimpleName();
    TextView statusList;
    RecyclerView recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusList = findViewById(R.id.txtviewDate);
        recycleview = findViewById(R.id.recycleView);

        DataBaseHelperDAO db = new DataBaseHelperDAO(MainActivity.this);
        List<CoffeeModel> coffeeList = db.viewAll();

        if(coffeeList.isEmpty())
            statusList.setText("Empty List");
        else {
            statusList.setText(R.string.listTitle);
            MyAdapter adapter = new MyAdapter(this, coffeeList);
            recycleview.setAdapter(adapter);
            recycleview.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    public void addData(View view) {
        Intent intent = new Intent(this, formData.class);
        startActivity(intent);
        DataBaseHelperDAO db = new DataBaseHelperDAO(MainActivity.this);
        List<CoffeeModel> coffeeList = db.viewAll();
        statusList.setText(R.string.listTitle);
        MyAdapter adapter = new MyAdapter(this, coffeeList);
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
    }

}