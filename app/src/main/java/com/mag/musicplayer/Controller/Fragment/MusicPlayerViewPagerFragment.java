package com.mag.musicplayer.Controller.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mag.musicplayer.data.model.Adapter.MusicViewPagerAdapter;
import com.mag.musicplayer.R;

public class MusicPlayerViewPagerFragment extends Fragment {

    public static final String VIEW_PAGER__MUSIC_LIST = "view_pager__music_list";
    public static final String VIEW_PAGER_ALBUM_LIST = "view_pager__album_list";
    public static final String VIEW_PAGER__ARTIST_LIST = "view_pager__artist_list";
    public static final String VIEW_PAGER__PLAY_LIST = "view_pager__play_list";
    public static final String VIEW_PAGER__FILE_EXPLORER = "view_pager__file_explorer";
    private MusicViewPagerAdapter musicViewPagerAdapter;
    private ViewPager viewPager;

    private SyncTabViewPager syncer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getContext() instanceof SyncTabViewPager)
            syncer = (SyncTabViewPager) getContext();

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

        viewPager = view.findViewById(R.id.musicViewPagerFragment_viewPager);

        musicViewPagerAdapter = new MusicViewPagerAdapter(getFragmentManager());
        musicViewPagerAdapter.addFrag(MusicListFragment.newInstance(), "All Musics", VIEW_PAGER__MUSIC_LIST);
        musicViewPagerAdapter.addFrag(AlbumListFragment.newInstance(), "Albums", VIEW_PAGER_ALBUM_LIST);
        musicViewPagerAdapter.addFrag(ArtistListFragment.newInstance(), "Artists", VIEW_PAGER__ARTIST_LIST);
        musicViewPagerAdapter.addFrag(PlayListFragment.newInstance(), "Playlists", VIEW_PAGER__PLAY_LIST);
        musicViewPagerAdapter.addFrag(FileExplorerFragment.newInstance(), "File Explorer", VIEW_PAGER__FILE_EXPLORER);


        setViewpager();

        setTabLayout();

    }

    private void setViewpager() {
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
    }

    private void setTabLayout() {
        TabLayout tabLayout = syncer.getTablayout();
        for (int k = tabLayout.getTabCount(); k < musicViewPagerAdapter.getCount(); k++)
            tabLayout.addTab(tabLayout.newTab().setText(musicViewPagerAdapter.getPageTitle(k)));
        syncer.getTablayout().getTabAt(0).select();
        syncer.getTablayout().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public MusicListFragment getMusicList() {
        return (MusicListFragment) musicViewPagerAdapter.getmFragmentList(VIEW_PAGER__MUSIC_LIST);
    }

    public AlbumListFragment getAlbumList() {
        return (AlbumListFragment) musicViewPagerAdapter.getmFragmentList(VIEW_PAGER_ALBUM_LIST);
    }

    public ArtistListFragment getArtistList() {
        return (ArtistListFragment) musicViewPagerAdapter.getmFragmentList(VIEW_PAGER__ARTIST_LIST);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public interface SyncTabViewPager {
        TabLayout getTablayout();
    }

}
