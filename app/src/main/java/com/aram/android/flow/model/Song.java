package com.aram.android.flow.model;

import android.net.Uri;

import java.time.Duration;
import java.util.Date;

/**
 * Created by bharatvah on 17-12-2017.
 */

public class Song {
    private long id;
    private String title;
    private String artist;
    private Duration duration;
    private Uri trackUri;

    public Song(long id, Uri trackUri, String title, String artist, Duration duration){
        this.id=id;
        this.trackUri = trackUri;
        this.title=title;
        this.artist=artist;
        this.duration = duration;
    }

    public void setTrackUri(Uri trackUri) {
        this.trackUri = trackUri;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public Duration getDuration(){ return duration;}
    public Uri getTrackUri(){ return  trackUri;}


}
