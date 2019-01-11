package com.example.androidmemoryleakage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SimpleClass simpleClass; // 1

    private static MainActivity mainActivity; //2.
    private static WeakReference<MainActivity> mainActivity2; //2.

    private static TextView textView; // 3

    private static Object sampleInnerClassObject; //4 . Don't have static inner class References

    Thread thread; // 6

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


        sampleInnerClassObject = new SampleInnerClass(); //4 . Don't have any static inner class References


        // 5. Staring a Anonymous Inner AsyncTask will lead ML
        /*new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }.execute();*/

        new MyAsyncTask().execute(); // 5 . That's how we can avoid it


        new Thread().start(); // 6. Cause To ML

        //Having a Anonymous Inner Thread
        thread = new Thread(){ // 6
            @Override
            public void run() {
                if(!isInterrupted()){

                }
            }
        };
        thread.start();



        // 7. Having a Anonymous Inner Handler will lead Memory Leak
        /* new Handler(){
             @Override
             public void handleMessage(Message msg) {
                 super.handleMessage(msg);
             }
         }.postDelayed(new Runnable() {
             @Override
             public void run() {

             }
         }, Long.MAX_VALUE >> 1);  */

        // 7. Avoid using Anonymous or Anonymous inner classes, instead use static inner class
        new CustomHandler().postDelayed(new RunnableForHandler(), Long.MAX_VALUE >> 1);




        // 8. Having a Anonymous Inner Class will lead Memory Leak
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, Long.MAX_VALUE >> 1); */


        //8. Avoid using Anonymous or Anonymous inner classes, instead use static inner class
        new Timer().schedule(new MyTimerTask(), Long.MAX_VALUE >> 1);


    }


    private static class MyTimerTask extends TimerTask {
        @Override
        public void run() {

        }
    }


    private static class CustomHandler extends Handler {
        @Override public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    }

    private static class RunnableForHandler implements Runnable{
        @Override
        public void run() {

        }
    }


    // 5. Private static AsyncTask class
    private static class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


    class SampleInnerClass{
        // 4 .
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        textView = null; // 3. Dereference this  static view to avoid ML

        if(thread!=null){ // 6.
            thread.interrupt();
        }
    }
}
