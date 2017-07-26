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
    transient boolean isEmptyPlaceholder = false;
    transient Runnable callback;
    @View(R.id.tv_title)
    transient TextView tvName;

    @View(R.id.iv_row_project)
    transient ImageView ivPic;

    @View(R.id.tv_row_descr)
    transient TextView tvDescr;



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
        if (isEmptyPlaceholder) {
            tvName.setText("Увы, список пуст");
            tvDescr.setText("Нажмите, чтобы загрузить заново");
            ivPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.empty));
            ivPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            tvName.setText(title);
            Glide.with(mContext).load(img.getOriginal()).into(ivPic);
        }
    }

    @Click(R.id.cv_project)
    public void onItemClick() {
        if (isEmptyPlaceholder) {
            callback.run();
        } else {
            SwipeableActivity.start(mContext, this);
            Log.d("kek", "clicket");
        }

    }

    public NewsModel(){}

    public NewsModel(Context mContext, Runnable callback) {
        this.callback = callback;
        this.isEmptyPlaceholder = true;
        this.mContext = mContext;
    }
}
