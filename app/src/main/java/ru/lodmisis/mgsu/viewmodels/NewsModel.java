package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.Serializable;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.SwipeableActivity;

/**
 * Created by romanismagilov on 18.07.17.
 */

@Animate(Animation.SCALE_UP_ASC)
@Layout(R.layout.item_news)
public class NewsModel implements Serializable{
    transient public Context mContext;
    transient public PlaceHolderView mPlaceHolderView;

    @View(R.id.tv_title)
    transient TextView tvName;

    @View(R.id.iv_row_project)
    transient ImageView ivPic;



    public String content;
    public String id;
    public String title;
    public String direction;
    public String shortDescription;

    public ImageModel img;

    @Expose
    @SerializedName("public")
    public Boolean _public;

    public String creatingDate;

    @Resolve
    private void onResolved() {
        tvName.setText(title);
        Glide.with(mContext).load(img.getOriginal()).into(ivPic);
    }

    @Click(R.id.cv_project)
    public void onItemClick() {
        SwipeableActivity.start(mContext, this);
        Log.d("kek", "clicket");
    }

    public NewsModel(){}

}
