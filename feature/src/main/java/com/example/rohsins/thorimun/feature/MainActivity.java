package com.example.rohsins.thorimun.feature;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
//import android.widget.LinearLayout;
//import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    ListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listNoticeHeader;
    HashMap<String, List<String>> listNoticeBody;
    String data;
    JSONArray jsonArray;
    Boolean dataReady = false;
    final String documentString = "PDF Document";

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

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        dataThread.start();

        while (!dataReady);

        try {
            listNoticeHeader = new ArrayList<String>();
            listNoticeBody = new HashMap<String, List<String>>();
            List<String> child;

                for (int i = 0; i < jsonArray.length(); i++) {
                    child = new ArrayList<String>();
                    listNoticeHeader.add(jsonArray.getJSONObject(i).getString("Title") + "\n");
                    if (jsonArray.getJSONObject(i).getString("Body").length() != 0) {
                        child.add(jsonArray.getJSONObject(i).getString("Body"));
                    }
                    if (jsonArray.getJSONObject(i).getString("Document").length() != 0) {
//                        child.add(jsonArray.getJSONObject(i).getString("Document"));
                        child.add(documentString);
                    }
                    listNoticeBody.put(listNoticeHeader.get(i), child);
                }

            listAdapter = new ListAdapter(this, listNoticeHeader, listNoticeBody);

            expandableListView.setAdapter(listAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (listAdapter.getChild(groupPosition, childPosition).toString().contentEquals(documentString)) {
                    try {
//                        print(jsonArray.getJSONObject(groupPosition).getString("Document"));
                        String urlLink = jsonArray.getJSONObject(groupPosition).getString("Document");
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(urlLink));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
    }
}
