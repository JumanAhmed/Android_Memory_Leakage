package com.example.androidmemoryleakage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SimpleClass simpleClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        simpleClass = SimpleClass.getSimpleClassInstance(this); // Cause Memory Leak

        simpleClass = SimpleClass.getSimpleClassInstance(getApplicationContext());

    }


}
