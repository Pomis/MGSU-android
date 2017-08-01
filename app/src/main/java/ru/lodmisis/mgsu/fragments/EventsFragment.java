package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.api.ErrorHandler;
import ru.lodmisis.mgsu.base.InjectionFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;
import ru.lodmisis.mgsu.viewmodels.TimeLineStartModel;
import ru.lodmisis.mgsu.viewmodels.TimelineEndModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends InjectionFragment {

    @BindView(R.id.phv_events)
    PlaceHolderView phvEvents;

    @BindView(R.id.skv_loading_indicator)
    SpinKitView skvIndicator;

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTimeline();
        load();

    }

    private void initTimeline() {

    }


    private void clearViews() {
        phvEvents.removeAllViews();
        skvIndicator.setVisibility(View.VISIBLE);
    }

    private void load() {
        clearViews();
        async(api.getUpcomingEvents())
                .onErrorReturnItem(EventModel.getEmptyPlaceholder(getContext(), this::load))
                .defaultIfEmpty(EventModel.getEmptyPlaceholder(getContext(), this::load))
                .doFinally(() -> {
                    phvEvents.addView(0, new TimeLineStartModel());
                    phvEvents.addView(new TimelineEndModel());
                    phvEvents.refresh();
                    skvIndicator.setVisibility(View.INVISIBLE);

                })
                .subscribe(item -> {
                    item.mContext = getContext();
                    item.mPlaceHolderView = phvEvents;
                    phvEvents.addView(item);

                }, throwable -> {

                    ErrorHandler.handle(throwable, getContext());
                });
    }
}
