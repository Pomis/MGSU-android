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

import butterknife.OnItemClick;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.SwipeableActivity;
import ru.lodmisis.mgsu.fragments.ProjectFragment;

@Animate(Animation.SCALE_UP_ASC)
@Layout(R.layout.item_project)
public class ProjectModel implements Serializable, Emptyable {
    public ProjectModel() {
    }


    public String content;

    public String id;

    public String name;

    public String direction;

    public Double need;

    public Double given;

    public String shortDescription;

    public ImageModel img;

    public String creatingDate;

    transient public Context mContext;
    transient public PlaceHolderView mPlaceHolderView;
    transient boolean isEmptyPlaceholder;
    transient public Runnable callback;

    @View(R.id.tv_title)
    transient TextView tvName;
    @View(R.id.iv_row_project)
    transient ImageView ivPic;
    @View(R.id.tv_row_descr)
    transient TextView tvDescr;

    static public ProjectModel getEmptyPlaceholder(Context mContext, Runnable callback) {
        ProjectModel projectModel = new ProjectModel();
        projectModel.mContext = mContext;
        projectModel.callback = callback;
        projectModel.isEmptyPlaceholder = true;
        return projectModel;
    }

    @Resolve
    private void onResolved() {
        if (isEmptyPlaceholder) {
            tvName.setText("Увы, список пуст");
            tvDescr.setText("Нажмите, чтобы загрузить заново");
            ivPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.empty));
            ivPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            if (name != null) tvName.setText(name);
            if (img != null) Glide.with(mContext).load(img.getOriginal()).into(ivPic);
            else ivPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_about));
        }
    }

    @Click(R.id.cv_project)
    public void onItemClick() {
        if (isEmptyPlaceholder) {
            callback.run();
        } else {
            SwipeableActivity.start(mContext, ProjectFragment.class, this);
            Log.d("kek", "clicket");
        }

    }


}