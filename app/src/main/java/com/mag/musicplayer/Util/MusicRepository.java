package com.mag.musicplayer.Util;

import com.mag.musicplayer.Model.Track;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {

    // Single Repository

    private static MusicRepository instance;

    public static MusicRepository getInstance() {
        if (instance == null)
            instance = new MusicRepository();
        return instance;
    }

    private MusicRepository() {
    }

    // Music

    private List<Track> tracks = new ArrayList<>();

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

}
