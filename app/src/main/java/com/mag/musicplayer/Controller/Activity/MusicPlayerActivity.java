package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.mag.musicplayer.Controller.Fragment.AlbumListFragment;
import com.mag.musicplayer.Controller.Fragment.ArtistListFragment;
import com.mag.musicplayer.Controller.Fragment.MusicBarFragment;
import com.mag.musicplayer.Controller.Fragment.MusicListFragment;
import com.mag.musicplayer.Controller.Fragment.MusicPlayerViewPagerFragment;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;
import com.mag.musicplayer.Util.UiUtil;

public class MusicPlayerActivity extends AppCompatActivity implements MusicPlayerViewPagerFragment.SyncTabViewPager, MusicListFragment.MusicListUiCallback, MusicBarFragment.MusicBarCallback, AlbumListFragment.AlbumListUiCallback, ArtistListFragment.ArtistListUiCallback {


    public static final String TAG_FRAGMENT_FILTER_ITEMS_LIST = "tag_fragment_filter_items_list";
    public static final String TAG_FRAGMENT_MUSIC_BAR = "tag_fragment_music_bar";
    public static final String TAG_MUSIC_PLAYER_VIEW_PAGER = "tag_music_player_view_pager";

    private MusicBarFragment musicBarFragment = MusicBarFragment.newInstance();
    private MusicPlayerViewPagerFragment musicPlayerViewPagerFragment = MusicPlayerViewPagerFragment.newInstance();
    private TabLayout tabLayout;

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicPlayerActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateMusicBar(null);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        MusicPlayer.getInstance().loadMusics(getContentResolver());

        UiUtil.changeFragment(getSupportFragmentManager(), musicPlayerViewPagerFragment, R.id.musicPlayerActivity_viewPagerFrame, true, TAG_MUSIC_PLAYER_VIEW_PAGER);
        UiUtil.changeFragment(getSupportFragmentManager(), musicBarFragment, R.id.musicPlayerActivity_trackFrame, true, TAG_FRAGMENT_MUSIC_BAR);

        tabLayout = findViewById(R.id.musicPlayerActivity_tabLayout);
        tabLayout.setupWithViewPager(musicPlayerViewPagerFragment.getViewPager());

    }

    @Override
    public void updateMusicBar(Track track) {
        musicBarFragment.updateBar(track);
    }

    @Override
    public Track getNext() {
        if (MusicRepository.getInstance().isRepeatingMode())
            return getTrackDistance(0);
        return getTrackDistance(+1);
    }

    @Override
    public Track getTrackDistance(int n) {
        if (MusicRepository.getInstance().isShuffle()) {
            int index = MusicRepository.getInstance().getTrackIndex(MusicPlayer.getInstance().getCurrentTrack()) + n;
            int size = MusicRepository.getInstance().getTracks().size();
            if (index % size < 0) index += size;
            index %= size;
            Track track = MusicRepository.getInstance().getShuffleTracks().get(index);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
            musicPlayerViewPagerFragment.getMusicList().getAdapter().updateUi();
            return track;
        } else {
            Log.d("SomsomSom", musicPlayerViewPagerFragment.getMusicList() + " ");
            int index = musicPlayerViewPagerFragment
                    .getMusicList()
                    .getAdapter()
                    .findTrackIndex(MusicPlayer
                            .getInstance()
                            .getCurrentTrack()) + n;
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
