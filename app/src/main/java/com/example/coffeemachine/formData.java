package com.example.coffeemachine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;
import java.util.Calendar;

public class formData extends AppCompatActivity {

    private EditText mDate;
    private AutoCompleteTextView mBrand;
    private AutoCompleteTextView mBrew;
    private AutoCompleteTextView mMilk;
    private EditText mNote;
    private ImageView mImg;
    private ImageButton mTakePhoto;
    //Calender
    int year, month, day;
    private DatePickerDialog.OnDateSetListener mDatePickerListener;
    //permission constants

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    //arrays of permissions
    private String[] cameraPermissions; //camera and storage
    private String[] storagePermissions;
    //Variable (will containt data to save)
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);

        //init views
        mDate = findViewById(R.id.editTextDate);
        mBrand = findViewById(R.id.editTextCoffeeBrand);
        mBrew = findViewById(R.id.editTextBrew);
        mMilk = findViewById(R.id.editTextMilk);
        mNote = findViewById(R.id.editTextTextMultiLine);
        mImg = findViewById(R.id.photo);
        mTakePhoto = findViewById(R.id.btnLoadPhoto);

        //on click listener for image
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkCameraPermission()){
                    requestCameraPermission();
                }
                else{
                    pickFromCamera();
                }
            }
        });

        //init arrays of static data
        String [] coffeeBrand = getResources().getStringArray(R.array.countriesBrand);
        String [] measurementList = getResources().getStringArray(R.array.brewList);

        //for ounces in coffee brew and milk
        ArrayAdapter<String> measurementAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, measurementList);
        mBrew.setAdapter(measurementAdapter);
        mMilk.setAdapter(measurementAdapter);

        //auto view for coffee Brand
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, coffeeBrand);
        mBrand.setAdapter(adapter);

        // Date pick DiaLog
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

        //init permission array
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    public void formSubmit(View view) {

        String date = mDate.getText().toString();
        String brand = mBrand.getText().toString();
        String brew = mBrew.getText().toString();
        String milk = mMilk.getText().toString();
        String note = mNote.getText().toString();

        CoffeeModel cfmodel = new CoffeeModel(1, date,brand,milk,brew,note,imageUri.toString());

        DataBaseHelperDAO db = new DataBaseHelperDAO(formData.this);
        boolean success = db.add(cfmodel);

        Toast.makeText(this, success + "", Toast.LENGTH_SHORT).show();

        if(success)
            Toast.makeText(this, imageUri+"", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Fail to add to the list", Toast.LENGTH_SHORT).show();

        //put the result to pass back into an Intent and close this Activity
        setResult(RESULT_OK);

        //back to Main Activity
        finish();


    }

    private boolean checkStoragePermission (){
        //check if the storage permission is enable
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission(){
        //request permission
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission (){
        //check if the camera permission is enable
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean writeStorageresult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && writeStorageresult;
    }
    private void requestCameraPermission(){
        //request permission
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }
    private void pickFromCamera(){
        CropImage.activity().start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //result of permission allowed/denied

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (permissions.length > 0 ){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else
                        Toast.makeText(this, "Camera and storage permissions are required", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                Toast.makeText(this, "Camera and storage permissions are required", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                imageUri = result.getUri();
                mImg.setImageURI(imageUri);
                mImg.setVisibility(View.VISIBLE);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}