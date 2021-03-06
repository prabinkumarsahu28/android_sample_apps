package com.xyz.one_many_room.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.xyz.one_many_room.R;
import com.xyz.one_many_room.database.DatabaseClient;
import com.xyz.one_many_room.interfaces.LooperPreparedListener;
import com.xyz.one_many_room.model.BackgroundThread;
import com.xyz.one_many_room.model.Dog;
import com.xyz.one_many_room.model.Owner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LooperPreparedListener {

    private EditText mEtOwnerName;
    private EditText mEtDogName;
    private EditText mEtDogBreed;
    private Button mBtnSave;
    private Button mBtnGetAllDogs;
    private DatabaseClient databaseClient;
    private BackgroundThread backgroundThread;
    private boolean isLooperReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewsAndListeners();
    }

    private void initViewsAndListeners() {
        mEtOwnerName = findViewById(R.id.etDownerName);
        mEtDogName = findViewById(R.id.etDogName);
        mEtDogBreed = findViewById(R.id.etDogBreed);
        mBtnSave = findViewById(R.id.btnSave);
        mBtnGetAllDogs = findViewById(R.id.btnGetAllDogs);
        mBtnSave.setOnClickListener(this);
        mBtnGetAllDogs.setOnClickListener(this);
        databaseClient = DatabaseClient.getDatabaseClient(this);
        backgroundThread = new BackgroundThread("db_thread", this);
        backgroundThread.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSave:
                if (isDataValid() && isLooperReady) {
                    backgroundThread.addTaskToMessageQueue(insertDataToDatabase());
                }
                break;
            case R.id.btnGetAllDogs:
                if (isLooperReady) {
                    backgroundThread.addTaskToMessageQueue(getAllDogAndOwnerDetailsFromDB());
                }
                break;
        }
    }

    private Runnable getAllDogAndOwnerDetailsFromDB() {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, OwnerDogInfoActivity.class);
                startActivity(intent);
            }
        };
    }

    private Runnable insertDataToDatabase() {
        return new Runnable() {
            @Override
            public void run() {
                // Insert owner info
                Owner owner = new Owner(mEtOwnerName.getText().toString());
                databaseClient.getAppDatabase().ownerDogDao().insertOwner(owner);

                // Insert Dog info

                String[] dogNames = mEtDogName.getText().toString().split(",");
                String[] dogBreeds = mEtDogBreed.getText().toString().split(",");
                List<Dog> dogList = new ArrayList<>();
                if (dogBreeds.length == dogNames.length) {
                    for (int i = 0; i < dogNames.length; i++) {
                        Dog dog = new Dog(dogNames[i],
                                dogBreeds[i],
                                owner.getOwnerId());
                        dogList.add(dog);
                    }
                }

                databaseClient.getAppDatabase().ownerDogDao().insertDog(dogList);
            }
        };
    }

    private boolean isDataValid() {
        if (mEtOwnerName.getText().toString().isEmpty()) {
            mEtOwnerName.setError("Owner name cannot be empty");
            return false;
        }
        if (mEtDogBreed.getText().toString().isEmpty()) {
            mEtDogBreed.setError("Dog breed cannot be empty");
            return false;
        }
        if (mEtDogName.getText().toString().isEmpty()) {
            mEtDogName.setError("Dog name cannot be empty");
            return false;
        }
        return true;
    }

    @Override
    public void looperPreparedListener() {
        isLooperReady = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundThread.getLooper().quit();
    }
}