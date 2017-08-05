package ru.lodmisis.mgsu;

import net.yslibrary.simplepreferences.annotation.Key;
import net.yslibrary.simplepreferences.annotation.Preferences;

/**
 * Created by romanismagilov on 07.07.17.
 */

@Preferences
public class Settings {

    private static final String DEV_URL = "http://85.143.104.47:4500";
    private static final String PRODUCTION_URL = "http://185.189.13.148:5000";

    public static final String BASE_URL = DEV_URL;


    @Key(omitGetterPrefix = true)
    protected boolean isLogged = false;
}
