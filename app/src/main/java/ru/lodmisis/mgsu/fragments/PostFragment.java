package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.InjectionFragment;
import ru.lodmisis.mgsu.utils.DatetimeUtil;
import ru.lodmisis.mgsu.viewmodels.NewsModel;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Просмотр новости или поста
 */
public class PostFragment extends InjectionFragment {


    @BindView(R.id.iv_post_photo)
    ImageView ivPostPhoto;

    @BindView(R.id.tv_post_date)
    TextView tvPostDate;

    @BindView(R.id.tv_post_month)
    TextView tvPostMonth;

    @BindView(R.id.tv_post_title)
    TextView tvPostTitle;

    @BindView(R.id.tv_post_description)
    TextView tvPostDescription;

    @BindView(R.id.vw_post_content)
    WebView wvPostContent;

    Unbinder unbinder;
    private NewsModel model;

    public PostFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resolve();
    }

    public PostFragment setModel(NewsModel model) {
        this.model = model;
        return this;
    }

    private void resolve() {
        tvPostTitle.setText(model.title);
        tvPostDescription.setText(model.description);
        if (model.creatingDate != null) {
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            tvPostDate.setText("" + model.creatingDate.getDate());
            tvPostMonth.setText(DatetimeUtil.getRuMonth(model.creatingDate));
            if (model.content != null)
                wvPostContent.loadData(model.content, "text/html; charset=utf-8", "utf-8");
        }
//        if (eventModel.finishDate != null) {
//            llFinishDatetime.setVisibility(View.VISIBLE);
//            tvFinishTime.setText(eventModel.finishDate.getHours() + ":"
//                    + eventModel.finishDate.getMinutes());
//            tvFinishDate.setText(eventModel.finishDate.getDate());
//            tvFinishMonth.setText(DatetimeUtil.getRuMonth(eventModel.finishDate));
//        }



        if (model.img != null)
            Glide.with(getContext())
                    .load(model.img.getOriginal())
                    .apply(centerCropTransform().placeholder(R.drawable.background_slonbox))
                    .into(ivPostPhoto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
