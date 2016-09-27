package com.example.tmutabazi.gentevents.remoteData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.tmutabazi.gentevents.model.EventDO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by tmutabazi on 9/23/2016.
 */

public class DataDownloader extends AsyncTask<Void, Void, ArrayList<EventDO> > {

   private String urlString = "https://datatank.stad.gent/4/toerisme/visitgentevents.json";
    InputStream inputStream;
    HttpURLConnection httpURLConnection;
    ProgressDialog progressDialog;
    StringBuilder buffer = null;
    ArrayList<EventDO> eventDOs;
    Context context;
    ListView listView;

    public DataDownloader( Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<EventDO> doInBackground(Void... paramas) {
        try
        {
            URL gentUrl = new URL(urlString.toString());
            httpURLConnection = (HttpURLConnection) gentUrl.openConnection();
            inputStream = httpURLConnection.getInputStream();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            buffer = new StringBuilder();
            String line;
            while ((line =br.readLine())!= null) {
                buffer.append(line);
                buffer.append("\n");
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            httpURLConnection.disconnect();

        }

        return getDataFromJson(buffer.toString());
    }

    @Override

    protected void onPreExecute(){
        super.onPreExecute();
        eventDOs = new ArrayList<EventDO>();
        progressDialog = ProgressDialog.show(context, "Downloading", "Downloading API data");
    }
    @Override
    protected void onPostExecute(ArrayList<EventDO> result){
        super.onPostExecute(result);
        progressDialog.dismiss();

    }


    private ArrayList<EventDO> getDataFromJson(String jsonLine){

        String title;
        JSONArray image;

        try {
            JSONArray array = new JSONArray(jsonLine);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                title = jsonObject.getString("title");
                image = new JSONArray(jsonObject.getString("images"));
                eventDOs.add(new EventDO(image.get(0).toString(), title));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventDOs;


    }
}
