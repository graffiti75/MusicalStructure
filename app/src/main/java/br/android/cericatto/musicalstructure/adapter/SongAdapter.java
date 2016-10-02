package br.android.cericatto.musicalstructure.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.android.cericatto.musicalstructure.R;
import br.android.cericatto.musicalstructure.activity.MainActivity;
import br.android.cericatto.musicalstructure.model.Song;
import br.android.cericatto.musicalstructure.utils.ContentManager;

/**
 * SongAdapter.java.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private Activity mActivity;
    private List<Song> mItems;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public SongAdapter(Activity context, List<Song> items) {
        mActivity = context;
        mItems = items;
    }

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Song item = mItems.get(position);
        String title = item.getTitle();
        String artist = item.getArtist();

        holder.titleTextView.setText(title);
        holder.titleTextView.setOnClickListener(onClickListener(item));
        holder.artistTextView.setText(artist);
        holder.artistTextView.setOnClickListener(onClickListener(item));
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private View.OnClickListener onClickListener(final Song item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentManager.getInstance().setSong(item);
                MainActivity activity = (MainActivity)mActivity;
                activity.updateCurrentMusic(item);
            }
        };
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView artistTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.id_adapter_song__title_text_view);
            artistTextView = (TextView)view.findViewById(R.id.id_adapter_song__artist_text_view);
        }
    }
}