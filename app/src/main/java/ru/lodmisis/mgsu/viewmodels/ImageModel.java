package ru.lodmisis.mgsu.viewmodels;

import java.io.Serializable;

import io.realm.RealmObject;
import ru.lodmisis.mgsu.Settings;

public class ImageModel extends RealmObject implements Serializable{

    private String small = "";

    private String original = "";

    public String getSmall() {
        return Settings.BASE_URL+"/"+small;
    }

    public String getOriginal() {
        return Settings.BASE_URL+"/"+original;
    }
}
