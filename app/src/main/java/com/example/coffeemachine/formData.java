package com.example.coffeemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class formData extends AppCompatActivity {

    private EditText mDate;
    private AutoCompleteTextView mBrand;
    private AutoCompleteTextView mBrew;
    private AutoCompleteTextView mMilk;
    private EditText mNote;

    private String date;
    //Calender
    int year, month, day;
    private DatePickerDialog.OnDateSetListener mDatePickerListener;

    //brand of coffee array


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);

        mDate = findViewById(R.id.editTextDate);
        mBrand = findViewById(R.id.editTextCoffeeBrand);
        mBrew = findViewById(R.id.editTextBrew);
        mMilk = findViewById(R.id.editTextMilk);
        mNote = findViewById(R.id.editTextTextMultiLine);
        String [] coffeeBrand = getResources().getStringArray(R.array.countriesBrand);
        String [] measurementList = getResources().getStringArray(R.array.brewList);

        //for ounces in coffee brew and milk
        ArrayAdapter<String> measurementAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, measurementList);
        mBrew.setAdapter(measurementAdapter);
        mMilk.setAdapter(measurementAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, coffeeBrand);
        mBrand.setAdapter(adapter);


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(formData.this, android.R.style.Theme_DeviceDefault_Dialog, mDatePickerListener, year, month, day);
                dialog.show();
            }
        });

        mDatePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String resultDate = year + "/" + month + "/" + dayOfMonth;
                mDate.setText(resultDate);
            }
        };


    }

    public void formSubmit(View view) {

        date = mDate.getText().toString();
        String brand = mBrand.getText().toString();
        String brew = mBrew.getText().toString();
        String milk = mMilk.getText().toString();
        String note = mNote.getText().toString();

        CoffeeModel cfmodel = new CoffeeModel(date,brand,milk,brew,note);

        DataBaseHelperDAO db = new DataBaseHelperDAO(formData.this);
        boolean success = db.add(cfmodel);

        Toast.makeText(this, success + "", Toast.LENGTH_SHORT).show();

        if(success)
            Toast.makeText(this, "Success add to the list ", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Fail to add to the list", Toast.LENGTH_SHORT).show();
        //done with the intent
        finish();


    }
}