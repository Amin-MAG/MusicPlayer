package com.mag.musicplayer.viewmodel;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.MusicPlayerRepository;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.util.MusicUtil;

public class TrackViewModel extends AndroidViewModel {

    private MutableLiveData<Track> playingTrack;

    private TrackRepository trackRepository;
    private MusicPlayerRepository musicPlayerRepository;

    public TrackViewModel(@NonNull Application application) {
        super(application);

        this.trackRepository = TrackRepository.getInstance();
        this.musicPlayerRepository = MusicPlayerRepository.getInstance();
        this.playingTrack = musicPlayerRepository.getPlayingTrack();

    }

    public MutableLiveData<Boolean> isPlayingMusic() {
        return musicPlayerRepository.isPlaying();
    }

    public Uri getCoverSrc() {
        return playingTrack.getValue().getImagePath();
    }

    public MutableLiveData<Track> getPlayingTrack() {
        return playingTrack;
    }

    public MutableLiveData<MediaPlayer> getMediaPlayer() {
        return musicPlayerRepository.getMediaPlayer();
    }


    public void seekPlayingMusicTo(int progressChangedValue) {
        getMediaPlayer().getValue().seekTo((int) ((double) (getTrackLength() * progressChangedValue) / 100));
    }

    public void onPausePlayBtnClicked() {
        if (isPlayingMusic().getValue())
            musicPlayerRepository.pause();
        else
            musicPlayerRepository.resume();
    }

    public void onPreviousBtnClicked() {
        trackRepository.goPreviousTrack();
    }

    public void onNextBtnClicked() {
        trackRepository.goNextTrack();
    }

    public String getTrackTitle() {
        return playingTrack.getValue().getTrackTitle();
    }

    public String getArtistName() {
        return playingTrack.getValue().getArtistName();
    }

    public int getTrackLength() {
        return playingTrack.getValue().getTrackLength();
    }

    public String getProgressTime(int progressChangedValue) {
        return MusicUtil.getStringTime((int) ((double) (progressChangedValue * getTrackLength() / 1000) / 100));
    }

    public int getPlayedPercentage() {
        return (int) (((double) (getMediaPlayer().getValue().getCurrentPosition()) / getTrackLength()) * 100);
    }

    public String getPlayedTime() {
        return MusicUtil.getStringTime(getMediaPlayer().getValue().getCurrentPosition() / 1000);
    }
}
