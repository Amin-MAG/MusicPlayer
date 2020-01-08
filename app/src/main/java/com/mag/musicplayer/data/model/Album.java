package com.mag.musicplayer.data.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album {

    private UUID albumId;
    private String albumTitle;
    private String artistName;
    private Uri imagePath;
    private List<Long> tracks = new ArrayList<>();


    public Album(String albumTitle, String artistName, Uri imagePath) {
        this.albumId = UUID.randomUUID();
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.imagePath = imagePath;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public List<Long> getTracks() {
        return tracks;
    }

}
