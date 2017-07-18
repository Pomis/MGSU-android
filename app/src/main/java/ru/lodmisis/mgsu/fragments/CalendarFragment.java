package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.BaseFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {

    ArrayList<EventModel> eventModels;

    @BindView(R.id.ccv_calendar)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.phv_events)
    PlaceHolderView phvEvents;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTitle();
        addTestEvents();
        drawLists();
    }

    private void updateTitle() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "LLLL YYYY", Locale.getDefault() );
        getActivity().setTitle("Cобытия за "+dateFormat.format(new Date()));

    }

    private void addTestEvents() {
        eventModels = new ArrayList<>();
        Date today = new Date();

        EventModel kek = new EventModel();
        kek.startDate = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        kek.isInternal = true;
        kek.mPlaceHolderView = phvEvents;
        kek.mContext = getContext();
        eventModels.add(kek);
        eventModels.add(kek);

        EventModel ke1k = new EventModel();
        ke1k.startDate = new Date(today.getTime() + (1000 * 60 * 60 * 125));
        ke1k.isInternal = false;
        ke1k.mPlaceHolderView = phvEvents;
        ke1k.mContext = getContext();

        eventModels.add(ke1k);

        for (EventModel eventModel : eventModels) {
            compactCalendarView.addEvent(eventModel.toCalendarEvent());
        }
    }

    private void drawLists() {
        eventModels.forEach(eventModel -> {
            phvEvents.addView(eventModel);
        });
    }
}
