package com.mag.musicplayer.data.repository;

import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.data.model.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackRepository {

    // Single Repository

    private static TrackRepository instance;

    public static TrackRepository getInstance() {
        if (instance == null)
            instance = new TrackRepository();
        return instance;
    }

    private TrackRepository() {
    }

    // Music

    private List<Track> allTracks = new ArrayList<>();


    private List<Track> shuffleTracks;

    private boolean isShuffleMode = false;
    private boolean isRepeatingMode = false;

    public void setAllTracks(List<Track> allTracks) {
        this.allTracks = allTracks;
    }

    public List<Track> getAllTracks() {
        return allTracks;
    }

    public List<Track> getShuffleTracks() {
        return shuffleTracks;
    }

    public List<Track> getTracks(String search) {
        List<Track> target = new ArrayList<>();
        for (Track track : allTracks)
            if (track.getTrackTitle().toLowerCase().contains(search) || track.getAlbumName().toLowerCase().contains(search) || track.getArtistName().toLowerCase().contains(search))
                target.add(track);
        return target;
    }

    public int getTrackIndex(Track track) {
        List<Track> allItems = isShuffleMode ? shuffleTracks : allTracks;
        for (int i = 0; i < allItems.size(); i++)
            if (allItems.get(i).getTrackId() == track.getTrackId())
                return i;
        return 0;
    }


    public Track getTrackById(long trackId) {
        for (Track track : allTracks)
            if (track.getTrackId() == trackId) return track;
        return null;
    }

    public List<Track> getTrackByAlbum(Album album) {
        List<Track> items = new ArrayList<>();
        for (Track track : allTracks)
            if (track.getAlbumName().equals(album.getAlbumTitle())) items.add(track);
        return items;
    }

    public List<Track> getTrackByArtist(Artist artist) {
        List<Track> items = new ArrayList<>();
        for (Track track : allTracks)
            if (track.getArtistName().equals(artist.getArtistName())) items.add(track);
        return items;
    }

    public void makeShuffle() {
        shuffleTracks = new ArrayList<>(allTracks);
        Collections.shuffle(shuffleTracks);
    }


    public void setShuffleMode(boolean shuffleMode) {
        isShuffleMode = shuffleMode;
    }

    public void setRepeatingMode(boolean repeatingMode) {
        isRepeatingMode = repeatingMode;
    }

    public boolean isRepeatingMode() {
        return isRepeatingMode;
    }

    public boolean isShuffle() {
        return isShuffleMode;
    }

    public Track goPreviousTrack() {
        return null;
    }

    public Track goNextTrack() {
        return null;
    }

}
