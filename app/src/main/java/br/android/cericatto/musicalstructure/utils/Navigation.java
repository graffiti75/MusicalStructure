package br.android.cericatto.musicalstructure.utils;

import android.app.Activity;

import br.android.cericatto.musicalstructure.R;

/**
 * Navigation.java.
 *
 * @author Rodrigo Cericatto
 * @since September 22, 2016
 */
public class Navigation {

    //--------------------------------------------------
    // Enum
    //--------------------------------------------------

    public enum Animation {
        GO,
        BACK
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    public static void animate(Activity activity, Animation animation) {
        if (animation == Animation.GO) {
            activity.overridePendingTransition(R.anim.open_next, R.anim.close_previous);
        } else {
            activity.overridePendingTransition(R.anim.open_previous, R.anim.close_next);
            activity.finish();
        }
    }
}