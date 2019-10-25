package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Controller.Fragment.TrackPlayerFragment;
import com.mag.musicplayer.Model.Track;

public class TrackPlayerActivity extends SingleFragmentActivity implements TrackPlayerFragment.TrackPlayerCallback {

    public static final String TAG_TRACK_PLAYER_FRAGMENT = "tag_track_player_fragment";
    private static Track actvitiyTrack;
    private TrackPlayerFragment trackPlayerFragment;
    private static TrackPlayerActivity trackPlayerActivity;

    private static TrackActivityCallback activityCallback;

    public static Intent newIntent(Context context, Track track, TrackActivityCallback callback) {
        actvitiyTrack = track;
        activityCallback = callback;
        return new Intent(context, TrackPlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackPlayerActivity = this;

    }

    @Override
    public Fragment getFragment() {
        trackPlayerFragment = TrackPlayerFragment.newInstance(actvitiyTrack);
        return trackPlayerFragment;
    }

    @Override
    public String getTagName() {
        return TAG_TRACK_PLAYER_FRAGMENT;
    }

    @Override
    public Track getTrackDistance(int distance) {
        return activityCallback.getTrackDistanceFromAdapter(distance);
    }

    public static TrackPlayerActivity getTrackPlayerActivity() {
        return trackPlayerActivity;
    }

    public void update(Track track) {
        trackPlayerFragment.update(track);
    }


    public interface TrackActivityCallback {
        Track getTrackDistanceFromAdapter(int distance);
    }

}
