package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.mag.musicplayer.Controller.Fragment.TrackPlayerFragment;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.viewmodel.TrackViewModel;

public class TrackPlayerActivity extends SingleFragmentActivity  {

    public static final String TAG_TRACK_PLAYER_FRAGMENT = "tag_track_player_fragment";

    private TrackViewModel viewModel;

    private TrackPlayerFragment trackPlayerFragment;
    private static TrackPlayerActivity trackPlayerActivity;

    public static Intent newIntent(Context context) {
        return new Intent(context, TrackPlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        trackPlayerActivity = this;

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


    public static TrackPlayerActivity getTrackPlayerActivity() {
        return trackPlayerActivity;
    }

}
