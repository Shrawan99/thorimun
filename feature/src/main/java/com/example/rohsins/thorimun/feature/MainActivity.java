package com.example.rohsins.thorimun.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.*;

public class MainActivity extends AppCompatActivity {

    String data;
    JSONArray jsonArray;
    Boolean dataReady = false;

    public void print(String value) {
        Log.i("TestIO", value);
    }

    public void print(int value) {
        Log.i("TestIO", String.valueOf(value));
    }

    Runnable dataRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                String path = "http://thorimun.gov.np/newsfeed";

                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                data = bufferedReader.readLine();
                jsonArray = new JSONArray(data);
//                print(jsonArray.getJSONObject(0).getString("Title"));

                dataReady = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Thread dataThread = new Thread(dataRunnable);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataThread.start();

        while (!dataReady);

        try {
            LinearLayout dLayout = (LinearLayout) findViewById(R.id.dLayout);
            LinearLayout subLayout = new LinearLayout(MainActivity.this);
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            subLayout.setPadding(10,50,10,0);
//            subLayout.setScroll

            TextView textView = new TextView(MainActivity.this);

                for (int i = 0; i < jsonArray.length(); i++) {
                    textView.append(jsonArray.getJSONObject(i).getString("Title") + "\n\n");
                }
//            print(jsonArray.getJSONObject(0).getString("Title"));
//            textView.setText(jsonArray.getJSONObject(0).getString("Title"));
//            textView.setText("hello");
            subLayout.addView(textView);
            dLayout.addView(subLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
