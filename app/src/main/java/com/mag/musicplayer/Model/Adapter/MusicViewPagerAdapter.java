package com.mag.musicplayer.Model.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public static final String VIEW_PAGER__MUSIC_LIST = "view_pager__music_list";
    public static final String VIEW_PAGER_ALBUM_LIST = "view_pager__album_list";
    public static final String VIEW_PAGER__ARTIST_LIST = "view_pager__artist_list";
    public static final String VIEW_PAGER__PLAY_LIST = "view_pager__play_list";
    public static final String VIEW_PAGER__FILE_EXPLORER = "view_pager__file_explorer";

    public MusicViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFrag(Fragment fragment, String title, String tag) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public Fragment getmFragmentList(String name) {
        switch (name) {
            case VIEW_PAGER__MUSIC_LIST:
                return mFragmentList.get(0);
            case VIEW_PAGER_ALBUM_LIST:
                return mFragmentList.get(1);
            case VIEW_PAGER__ARTIST_LIST:
                return mFragmentList.get(2);
            case VIEW_PAGER__PLAY_LIST:
                return mFragmentList.get(3);
            case VIEW_PAGER__FILE_EXPLORER:
                return mFragmentList.get(4);
            default:
                break;
        }
        return null;
    }

}
