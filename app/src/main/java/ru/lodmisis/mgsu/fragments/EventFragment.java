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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import at.markushi.ui.RevealColorView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.InjectionFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends InjectionFragment {

    @BindView(R.id.iv_row_project)
    ImageView ivRowProject;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_row_descr)
    TextView tvRowDescr;
    @BindView(R.id.tv_row_date)
    TextView tvRowDate;
    @BindView(R.id.tv_row_month)
    TextView tvRowMonth;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.rcv)
    RevealColorView rcvReveal;
    @BindView(R.id.v_event)
    View vEvent;
    @BindView(R.id.tv_finish_date)
    TextView tvFinishDate;
    @BindView(R.id.tv_finish_month)
    TextView tvFinishMonth;
    @BindView(R.id.tv_finish_time)
    TextView tvFinishTime;
    @BindView(R.id.iv_attend)
    ImageView ivAttend;
    @BindView(R.id.cv_project)
    CardView cvProject;

    Unbinder unbinder;

    private EventModel eventModel;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        readModel();
    }

    private void readModel() {
        tvTitle.setText("Название мероприятия");
        Glide.with(getContext()).load("http://funzoo.ru/uploads/posts/2008-08/1220177654_slon1.jpg")
                .into(ivRowProject);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private View selectedView;
    private int backgroundColor;

    @OnClick(R.id.iv_attend)
    void attend() {
        final int color = getResources().getColor(R.color.colorAccent);
        final Point p = getLocationInView(cvProject, ivAttend);

        if (selectedView == ivAttend) {
            rcvReveal.hide(p.x, p.y, backgroundColor, 0, 300, null);
            selectedView = null;
        } else {
            rcvReveal.reveal(p.x, p.y, color, ivAttend.getHeight() / 2, 340, null);
            selectedView = ivAttend;
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
