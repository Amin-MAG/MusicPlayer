package com.mag.musicplayer.builder;

import android.content.Context;
import android.content.Intent;

import com.mag.musicplayer.services.MusicPlayerService;

public class MyIntentBuilder {

    private static final String KEY_COMMAND = "KEY_COMMAND";
    private static final String KEY_MESSAGE = "KEY_MESSAGE";


    private static MyIntentBuilder instance;

    public enum Command {
        INVALID(0),
        START(1),
        STOP(2);

        private int value;

        Command(int i) {
            this.value = i;
        }

    }

    private String mMessage;
    private Context mContext;
    private Command mCommandId;

    public static MyIntentBuilder getInstance(Context context) {
        return new MyIntentBuilder(context);
    }

    public MyIntentBuilder(Context context) {
        this.mContext = context;
    }

    public MyIntentBuilder setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public MyIntentBuilder setCommand(Command command) {
        this.mCommandId = command;
        return this;
    }

    public Intent build() {
        Intent intent = new Intent(mContext, MusicPlayerService.class);
        if (mCommandId != Command.INVALID) {
            intent.putExtra(KEY_COMMAND, mCommandId);
        }
        if (mMessage != null) {
            intent.putExtra(KEY_MESSAGE, mMessage);
        }
        return intent;
    }


    public Context getmContext() {
        return mContext;
    }

}