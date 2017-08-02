package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;

/**
 * Created by romanismagilov on 03.08.17.
 */

public interface Emptyable {
    public static Emptyable getEmptyPlaceholder(Context context, Runnable callback) {
        return null;
    }
}
