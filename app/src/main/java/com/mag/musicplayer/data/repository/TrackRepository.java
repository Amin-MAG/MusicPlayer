package com.mag.musicplayer.data.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.data.model.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackRepository {

    // Single Repository

    private static TrackRepository instance;

    public static TrackRepository getInstance() {
        if (instance == null)
            instance = new TrackRepository();
        return instance;
    }

    private TrackRepository() {
        isShuffleMode.setValue(false);
        isRepeatingMode.setValue(false);
    }

    // Music

    private List<Track> allTracks = new ArrayList<>();


    private List<Track> shuffleTracks;

    private MutableLiveData<Boolean> isShuffleMode = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRepeatingMode = new MutableLiveData<>();

    public void setAllTracks(List<Track> allTracks) {
        this.allTracks = allTracks;
    }

    public List<Track> getAllTracks() {
        return allTracks;
    }

    public List<Track> getShuffleTracks() {
        return shuffleTracks;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> getTracks(String search) {
        return allTracks.stream().filter(track -> track.getTitle().toLowerCase().contains(search) || track.getAlbumName().toLowerCase().contains(search) || track.getArtist().toLowerCase().contains(search)).collect(Collectors.toList());
    }

    public int getTrackIndex(Track track) {
        List<Track> allItems = isShuffleMode.getValue() ? shuffleTracks : allTracks;
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
            if (track.getArtist().equals(artist.getArtistName())) items.add(track);
        return items;
    }

    public void makeShuffle() {
        shuffleTracks = new ArrayList<>(allTracks);
        Collections.shuffle(shuffleTracks);
    }


    public void switchShuffle() {
        isShuffleMode.setValue(!isShuffleMode.getValue());
        if (isShuffleMode.getValue()) makeShuffle();
    }

    public void switchRepeating() {
        this.isRepeatingMode.setValue(!isRepeatingMode.getValue());
    }

    public MutableLiveData<Boolean> isRepeating() {
        return isRepeatingMode;
    }

    public MutableLiveData<Boolean> isShuffle() {
        return isShuffleMode;
    }

    public Track goPreviousTrack() {
        return null;
    }

    public Track goNextTrack() {
        return null;
    }

}
