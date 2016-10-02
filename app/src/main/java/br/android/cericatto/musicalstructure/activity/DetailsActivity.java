package br.android.cericatto.musicalstructure.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import br.android.cericatto.musicalstructure.R;
import br.android.cericatto.musicalstructure.model.Song;
import br.android.cericatto.musicalstructure.utils.ContentManager;
import br.android.cericatto.musicalstructure.utils.Navigation;

/**
 * DetailsActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class DetailsActivity extends AppCompatActivity implements
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
    View.OnClickListener {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Context.
     */

    private Activity mActivity = DetailsActivity.this;

    /**
     * Song.
     */

    private Song mCurrentSong;
    private MediaPlayer mMediaPlayer;

    /**
     * Layout.
     */

    private Button mBackToLibraryButton;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initToolbar(true);
        setLayout();
        setMediaPlayer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Navigation.animate(this, Navigation.Animation.BACK);
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.stop();
        super.onDestroy();
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Integer id = menuItem.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            getSupportActionBar().setTitle(R.string.activity_details__parent_title);
        }
    }

    private void setLayout() {
        mCurrentSong = ContentManager.getInstance().getSong();

        TextView artistTextView = (TextView)findViewById(R.id.id_activity_details__artist_text_view);
        artistTextView.setText(mCurrentSong.getArtist());

        TextView albumTextView = (TextView)findViewById(R.id.id_activity_details__album_text_view);
        albumTextView.setText(mCurrentSong.getAlbum());

        TextView titleTextView = (TextView)findViewById(R.id.id_activity_details__title_text_view);
        titleTextView.setText(mCurrentSong.getTitle());

        TextView durationTextView = (TextView)findViewById(R.id.id_activity_details__duration_text_view);
        int totalSeconds = mCurrentSong.getDuration() / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String duration = minutes + ":" + seconds;
        durationTextView.setText(duration);

        mBackToLibraryButton = (Button)findViewById(R.id.id_activity_details__back_to_library_button);
        mBackToLibraryButton.setOnClickListener(this);
    }

    private void setMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
//        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

        long id = mCurrentSong.getId();
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), trackUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    //--------------------------------------------------
    // Listeners
    //--------------------------------------------------

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.reset();
    }

    @Override
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in);
        mBackToLibraryButton.startAnimation(animation);

        onBackPressed();
    }
}