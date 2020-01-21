package com.mag.musicplayer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mag.musicplayer.data.repository.MusicPlayer;

public class MusicViewModel extends AndroidViewModel {

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    public MusicViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMusics() {
        musicPlayer.loadMusics(getApplication().getContentResolver());
    }

}
