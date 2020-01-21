package com.mag.musicplayer.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.mag.musicplayer.view.fragment.TrackPlayerFragment;

public class TrackPlayerActivity extends SingleFragmentActivity {

    public static final String TAG_TRACK_PLAYER_FRAGMENT = "tag_track_player_fragment";

    private TrackPlayerFragment trackPlayerFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, TrackPlayerActivity.class);
    }

    @Override
    public Fragment getFragment() {
        trackPlayerFragment = TrackPlayerFragment.newInstance();
        return trackPlayerFragment;
    }

    @Override
    public String getTagName() {
        return TAG_TRACK_PLAYER_FRAGMENT;
    }

}
