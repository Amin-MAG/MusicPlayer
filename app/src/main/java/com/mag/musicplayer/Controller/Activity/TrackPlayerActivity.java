package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Controller.Fragment.TrackPlayerFragment;
import com.mag.musicplayer.Model.Track;

public class TrackPlayerActivity extends SingleFragmentActivity implements TrackPlayerFragment.TrackPlayerCallback {

    public static final String TAG_TRACK_PLAYER_FRAGMENT = "tag_track_player_fragment";
    private static Track actvitiyTrack;


    public static Intent newIntent(Context context, Track track) {
        actvitiyTrack = track;
        return new Intent(context, TrackPlayerActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return TrackPlayerFragment.newInstance(actvitiyTrack);
    }

    @Override
    public String getTagName() {
        return TAG_TRACK_PLAYER_FRAGMENT;
    }

    @Override
    public void updateMusicBar(Track track) {

    }

}
