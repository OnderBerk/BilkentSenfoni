package com.mobileproje;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MyIntentService extends IntentService {

    JSONArray array;
    int imagess[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    ArrayList<Concert> concertlist;
    private static final String TAG_CONCERT = "concert";
    private static final String TAG_NAME = "name";
    private static final String TAG_PLACE = "place";
    private static final String TAG_PRICE = "price";
    private static final String TAG_IMAGE = "img";
    public MyIntentService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        concertlist = new  ArrayList<Concert>();

        String filename = intent.getStringExtra("filename");

        String jsonfileContent = loadFileFromAsset(filename);

        if (jsonfileContent != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonfileContent);

                // Getting JSON Array node
                array = jsonObj.getJSONArray(TAG_CONCERT);

                // looping through all Contacts
                for (int i = 0; i < array.length(); i++) {
                    JSONObject concert = array.getJSONObject(i);

                    String name = concert.getString(TAG_NAME);
                    String place = concert.getString(TAG_PLACE);
                    int price = concert.getInt(TAG_PRICE);
                    String image=imagess[i]+concert.getString(TAG_IMAGE);

                    Concert a = new Concert(name, place, price,image);
                    concertlist.add(a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent broascastIntent = new Intent();
        Bundle b = new Bundle();
        b.putParcelableArrayList("liste", concertlist);
        broascastIntent.putExtras(b);

        broascastIntent.setAction("CONCERT_JSON_PARSE_ACTION");

        getBaseContext().sendBroadcast(broascastIntent);

        Log.d("Service",":servis END" );
    }
    private String loadFileFromAsset(String fileName) {
        String jsonfileContent = null;
        try {

            InputStream is = getBaseContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            jsonfileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonfileContent;
    }
}
