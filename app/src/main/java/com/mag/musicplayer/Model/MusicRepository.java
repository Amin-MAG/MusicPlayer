package com.mag.musicplayer.Model;

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
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public Track getTrackById(long trackId) {
        for (Track track : tracks)
            if (track.getTrackId() == trackId) return track;
        return null;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

}
