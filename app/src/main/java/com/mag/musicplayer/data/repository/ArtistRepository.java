package com.mag.musicplayer.data.repository;

import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {

    private static ArtistRepository instance;

    private ArtistRepository() {
    }

    public static ArtistRepository getInstance() {
        if (instance == null)
            instance = new ArtistRepository();
        return instance;
    }


    private List<Artist> allArtists = new ArrayList<>();


    public List<Artist> getAllArtists() {
        return allArtists;
    }

    public List<Artist> getArtists(String search) {
        List<Artist> target = new ArrayList<>();
        for (Artist artist : allArtists)
            if (artist.getArtistName().toLowerCase().contains(search))
                target.add(artist);
        return target;
    }

    public void setAllArtists(List<Artist> allArtists) {
        this.allArtists = allArtists;
    }

}
