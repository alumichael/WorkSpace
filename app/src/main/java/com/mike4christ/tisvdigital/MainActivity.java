package com.mike4christ.tisvdigital;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserPreferences userPreferences = new UserPreferences(this);
    }
}
