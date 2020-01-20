package com.mag.musicplayer.viewmodel;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.MusicPlayer;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.util.MusicUtil;

import java.io.IOException;

public class TrackViewModel extends AndroidViewModel {

    private MutableLiveData<Track> track = new MutableLiveData<>();

    private TrackRepository trackRepository;
    private MusicPlayer musicPlayer;

    public TrackViewModel(@NonNull Application application) {
        super(application);

        this.trackRepository = TrackRepository.getInstance();
        this.musicPlayer = MusicPlayer.getInstance();

    }


    // Events

    /* Track List Methods */

    public void onTrackClicked() {

        try {
            musicPlayer.playMusic(track.getValue(), getApplication());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void onTrackClicked(Track track) {

        try {
            musicPlayer.playMusic(track, getApplication());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Track Activity Methods */

    public void seekPlayingMusicTo(int progressChangedValue) {
        getMediaPlayer().getValue().seekTo((int) ((double) (getTrackRawLength() * progressChangedValue) / 100));
    }

    public void onPlayPauseBtnClicked() {
        if (isPlayingMusic().getValue())
            musicPlayer.pause();
        else
            musicPlayer.resume();
    }

    public void onPreviousBtnClicked() {
        trackRepository.goPreviousTrack();
    }

    public void onNextBtnClicked() {
        trackRepository.goNextTrack();
    }


    // Logic

    public String getLength() {
        return MusicUtil.getStringTime(getTrackRawLength() / 1000);
    }

    public String getProgressTime(int progressChangedValue) {
        return MusicUtil.getStringTime((int) ((double) (progressChangedValue * getTrackRawLength() / 1000) / 100));
    }

    public int getPlayedPercentage() {
        return (int) (((double) (getMediaPlayer().getValue().getCurrentPosition()) / getTrackRawLength()) * 100);
    }

    public String getPlayedTime() {
        return MusicUtil.getStringTime(getMediaPlayer().getValue().getCurrentPosition() / 1000);
    }

    public String getShortTitle() {
        return getTitle().length() > 30 ? getTitle().substring(0, 30) + "..." : getTitle();
    }


    // Getter

    public String getTitle() {
        return track.getValue().getTrackTitle();
    }

    public String getArtistName() {
        return track.getValue().getArtistName();
    }

    public int getTrackRawLength() {
        return track.getValue().getTrackLength();
    }

    public MutableLiveData<Boolean> isPlayingMusic() {
        return musicPlayer.isPlaying();
    }

    public boolean isPlayingTrack() {
        if (getPlayingTrack().getValue() != null && track.getValue().getTrackId() == getPlayingTrack().getValue().getTrackId())
            return true;
        return false;
    }

    public Uri getCoverSrc() {
        return track.getValue().getImagePath();
    }

    public MutableLiveData<Track> getPlayingTrack() {
        return musicPlayer.getPlayingTrack();
    }

    public Track getTrack() {
        return track.getValue();
    }

    public MutableLiveData<MediaPlayer> getMediaPlayer() {
        return musicPlayer.getMediaPlayer();
    }


    // Setter

    public void setTrack(Track playingTrack) {
        this.track.setValue(playingTrack);
    }


}
