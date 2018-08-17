package com.example.rohsins.thorimun.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tView1 = new TextView(this);
        tView1.setText("what is up");

        LinearLayout dLayout = (LinearLayout) findViewById(R.id.dLayout);
        LinearLayout subLayout = new LinearLayout(this);
        subLayout.setOrientation(LinearLayout.HORIZONTAL);
        subLayout.addView(tView1);
        dLayout.addView(subLayout);

        try {
            String path = "http://thorimun.gov.np/newsfeed";
            URL url = new URL(path);
            Log.i("TestIO", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
