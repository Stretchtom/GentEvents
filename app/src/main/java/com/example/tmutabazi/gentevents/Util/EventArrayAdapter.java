package com.example.tmutabazi.gentevents.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmutabazi.gentevents.R;
import com.example.tmutabazi.gentevents.model.EventDO;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by tmutabazi on 9/27/2016.
 * This class populates the listview as it downloads the images
 */

public class EventArrayAdapter extends ArrayAdapter<EventDO> {
    private final Context context;
    private final ArrayList<EventDO> eventDOs;
    public EventArrayAdapter(Context context, ArrayList<EventDO> objects) {
        super(context, R.layout.event_list_layout, objects);
        this.context = context;
        this.eventDOs = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.event_list_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
       try {

           // Here is where we call the asynctask that downloads the images to put in the list
                 new DownloadImages(imageView,context,textView,eventDOs.get(position).getTitle()).execute(new URL(eventDOs.get(position).getImage()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Returns the view to the Main UI as the user scrolls
        return rowView;
    }

    // This AsynTask is the one the downloads the images

    class DownloadImages extends AsyncTask <URL,Void, Bitmap> {

        ImageView pictureImageView;
        TextView textView;
        Bitmap eventBMP;
        Context context;
        String text;
        public DownloadImages(ImageView imageView, Context context, TextView textView, String text) {

            this.pictureImageView = imageView;
            this.context = context;
            this.textView = textView;
            this.text = text;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            InputStream inputStream = null;
            URL urldisplay = urls[0];
            try {
                inputStream = urldisplay.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            eventBMP = BitmapFactory.decodeStream(inputStream);
            return eventBMP;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            pictureImageView.setImageBitmap(result);
            textView.setText(text);

        }
    }
}
