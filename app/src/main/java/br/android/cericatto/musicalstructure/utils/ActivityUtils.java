package br.android.cericatto.musicalstructure.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * ActivityUtils.java.
 *
 * @author Rodrigo Cericatto
 * @since Oct 1, 2016
 */
public class ActivityUtils {

    //--------------------------------------------------
    // Activity Methods
    //--------------------------------------------------

    public static void startActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        Navigation.animate(activity, Navigation.Animation.GO);
    }

    private static Bundle getExtra(Bundle extras, String key, Object value) {
        if (value instanceof String) {
            extras.putString(key, (String)value);
        } else if (value instanceof Integer) {
            extras.putInt(key, (Integer)value);
        } else if (value instanceof Long) {
            extras.putLong(key, (Long)value);
        } else if (value instanceof Boolean) {
            extras.putBoolean(key, (Boolean) value);
        }
        return extras;
    }
}