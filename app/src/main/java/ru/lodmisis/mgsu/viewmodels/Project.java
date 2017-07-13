package ru.lodmisis.mgsu.viewmodels;

/**
 * Created by romanismagilov on 12.07.17.
 */


import android.content.Context;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import butterknife.OnItemClick;
import ru.lodmisis.mgsu.R;

@Animate(Animation.SCALE_UP_ASC)
@Layout(R.layout.item_project)
public class Project{// extends BasePlaceHolder {

//    @Ignore
    transient public Context mContext;

//    @Ignore
    transient public PlaceHolderView mPlaceHolderView;


    public Project(){}

    public Object content;
    public String id;

    @View(R.id.tv_title)
    transient public TextView tvName;

    public String name;

    public String direction;
    public Double need;
    public Double given;
    public String shortDescription;

    public ImageModel img;

    @Expose
    @SerializedName("public")
    public Boolean _public;

    public String creatingDate;

    @Resolve
    private void onResolved() {
        tvName.setText(name);
        // Glide.with(mContext).load(mUrl).into(imageView);
    }

    @OnItemClick
    public void onItemClick() {
    }
}