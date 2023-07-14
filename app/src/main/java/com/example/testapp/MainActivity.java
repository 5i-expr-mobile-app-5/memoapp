package com.example.testapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String[] FROM = {"date","time","content"};
    private static final int[] TO = {R.id.date,R.id.time,R.id.content};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);
    }
}