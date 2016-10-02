package br.android.cericatto.musicalstructure.model;

/**
 * Song.java.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class Song {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private long id;
    private String title;
    private String artist;
    private String album;
    private Integer duration;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public Song(long id, String title, String artist, String album, Integer duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    //--------------------------------------------------
    // To String
    //--------------------------------------------------

    @Override
    public String toString() {
        return "Song{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", artist='" + artist + '\'' +
            ", album='" + album + '\'' +
            ", duration=" + duration +
            '}';
    }

    //--------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}