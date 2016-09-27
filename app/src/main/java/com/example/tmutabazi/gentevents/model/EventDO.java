package com.example.tmutabazi.gentevents.model;

/**
 * Created by tmutabazi on 9/25/2016.
 * This is the dataobject class that only contains the information we need which the title and image
 */

public class EventDO {

    String title;
    String image;

    public EventDO(String image, String title) {
        this.image = image;
        this.title =title;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
