package com.github.brycemcd.bikedockfinder;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StationApiParser {

    public static final String URL = "https://gbfs.citibikenyc.com/gbfs/en/station_status.json";

    // https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    // NOTE: in clojure, this is just `open`
    public static JSONObject getStationData() {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        JSONObject apiResult;

        try {
            URL url = new URL(StationApiParser.URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            apiResult = new JSONObject(buffer.toString());
            Log.d("JSON PARSE", apiResult.toString());
        } catch (JSONException e) {
            apiResult = new JSONObject();
            Log.e("JSON PARSE", "OH NOES", e);
        }

        return apiResult;
    }
}
