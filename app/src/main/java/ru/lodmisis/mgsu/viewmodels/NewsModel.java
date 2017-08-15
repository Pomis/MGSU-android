package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;

import es.dmoral.toasty.Toasty;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.SwipeableActivity;
import ru.lodmisis.mgsu.fragments.PostFragment;
import ru.lodmisis.mgsu.utils.DatetimeUtil;

/**
 * Created by romanismagilov on 18.07.17.
 */

@Animate(Animation.SCALE_UP_ASC)
@Layout(R.layout.item_news)
public class NewsModel implements Serializable, Emptyable {
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
//    public String direction;
    public String description;
    public ImageModel img;
    public Date creatingDate;

    @Resolve
    private void onResolved() {
        if (isEmptyPlaceholder) {
            tvName.setText("Увы, список пуст");
            tvDescr.setText("Нажмите, чтобы загрузить заново");
            ivPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.empty));
            ivPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            if (title != null) tvName.setText(title);
            if (description != null) tvDescr.setText(concateDescription());
            else ivPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_about));
            if (img != null) Glide.with(mContext).load(img.getOriginal()).into(ivPic);

        }
    }

    private String concateDescription() {
        return creatingDate.getDate() + " "
                + DatetimeUtil.getRuMonth(creatingDate) + " • "
                + description;
    }

    @Click(R.id.cv_project)
    public void onItemClick() {
        if (isEmptyPlaceholder) {
            callback.run();
        } else {
//            Toasty.info(mContext, "Экран не создан").show();
            SwipeableActivity.start(mContext, PostFragment.class, this);
            Log.d("kek", "clicket");
        }

    }

    public NewsModel() {
    }

    public NewsModel(Context mContext, Runnable callback) {
        this.callback = callback;
        this.isEmptyPlaceholder = true;
        this.mContext = mContext;
    }
}
