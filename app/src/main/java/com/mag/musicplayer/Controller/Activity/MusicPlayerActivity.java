package com.mag.musicplayer.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mag.musicplayer.Controller.Fragment.MusicListFragment;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.UiUtil;

public class MusicPlayerActivity extends AppCompatActivity {

    public static final String FRAGMENT_MUSIC_LIST = "fragment_music_list";

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicPlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        UiUtil.changeFragment(getSupportFragmentManager(), MusicListFragment.newInstance(),R.id.musicPlayerActivity_listFrame,true, FRAGMENT_MUSIC_LIST);

    }

}
