package com.mag.musicplayer.data.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.model.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Artist> getArtists(String search) {
        return allArtists.stream().filter((artist)-> artist.getArtistName().toLowerCase().contains(search)).collect(Collectors.toList());
    }

    public void setAllArtists(List<Artist> allArtists) {
        this.allArtists = allArtists;
    }

}
