package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mag.musicplayer.Controller.Fragment.AlbumListFragment;
import com.mag.musicplayer.Controller.Fragment.FilterItemsFragment;
import com.mag.musicplayer.Controller.Fragment.MusicBarFragment;
import com.mag.musicplayer.Controller.Fragment.MusicListFragment;
import com.mag.musicplayer.Controller.Fragment.MusicPlayerViewPagerFragment;
import com.mag.musicplayer.Controller.Fragment.SearchFragment;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;
import com.mag.musicplayer.Util.UiUtil;

public class MusicPlayerActivity extends AppCompatActivity implements MusicListFragment.MusicListUiCallback, MusicBarFragment.MusicBarCallback, AlbumListFragment.AlbumListUiCallback {


    public static final String TAG_FRAGMENT_MUSIC_LIST = "tag_fragment_music_list";
    public static final String TAG_FRAGMENT_SEARCH = "tag_fragment_search";
    public static final String TAG_FRAGMENT_FILTER_ITEMS_LIST = "tag_fragment_filter_items_list";
    public static final String TAG_FRAGMENT_MUSIC_BAR = "tag_fragment_music_bar";
    public static final String TAG_MUSIC_PLAYER_VIEW_PAGER = "tag_music_player_view_pager";

    private MusicBarFragment musicBarFragment = MusicBarFragment.newInstance();
    private FilterItemsFragment filterItemsFragment = FilterItemsFragment.newInstance();
    private MusicPlayerViewPagerFragment musicPlayerViewPagerFragment = MusicPlayerViewPagerFragment.newInstance();
    private SearchFragment searchFragment = SearchFragment.newInstance();

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
        UiUtil.changeFragment(getSupportFragmentManager(), searchFragment, R.id.musicPlayerActivity_searchFrame, true, TAG_FRAGMENT_SEARCH);
        UiUtil.changeFragment(getSupportFragmentManager(), filterItemsFragment, R.id.musicPlayerActivity_itemsFrame, true, TAG_FRAGMENT_FILTER_ITEMS_LIST);
        UiUtil.changeFragment(getSupportFragmentManager(), musicBarFragment, R.id.musicPlayerActivity_trackFrame, true, TAG_FRAGMENT_MUSIC_BAR);

    }

    @Override
    public void updateMusicBar(Track track) {

        if (track == null) {
            musicBarFragment.updateBar();
            ;
        } else {
            musicBarFragment.updateBar(track);
        }

    }

    @Override
    public Track getNext() {
        return getTrackDistance(+1);
    }

    @Override
    public Track getTrackDistance(int n) {
        int index = musicPlayerViewPagerFragment.getMusicList().getAdapter().findTrackIndex(MusicPlayer.getInstance().getCurrentTrack()) + n;
        int size = musicPlayerViewPagerFragment.getMusicList().getAdapter().getItemCount();
        if (index % size < 0) index += size;
        index %= size;
        Track track = musicPlayerViewPagerFragment.getMusicList().getAdapter().getTracks().get(index);
        musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
        musicPlayerViewPagerFragment.getMusicList().getAdapter().updateUi();
        return track;
    }

    @Override
    public void updateRecyclerSelectedTrack(Track track) {
        musicPlayerViewPagerFragment.getMusicList().getAdapter().setSelectedTrack(track);
        musicPlayerViewPagerFragment.getAlbumList().getMusicListAdapter().setSelectedTrack(track);
    }

}
