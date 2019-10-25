package com.mag.musicplayer.Util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.Var.Constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    public static final String CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART = "content://media/external/audio/albumart";
    private static MusicPlayer instance;
    private MusicPlayerCallback callback;

    private MusicPlayer() {
        this.mediaPlayer = new MediaPlayer();
    }

    public static MusicPlayer getInstance() {
        if (instance == null)
            instance = new MusicPlayer();
        return instance;
    }

    private MediaPlayer mediaPlayer;
    private Track currentTrack;

    public void loadMusics(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(Constants.externalMusicUri, null, null, null, null);
        List<Track> tracks = new ArrayList<>();

        while (cursor.moveToNext()) {

            String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String trackTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String trackLength = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            // Music Cover Uri
            Uri uri = ContentUris.withAppendedId(Uri.parse(CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART), Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

            tracks.add(new Track(Long.parseLong(trackId), trackTitle, trackAlbum, trackArtist, uri, Integer.parseInt(trackLength), null));

        }

        cursor.close();

        MusicRepository.getInstance().setTracks(tracks);

    }

    public static InputStream getInputStreamOfImage(ContentResolver contentResolver, Uri uri) {
        ContentResolver res = contentResolver;
        InputStream in = null;
        try {
            in = res.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }

    public void playMusic(Track track, final Context context) throws IOException {

        mediaPlayer.stop();

        currentTrack = track;

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentTrack.getTrackId());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Track nextTrack = callback.getNextTrack();
                try {
                    playMusic(nextTrack, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.updateUiAutoSkip();
            }
        });
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void setCallback(MusicPlayerCallback callback) {
        this.callback = callback;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public static String getLengthText(int seconds) {
        int minutes = seconds / 60;
        int secondReminder = seconds % 60;
        return (minutes < 10 ? "0" + minutes : minutes) + ":" + (secondReminder < 10 ? "0" + secondReminder : secondReminder);
    }

    public interface MusicPlayerCallback {
        Track getNextTrack();

        void updateUiAutoSkip();
    }

}
