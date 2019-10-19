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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private static MusicPlayer instance;

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

        while (cursor.moveToNext())
            addCursorToTracks(cursor, tracks);

        cursor.close();

        MusicRepository.getInstance().setTracks(tracks);

    }

    private void addCursorToTracks(Cursor cursor, List<Track> tracks) {
        String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        String trackTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String trackLength = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

        tracks.add(new Track(Long.parseLong(trackId), trackTitle, trackAlbum, trackArtist, null, Integer.parseInt(trackLength), null));
    }

    public void playMusic(Track track, Context context) throws IOException {

        mediaPlayer.stop();

        currentTrack = track;

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentTrack.getTrackId());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

}
