package com.example.androidmemoryleakage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    SimpleClass simpleClass; // 1

    private static MainActivity mainActivity; //2.
    private static WeakReference<MainActivity> mainActivity2; //2.

    private static TextView textView; // 3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        simpleClass = SimpleClass.getSimpleClassInstance(this);  // 1. Here Activity context lead to Memory Leak
        simpleClass = SimpleClass.getSimpleClassInstance(getApplicationContext()); // 1. Use Application Context


        mainActivity = this; // 2. Cause to Memory Leak
        mainActivity2 = new WeakReference<MainActivity>(this); // 2. use WeakReference to avoid ML


        textView = (TextView) findViewById(R.id.tv_hello); // 3 . this static view holding the reference and Cause to ML
        // 3. to avoid this above ML , dereference this static view in onDestroy() Method .

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        textView = null; // 3. Dereference this  static view to avoid ML
    }
}
