package com.example.androidmemoryleakage;

import android.content.Context;

public class SimpleClass {

    private static SimpleClass simpleClassInstance;

    private Context context;

    private SimpleClass(Context context){
        this.context = context;
    }

    public static SimpleClass getSimpleClassInstance(Context context){

        if (simpleClassInstance == null){
            simpleClassInstance = new SimpleClass(context);
        }

        return simpleClassInstance;
    }
}
