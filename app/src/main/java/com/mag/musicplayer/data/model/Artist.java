package com.mag.musicplayer.data.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Artist {

    private UUID artistiId;
    private String artistName;
    private Uri imagePath;
    private List<Long> tracks = new ArrayList<>();
    private List<UUID> albums = new ArrayList<>();

    public Artist(String artistName, Uri imagePath) {
        this.artistiId = UUID.randomUUID();
        this.artistName = artistName;
        this.imagePath = imagePath;
    }

    public UUID getArtistiId() {
        return artistiId;
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

    public List<UUID> getAlbums() {
        return albums;
    }

}
