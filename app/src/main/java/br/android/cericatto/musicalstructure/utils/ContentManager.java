package br.android.cericatto.musicalstructure.utils;

import br.android.cericatto.musicalstructure.model.Song;

/**
 * ContentManager.java class.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class ContentManager {

    //----------------------------------------------
    // Statics
    //----------------------------------------------

    private static ContentManager sInstance = null;

    //----------------------------------------------
    // Attributes
    //----------------------------------------------

    private Song mCurrentSong;

    //----------------------------------------------
    // Constructor
    //----------------------------------------------

    /**
     * Public constructor.
     */
    public ContentManager() {}

    /**
     * @return The singleton instance of ContentManager.
     */
    public static ContentManager getInstance() {
        if (sInstance == null) {
            sInstance = new ContentManager();
        }
        return sInstance;
    }

    //----------------------------------------------
    // Methods
    //----------------------------------------------

    public Song getSong() {
        return mCurrentSong;
    }

    public void setSong(Song current) {
        mCurrentSong = current;
    }
}