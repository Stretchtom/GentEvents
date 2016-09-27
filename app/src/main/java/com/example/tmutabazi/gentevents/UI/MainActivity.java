package com.example.tmutabazi.gentevents.UI;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.tmutabazi.gentevents.Util.EventArrayAdapter;
import com.example.tmutabazi.gentevents.model.EventDO;
import com.example.tmutabazi.gentevents.remoteData.DataDownloader;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ListActivity {

    ArrayList<EventDO> eventDOs = new ArrayList<EventDO>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the class that contains the asynctask that gets data from the API
        DataDownloader downloader = new DataDownloader(this);

        try {
            // Here is where the asynctask is executed
            eventDOs =downloader.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // We pass the data to the adpter to be shown on the activity
        // This is also passed with the context
        setListAdapter(new EventArrayAdapter(this,eventDOs));
    }
}


