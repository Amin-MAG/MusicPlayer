package com.mag.musicplayer.Model;

import java.util.Date;

public class Track {

    private long trackId;
    private String trackName;
    private String albumName;
    private String artistName;
    private String trackPath;
    private String imagePath;
    // length to seconds
    private int trackLength;
    private Date lastModified;
    private int trackPlayCount;


    public Track(long trackId, String trackName, String albumName, String artistName, String trackPath, String imagePath, int trackLength, Date lastModified, int trackPlayCount) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.trackPath = trackPath;
        this.imagePath = imagePath;
        this.trackLength = trackLength;
        this.lastModified = lastModified;
        this.trackPlayCount = trackPlayCount;
    }

    public Track(long trackId, String trackName, String albumName, String artistName, String trackPath, String imagePath, int trackLength, Date lastModified) {
        this(trackId, trackName, albumName, artistName, trackPath, imagePath, trackLength, lastModified, 0);
    }


    public long getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackPath() {
        return trackPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public int getTrackPlayCount() {
        return trackPlayCount;
    }

    public void addCount() {
        trackPlayCount++;
    }

}
