package com.mag.musicplayer.data.repository;

import com.mag.musicplayer.data.model.Album;

import java.util.ArrayList;
import java.util.List;

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

    public List<Album> getAlbums(String search) {
        List<Album> target = new ArrayList<>();
        for (Album album : allAlbums)
            if (album.getAlbumTitle().toLowerCase().contains(search) || album.getArtistName().toLowerCase().contains(search))
                target.add(album);
        return target;
    }

    public void setAllAlbums(List<Album> allAlbums) {
        this.allAlbums = allAlbums;
    }

}
