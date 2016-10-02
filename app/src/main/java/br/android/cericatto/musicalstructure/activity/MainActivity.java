package br.android.cericatto.musicalstructure.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.android.cericatto.musicalstructure.R;
import br.android.cericatto.musicalstructure.adapter.SongAdapter;
import br.android.cericatto.musicalstructure.model.Song;
import br.android.cericatto.musicalstructure.utils.ActivityUtils;
import br.android.cericatto.musicalstructure.utils.PermissionUtils;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    /**
     * Permissions.
     */

    private static final String[] PERMISSIONS = { Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int PERMISSION_REQUEST = 10;

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Contexts.
     */

    private Activity mActivity = MainActivity.this;

    /**
     * Song Adapter.
     */

    private List<Song> mSongList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    /**
     * Permissions.
     */

    private boolean mHasPermission = false;

    /**
     * Layout.
     */

    private TextView mCurrentMusicTextView;
    private Button mButton;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(false);
        setLayout();
        checkPermissions();
    }

    //--------------------------------------------------
    // Permission Methods
    //--------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    permissionGrantedAction();
                    if (!mHasPermission) {
                        setResult(RESULT_OK);
                        finish();
                    }
                } else {
                    // Permission denied.
                    PermissionUtils.alertAndFinish(mActivity);
                }
                return;
            }
        }
    }

    private void checkPermissions() {
        // Checks the Android version of the device.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean canReadExternalStorage = PermissionUtils.canReadExternalStorage(mActivity);
            if (!canReadExternalStorage) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST);
            } else {
                // Permission was granted.
                permissionGrantedAction();
            }
        } else {
            // Version is below Marshmallow.
            permissionGrantedAction();
        }
    }

    private void permissionGrantedAction() {
        mHasPermission = true;
        setRecyclerView();
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setLayout() {
        mCurrentMusicTextView = (TextView)findViewById(R.id.id_activity_main__current_music_text_view);

        mButton = (Button)findViewById(R.id.id_activity_main__button);
        mButton.setOnClickListener(this);
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.id_activity_main__recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getSongList();
        SongAdapter adapter = new SongAdapter(this, mSongList);
        mRecyclerView.setAdapter(adapter);
    }

    private void getSongList() {
        // Gets cursor.
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        // Sets list.
        if (musicCursor != null && musicCursor.moveToFirst()) {
            // Gets columns.
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int durationColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            // Adds songs to list.
            do {
                long id = musicCursor.getLong(idColumn);
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistColumn);
                String album = musicCursor.getString(albumColumn);
                int duration = musicCursor.getInt(durationColumn);
                mSongList.add(new Song(id, title, artist, album, duration));
            }
            while (musicCursor.moveToNext());
        }

        // Return.
        orderMusic();
    }

    private void orderMusic() {
        Collections.sort(mSongList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }

    //--------------------------------------------------
    // Callback Methods
    //--------------------------------------------------

    public void updateCurrentMusic(Song item) {
        String text = item.getTitle() + " - " + item.getArtist();
        mCurrentMusicTextView.setText(text);
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in);
        mButton.startAnimation(animation);

        String currentMusic = mCurrentMusicTextView.getText().toString();
        if (currentMusic.equals(getString(R.string.activity_main__no_music_selected))) {
            Toast.makeText(mActivity, R.string.activity_main__please_select_a_song, Toast.LENGTH_LONG).show();
        } else {
            ActivityUtils.startActivity(mActivity, DetailsActivity.class);
        }
    }
}