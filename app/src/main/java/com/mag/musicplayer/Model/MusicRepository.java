package com.mag.musicplayer.Model;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<Track> shuffleTracks;
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private boolean isShuffleMode = false;

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Track> getShuffleTracks() {
        return shuffleTracks;
    }

    public List<Track> getTracks(String search) {
        List<Track> target = new ArrayList<>();
        for (Track track : tracks)
            if (track.getTrackTitle().toLowerCase().contains(search) || track.getAlbumName().toLowerCase().contains(search) || track.getArtistName().toLowerCase().contains(search))
                target.add(track);
        return target;
    }

    public int getTrackIndex(Track track) {
        List<Track> allItems = isShuffleMode ? shuffleTracks : tracks;
        for (int i = 0; i < allItems.size(); i++)
            if (allItems.get(i).getTrackId() == track.getTrackId())
                return i;
        return 0;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Album> getAlbums(String search) {
        List<Album> target = new ArrayList<>();
        for (Album album : albums)
            if (album.getAlbumTitle().toLowerCase().contains(search) || album.getArtistName().toLowerCase().contains(search))
                target.add(album);
        return target;
    }


    public List<Artist> getArtists() {
        return artists;
    }

    public List<Artist> getArtists(String search) {
        List<Artist> target = new ArrayList<>();
        for (Artist artist : artists)
            if (artist.getArtistName().toLowerCase().contains(search))
                target.add(artist);
        return target;
    }


    public Track getTrackById(long trackId) {
        for (Track track : tracks)
            if (track.getTrackId() == trackId) return track;
        return null;
    }

    public List<Track> getTrackByAlbum(Album album) {
        List<Track> items = new ArrayList<>();
        for (Track track : tracks)
            if (track.getAlbumName().equals(album.getAlbumTitle())) items.add(track);
        return items;
    }

    public List<Track> getTrackByArtist(Artist artist) {
        List<Track> items = new ArrayList<>();
        ;
        for (Track track : tracks)
            if (track.getArtistName().equals(artist.getArtistName())) items.add(track);
        return items;
    }

    public void makeShuffle() {
        shuffleTracks = new ArrayList<>(tracks);
        Collections.shuffle(shuffleTracks);
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void setShuffleMode(boolean shuffleMode) {
        isShuffleMode = shuffleMode;
    }

    public boolean isShuffle() {
        return isShuffleMode;
    }

}
