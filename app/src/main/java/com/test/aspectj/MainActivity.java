package com.test.aspectj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.runntime.AspectjManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AspectjManager.init(whiteListFile, whiteListFileExceptionClass);
        try {
            int [] a = null;
            a[0] = 10;
        } catch (Exception e) {

        }
    }


    static Class [] whiteListFile = {
            MainActivity.class
    };
    static Class [][] whiteListFileExceptionClass = {
            {
                NullPointerException.class,
            },
    };

}
