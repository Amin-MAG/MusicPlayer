package com.mag.musicplayer.Controller.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.UiUtil;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    public abstract String getTagName();

    public Intent newIntent(Context context) {
        return new Intent(context, SingleFragmentActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.singleFragment_mainFrame);

        if (fragment == null)
            UiUtil.changeFragment(fragmentManager, getFragment(), R.id.singleFragment_mainFrame, false, getTagName());

    }


}

