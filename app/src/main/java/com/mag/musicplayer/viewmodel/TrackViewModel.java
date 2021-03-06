package com.mag.musicplayer.viewmodel;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.MusicPlayer;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.util.MusicUtil;

import java.io.IOException;
import java.util.List;

public class TrackViewModel extends AndroidViewModel {

    private MutableLiveData<Track> track = new MutableLiveData<>();

    private TrackRepository trackRepository = TrackRepository.getInstance();
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    public TrackViewModel(@NonNull Application application) {
        super(application);
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

    /* Track Activity Methods */
    public void seekPlayingMusicTo(int progressChangedValue) {
        getMediaPlayer().getValue().seekTo((int) ((double) (getPlayingRawLength() * progressChangedValue) / 100));
    }

    public void onPlayPauseBtnClicked() {
        if (isPlayingMusic().getValue())
            musicPlayer.pause();
        else
            musicPlayer.resume();
    }

    public void onPreviousBtnClicked() {
        int index = getPlayingTrack().getValue().getIndex() - 1;
        if (index < 0)
            index += getPlayingList().getValue().size();
        try {
            musicPlayer.playMusic(musicPlayer.getPlayingList().getValue().get(index), getApplication());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNextBtnClicked() {
        int index = getPlayingTrack().getValue().getIndex() + 1;
        if (index >= getPlayingList().getValue().size())
            index = 0;
        try {
            musicPlayer.playMusic(musicPlayer.getPlayingList().getValue().get(index), getApplication());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Logic

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> search(String searchingString) {
        return trackRepository.getTracks(searchingString.toLowerCase());
    }

    public String getPlayedTime() {
        return MusicUtil.getStringTime(getMediaPlayer().getValue().getCurrentPosition() / 1000);
    }

    public String getShortTitle() {
        return getTitle().length() > 25 ? getTitle().substring(0, 25) + "..." : getTitle();
//        return getTitle().length() > 30 ? getTitle().substring(0, 30) + "..." : getTitle();
    }

    /* Playing Track */

    public int getPlayedPercentage() {
        return (int) (((double) (getMediaPlayer().getValue().getCurrentPosition()) / getPlayingRawLength()) * 100);
    }

    public String getLength() {
        return MusicUtil.getStringTime(track.getValue().getLength() / 1000);
    }

    public String getProgressTime(int progressChangedValue) {
        return MusicUtil.getStringTime((int) ((double) (progressChangedValue * getPlayingRawLength() / 1000) / 100));
    }


    // Getter

    /* Track */

    public String getTitle() {
        return track.getValue().getTitle();
    }

    public String getArtistName() {
        return track.getValue().getArtist();
    }

    public Uri getCoverSrc() {
        return track.getValue().getImagePath();
    }

    public Track getTrack() {
        return track.getValue();
    }

    /* Playing Track */

    public MutableLiveData<Boolean> isPlayingMusic() {
        return musicPlayer.isPlaying();
    }

    public boolean isPlayingTrack() {
        if (getPlayingTrack().getValue() != null && track.getValue().getTrackId() == getPlayingTrack().getValue().getTrackId())
            return true;
        return false;
    }


    public MutableLiveData<Track> getPlayingTrack() {
        return musicPlayer.getPlayingTrack();
    }

    public MutableLiveData<MediaPlayer> getMediaPlayer() {
        return musicPlayer.getMediaPlayer();
    }

    public String getPlayingTitle() {
        if (getPlayingTrack().getValue() == null) return "";
        return getPlayingTrack().getValue().getTitle().length() > 25 ? getPlayingTrack().getValue().getTitle().substring(0, 25) + "..." : getPlayingTrack().getValue().getTitle();
    }

    public String getPlayingArtist() {
        if (getPlayingTrack().getValue() == null) return "";
        return getPlayingTrack().getValue().getArtist();
    }

    public int getPlayingRawLength() {
        if (getPlayingTrack().getValue() == null) return 0;
        return getPlayingTrack().getValue().getLength();
    }

    public String getPlayingLength() {
        if (getPlayingTrack().getValue() == null) return "";
        return MusicUtil.getStringTime(getPlayingRawLength() / 1000);
    }

    public Uri getPlayingCoverSrc() {
        if (getPlayingTrack().getValue() == null) return null;
        return getPlayingTrack().getValue().getImagePath();
    }

    public MutableLiveData<Boolean> isShuffle() {
        return trackRepository.isShuffle();
    }

    public MutableLiveData<Boolean> isRepeating() {
        return trackRepository.isRepeating();
    }

    public MutableLiveData<List<Track>> getPlayingList() {
        return musicPlayer.getPlayingList();
    }

    // Setter

    public void setTrack(Track playingTrack) {
        this.track.setValue(playingTrack);
    }

    public List<Track> getTrackList() {
        return trackRepository.getAllTracks();
    }

    public void setPlayingList(List<Track> tracks) {

        for (int i = 0; i < tracks.size(); i++)
            tracks.get(i).setIndex(i);

        for (int i = 0; i < tracks.size(); i++)
            Log.d("setPlayingList", "setPlayingList: " + tracks.get(i).getIndex());


        getPlayingList().setValue(tracks);
    }

}
