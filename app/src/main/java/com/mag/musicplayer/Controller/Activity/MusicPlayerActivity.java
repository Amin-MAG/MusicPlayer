package com.mag.musicplayer.Controller.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.mag.musicplayer.Controller.Fragment.MusicBarFragment;
import com.mag.musicplayer.Controller.Fragment.MusicPlayerViewPagerFragment;
import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.MusicPlayer;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.util.UiUtil;

public class MusicPlayerActivity extends AppCompatActivity implements MusicPlayerViewPagerFragment.SyncTabViewPager, MusicBarFragment.MusicBarCallback {


    public static final String TAG_FRAGMENT_FILTER_ITEMS_LIST = "tag_fragment_filter_items_list";
    public static final String TAG_FRAGMENT_MUSIC_BAR = "tag_fragment_music_bar";
    public static final String TAG_MUSIC_PLAYER_VIEW_PAGER = "tag_music_player_view_pager";
    public static final String ON_SAVE_INSTANT_IS_THE_FIRST_TIME = "on_save_instant_isTheFirstTime";
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 10002;

    private MusicBarFragment musicBarFragment = MusicBarFragment.newInstance();
    private MusicPlayerViewPagerFragment musicPlayerViewPagerFragment = MusicPlayerViewPagerFragment.newInstance();
    private TabLayout tabLayout;

    private boolean isTheFirstTime = false;

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicPlayerActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE:

                if (grantResults[0] != -1) {
                    MusicPlayer.getInstance().loadMusics(getContentResolver());
                    startActivity(newIntent(this));
                    finish();
                }

                break;
            default:
                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

//        updateMusicBar(null);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(ON_SAVE_INSTANT_IS_THE_FIRST_TIME, isTheFirstTime);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        MusicPlayer.getInstance().setCallback(new MusicPlayer.MusicPlayerCallback() {
            private Track nextTrack;

            @Override
            public Track getNextTrack() {
                nextTrack = getNext();
                return nextTrack;
            }

            @Override
            public void updateUiAutoSkip() {
                updateMusicBar(nextTrack);
//                if (TrackPlayerActivity.getTrackPlayerActivity() != null)
//                    TrackPlayerActivity.getTrackPlayerActivity().update(nextTrack);
            }

        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
        } else {
            MusicPlayer.getInstance().loadMusics(getContentResolver());
        }


//        if (savedInstanceState != null)
//            isTheFirstTime = savedInstanceState.getBoolean(ON_SAVE_INSTANT_IS_THE_FIRST_TIME);
//
//        if (!isTheFirstTime) {
        UiUtil.changeFragment(getSupportFragmentManager(), musicPlayerViewPagerFragment, R.id.musicPlayerActivity_viewPagerFrame, true, TAG_MUSIC_PLAYER_VIEW_PAGER);
        UiUtil.changeFragment(getSupportFragmentManager(), musicBarFragment, R.id.musicPlayerActivity_trackFrame, true, TAG_FRAGMENT_MUSIC_BAR);
//        Log.d("LifeCycle", getSupportFragmentManager().getFragments().size() + "");

//            isTheFirstTime = true;
//        }

        tabLayout = findViewById(R.id.musicPlayerActivity_tabLayout);
        tabLayout.setupWithViewPager(musicPlayerViewPagerFragment.getViewPager());

    }

    public void updateMusicBar(Track track) {
        musicBarFragment.updateBar(track);
    }

    public Track getNext() {
        if (TrackRepository.getInstance().isRepeatingMode())
            return getTrackDistance(0);
        return getTrackDistance(+1);
    }

    @Override
    public Track getTrackDistance(int n) {
        if (TrackRepository.getInstance().isShuffle()) {
            int index = TrackRepository.getInstance().getTrackIndex(MusicPlayer.getInstance().getPlayingTrack().getValue()) + n;
            int size = TrackRepository.getInstance().getAllTracks().size();
            if (index % size < 0) index += size;
            index %= size;
            Track track = TrackRepository.getInstance().getShuffleTracks().get(index);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().updateUi();
            return track;
        } else {
            int index = musicPlayerViewPagerFragment
                    .getMusicList()
                    .getAdapter()
                    .findTrackIndex(MusicPlayer
                            .getInstance()
                            .getPlayingTrack().getValue()) + n;
            Log.d("SomsomSom", index + " ");
            int size = musicPlayerViewPagerFragment.getMusicList().getAdapter().getItemCount();
            if (index % size < 0) index += size;
            index %= size;
            Track track = musicPlayerViewPagerFragment.getMusicList().getAdapter().getTracks().get(index);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().updateUi();
            return track;

        }
    }

    @Override
    public void updateRecyclerSelectedTrack(Track track) {
        musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
//        musicPlayerViewPagerFragment.getAlbumList().getMusicListAdapter().setSelectedTrack(track);
//        musicPlayerViewPagerFragment.getArtistList().getMusicListAdapter().setSelectedTrack(track);
    }

    @Override
    public TabLayout getTablayout() {
        return tabLayout;
    }

    public interface musicPlayerActivity {
        TabLayout getTablayout();
    }

}
