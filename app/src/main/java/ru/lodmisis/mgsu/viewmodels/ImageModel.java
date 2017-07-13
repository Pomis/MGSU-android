package ru.lodmisis.mgsu.viewmodels;

import ru.lodmisis.mgsu.Settings;

public class ImageModel {

    private String small = "";

    private String original = "";

    public String getSmall() {
        return Settings.BASE_URL+"/"+small;
    }

    public String getOriginal() {
        return Settings.BASE_URL+"/"+original;
    }
}
