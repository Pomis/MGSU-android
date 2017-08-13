package ru.lodmisis.mgsu.fragments;


import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jakewharton.rxbinding2.view.RxView;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import at.markushi.ui.RevealColorView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.http.Path;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.api.ErrorHandler;
import ru.lodmisis.mgsu.base.InjectionFragment;
import ru.lodmisis.mgsu.utils.DatetimeUtil;
import ru.lodmisis.mgsu.viewmodels.EventModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends InjectionFragment {
    enum AttendTypes {
        NOT_GOING, NOT_SURE, GOING
    }

    @BindView(R.id.iv_project)
    ImageView ivProject;
    @BindView(R.id.rcv)
    RevealColorView rcv;
    @BindView(R.id.iv_attend)
    ImageView ivAttend;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_descr)
    TextView tvDescr;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_start_month)
    TextView tvStartMonth;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_finish_date)
    TextView tvFinishDate;
    @BindView(R.id.tv_finish_month)
    TextView tvFinishMonth;
    @BindView(R.id.tv_finish_time)
    TextView tvFinishTime;
    @BindView(R.id.ll_start_datetime)
    LinearLayout llStartDatetime;
    @BindView(R.id.ll_finish_datetime)
    LinearLayout llFinishDatetime;
    @BindView(R.id.cv_project)
    CardView cvProject;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.skv_loading_indicator)
    SpinKitView skvLoadingIndicator;

    private EventModel eventModel;

    private View selectedView;
    private int backgroundColor;

    Unbinder unbinder;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public Fragment setModel(@Nullable EventModel eventModel) {
        this.eventModel = eventModel;
        return this;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (eventModel != null) resolve();
        initAttend();
    }

    private void resolve() {
        tvTitle.setText(eventModel.title);
        tvDescr.setText(eventModel.userAttends ? "Вы зарегистрированы на событие" :
                "Вы не зарегистрированы на событие");
        if (eventModel.date != null) {
            llStartDatetime.setVisibility(View.VISIBLE);
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            tvStartTime.setText(localDateFormat.format(eventModel.date));
            tvStartDate.setText("" + eventModel.date.getDate());
            tvStartMonth.setText(DatetimeUtil.getRuMonth(eventModel.date));
            tvContent.setText(eventModel.content);
        }
//        if (eventModel.finishDate != null) {
//            llFinishDatetime.setVisibility(View.VISIBLE);
//            tvFinishTime.setText(eventModel.finishDate.getHours() + ":"
//                    + eventModel.finishDate.getMinutes());
//            tvFinishDate.setText(eventModel.finishDate.getDate());
//            tvFinishMonth.setText(DatetimeUtil.getRuMonth(eventModel.finishDate));
//        }


        if (eventModel.img != null)
            Glide.with(getContext()).load(eventModel.img.getOriginal()).into(ivProject);
    }

    private void initAttend() {
        RxView.clicks(ivAttend)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> startAttending());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    void startAttending() {
        ivAttend.setVisibility(View.GONE);
        skvLoadingIndicator.setVisibility(View.VISIBLE);
        async(api.attend(eventModel.id, 0))
                .subscribe(s -> attend(true), throwable -> {
                    attend(false);
                    ErrorHandler.handle(throwable, getContext());
                });
    }

    void attend(boolean success) {
        try {
            if (success) {
                skvLoadingIndicator.setVisibility(View.GONE);

                final int color = getResources().getColor(R.color.colorAccent);
                final Point p = getLocationInView(cvProject, ivAttend);

                if (selectedView == ivAttend) {
                    rcv.hide(p.x, p.y, backgroundColor, 0, 300, null);
                    selectedView = null;
                } else {
                    rcv.reveal(p.x, p.y, color, ivAttend.getHeight() / 2, 340, null);
                    selectedView = ivAttend;
                }
            } else {
                skvLoadingIndicator.setVisibility(View.GONE);
                ivAttend.setVisibility(View.VISIBLE);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;
        return new Point(l1[0], l1[1]);
    }

    private int getColor(View view) {
        return Color.parseColor((String) view.getTag());
    }
}
