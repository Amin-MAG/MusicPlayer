package com.mag.musicplayer.Controller.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Controller.Fragment.TrackPlayerFragment;
import com.mag.musicplayer.R;

public class TrackPlayerActivity extends SingleFragmentActivity {

    public static final String TAG_TRACK_PLAYER_FRAGMENT = "tag_track_player_fragment";

    @Override
    public Fragment getFragment() {
        return TrackPlayerFragment.newInstance();
    }

    @Override
    public String getTagName() {
        return TAG_TRACK_PLAYER_FRAGMENT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_player);
    }
}
