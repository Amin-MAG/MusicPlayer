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

public class TrackViewModel extends AndroidViewModel {

    private MutableLiveData<Track> playingTrack;

    private TrackRepository trackRepository;
    private MusicPlayer musicPlayer;

    public TrackViewModel(@NonNull Application application) {
        super(application);

        this.trackRepository = TrackRepository.getInstance();
        this.musicPlayer = MusicPlayer.getInstance();
        this.playingTrack = musicPlayer.getPlayingTrack();

    }

    public MutableLiveData<Boolean> isPlayingMusic() {
        return musicPlayer.isPlaying();
    }

    public Uri getCoverSrc() {
        return playingTrack.getValue().getImagePath();
    }

    public MutableLiveData<Track> getPlayingTrack() {
        return playingTrack;
    }

    public MutableLiveData<MediaPlayer> getMediaPlayer() {
        return musicPlayer.getMediaPlayer();
    }


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

    public String getTrackTitle() {
        return playingTrack.getValue().getTrackTitle();
    }

    public String getArtistName() {
        return playingTrack.getValue().getArtistName();
    }

    public int getTrackRawLength() {
        return playingTrack.getValue().getTrackLength();
    }

    public String getTrackLength() {
        return MusicUtil.getStringTime(getTrackRawLength()/1000);
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
}
