package com.mag.musicplayer.Controller.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mag.musicplayer.Model.Adapter.MusicViewPagerAdapter;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.HashMap;
import java.util.List;

public class MusicPlayerViewPagerFragment extends Fragment {

    public static final String VIEW_PAGER__MUSIC_LIST = "view_pager__music_list";
    public static final String VIEW_PAGER_ALBUM_LIST = "view_pager__album_list";
    public static final String VIEW_PAGER__ARTIST_LIST = "view_pager__artist_list";
    public static final String VIEW_PAGER__PLAY_LIST = "view_pager__play_list";
    public static final String VIEW_PAGER__FILE_EXPLORER = "view_pager__file_explorer";
    private HashMap<String, Fragment> fragments = new HashMap<>();
    private MusicViewPagerAdapter musicViewPagerAdapter;
    private ViewPager viewPager;

    private  SyncTabViewPager syncer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getContext() instanceof SyncTabViewPager)
            syncer= (SyncTabViewPager) getContext();

    }

    public static MusicPlayerViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        MusicPlayerViewPagerFragment fragment = new MusicPlayerViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicPlayerViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_player_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        musicViewPagerAdapter = new MusicViewPagerAdapter(getFragmentManager());
        musicViewPagerAdapter.addFrag(MusicListFragment.newInstance(), "All Musics", VIEW_PAGER__MUSIC_LIST);
        musicViewPagerAdapter.addFrag(AlbumListFragment.newInstance(), "Albums", VIEW_PAGER_ALBUM_LIST);
        musicViewPagerAdapter.addFrag(ArtistListFragment.newInstance(), "Artists", VIEW_PAGER__ARTIST_LIST);
        musicViewPagerAdapter.addFrag(PlayListFragment.newInstance(), "Playlists", VIEW_PAGER__PLAY_LIST);
        musicViewPagerAdapter.addFrag(FileExplorerFragment.newInstance(), "File Explorer", VIEW_PAGER__FILE_EXPLORER);

        viewPager = view.findViewById(R.id.musicViewPagerFragment_viewPager);
        viewPager.setAdapter(musicViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                syncer.getTablayout().getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = syncer.getTablayout();
        for (int k = tabLayout.getTabCount() ; k < musicViewPagerAdapter.getCount(); k++)
            tabLayout.addTab(tabLayout.newTab().setText(musicViewPagerAdapter.getPageTitle(k)));
        syncer.getTablayout().getTabAt(0).select();


    }

    public MusicListFragment getMusicList() {
        return (MusicListFragment) fragments.get(VIEW_PAGER__MUSIC_LIST);
    }

    public AlbumListFragment getAlbumList() {
        return (AlbumListFragment) fragments.get(VIEW_PAGER_ALBUM_LIST);
    }

    public ArtistListFragment getArtistList() {
        return (ArtistListFragment) fragments.get(VIEW_PAGER__ARTIST_LIST);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public interface SyncTabViewPager{
        TabLayout getTablayout();
    }

}
