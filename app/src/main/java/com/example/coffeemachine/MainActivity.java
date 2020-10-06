package com.example.coffeemachine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//First app
public class MainActivity extends AppCompatActivity {

    String LOG_TAG = MainActivity.class.getSimpleName();
    RecyclerView recycleview;
    MyAdapter adapter ;
    DataBaseHelperDAO db;
    List<CoffeeModel> coffeeList;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleview = findViewById(R.id.recycleView);
        //open data base
        db = new DataBaseHelperDAO(MainActivity.this);
        //view all data from the database
       coffeeList = db.viewAll();

        adapter = new MyAdapter(this, coffeeList);
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));

        //swipe to remove the data
        ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                //GET THE OBJECT FROM THE POSITION BEFORE REMOVING IT FORM THE ARRAYLIST
                CoffeeModel md = coffeeList.get(pos);
                coffeeList.remove(pos);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                //REMOVE FROM THE DATABASE
                boolean result = db.DeleteOne(md);
                if (result)
                    Toast.makeText(MainActivity.this, "Successfully remove from the database", Toast.LENGTH_SHORT).show();
            }
        };

        //attach to our recycle view
        new ItemTouchHelper(helper).attachToRecyclerView(recycleview);

    }
    //add new coffee

    public void addData() {
        Intent intent = new Intent(this, formData.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                assert data != null;
                coffeeList = db.viewAll();
                adapter = new MyAdapter(this, coffeeList);
                recycleview.setAdapter(adapter);
                recycleview.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void addData(MenuItem item) {
        addData();
    }
}