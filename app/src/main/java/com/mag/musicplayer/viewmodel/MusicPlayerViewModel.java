package com.mag.musicplayer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.repository.MusicPlayer;

public class MusicPlayerViewModel extends AndroidViewModel {

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    public MusicPlayerViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMusics() {
        musicPlayer.loadMusics(getApplication().getContentResolver());
    }

    public MutableLiveData<Boolean> isPlayingMusic() {
        return musicPlayer.isPlaying();
    }

}
