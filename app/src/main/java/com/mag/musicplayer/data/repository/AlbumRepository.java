package com.mag.musicplayer.data.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mag.musicplayer.data.model.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumRepository {

    private static AlbumRepository instance;

    private AlbumRepository() {
    }

    public static AlbumRepository getInstance() {
        if (instance == null)
            instance = new AlbumRepository();
        return instance;
    }


    private List<Album> allAlbums = new ArrayList<>();


    public List<Album> getAllAlbums() {
        return allAlbums;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Album> getAlbums(String search) {
        return allAlbums.stream().filter(album -> album.getAlbumTitle().toLowerCase().contains(search) || album.getArtistName().toLowerCase().contains(search)).collect(Collectors.toList());
    }

    public void setAllAlbums(List<Album> allAlbums) {
        this.allAlbums = allAlbums;
    }

}
