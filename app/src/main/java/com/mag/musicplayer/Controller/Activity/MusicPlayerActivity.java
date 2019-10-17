package com.mag.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mag.musicplayer.Controller.Fragment.FilterItemsFragment;
import com.mag.musicplayer.Controller.Fragment.MusicListFragment;
import com.mag.musicplayer.Controller.Fragment.SearchFragment;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.UiUtil;

public class MusicPlayerActivity extends AppCompatActivity {


    public static final String TAG_FRAGMENT_MUSIC_LIST = "tag_fragment_music_list";
    public static final String TAG_FRAGMENT_SEARCH = "tag_fragment_search";
    public static final String TAG_FRAGMENT_FILTER_ITEMS_LIST = "tag_fragment_filter_items_list";

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicPlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        UiUtil.changeFragment(getSupportFragmentManager(), MusicListFragment.newInstance(), R.id.musicPlayerActivity_listFrame, true, TAG_FRAGMENT_MUSIC_LIST);
        UiUtil.changeFragment(getSupportFragmentManager(), SearchFragment.newInstance(), R.id.musicPlayerActivity_searchFrame, true, TAG_FRAGMENT_SEARCH);
        UiUtil.changeFragment(getSupportFragmentManager(), FilterItemsFragment.newInstance(), R.id.musicPlayerActivity_itemsFrame, true, TAG_FRAGMENT_FILTER_ITEMS_LIST);

    }

}
