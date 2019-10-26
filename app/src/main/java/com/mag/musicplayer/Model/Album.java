package com.mag.musicplayer.Model;

import android.net.Uri;

public class Album {

    private long albumId;
    private String albumTitle;
    private long artistId;
    private Uri imagePath;


    public Album(long albumId, String albumTitle, long artistId, Uri imagePath) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.artistId = artistId;
        this.imagePath = imagePath;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public long getArtistId() {
        return artistId;
    }

    public Uri getImagePath() {
        return imagePath;
    }

}
