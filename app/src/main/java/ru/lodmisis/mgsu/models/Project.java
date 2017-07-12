package ru.lodmisis.mgsu.models;

/**
 * Created by romanismagilov on 12.07.17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    public String id;
    public String name;
    public String direction;
    public Double need;
    public Double given;
    public String shortDescription;
    public Object content;
    public ImageModel img;

    @Expose
    @SerializedName("public")
    public Boolean _public;

    public String creatingDate;

}