package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.mindorks.placeholderview.PlaceHolderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.InjectionFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;
import ru.lodmisis.mgsu.viewmodels.TimeLineStartModel;
import ru.lodmisis.mgsu.viewmodels.TimelineEndModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends InjectionFragment {

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
        setListeners();
    }

    private void updateTitle() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
        getActivity().setTitle("Cобытия за " + dateFormat.format(new Date()));
    }

    private void setListeners() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                for (int i = eventModels.size() - 1; i >= 0; i--) {
                    boolean dateSelected = eventModels.get(i).date
                            .getDate() == dateClicked.getDate();
                    eventModels.get(i).selected = dateSelected;
                    if (dateSelected) {
                        phvEvents.refresh();
                        phvEvents.smoothScrollToPosition(i);
                    }
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
    }

    private void addTestEvents() {
        eventModels = new ArrayList<>();
        Date today = new Date();

        EventModel kek = new EventModel();
        kek.date
                = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        kek.isInternal = true;
        kek.mPlaceHolderView = phvEvents;
        kek.mContext = getContext();
        kek.group(null);
        eventModels.add(kek);


        EventModel kek3 = new EventModel();
        kek3.date = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        kek3.isInternal = true;
        kek3.mPlaceHolderView = phvEvents;
        kek3.mContext = getContext();
        kek3.group(kek);
        eventModels.add(kek3);


        EventModel ke1k = new EventModel();
        ke1k.date
                = new Date(today.getTime() + (1000 * 60 * 60 * 125));
        ke1k.isInternal = false;
        ke1k.mPlaceHolderView = phvEvents;
        ke1k.mContext = getContext();
        ke1k.group(kek3);
        eventModels.add(ke1k);

        EventModel ke2k = new EventModel();
        ke2k.date
                = new Date(today.getTime() + (1000 * 60 * 60 * 149));
        ke2k.isInternal = false;
        ke2k.mPlaceHolderView = phvEvents;
        ke2k.mContext = getContext();
        ke2k.group(ke1k);
        eventModels.add(ke2k);

        EventModel ke4k = new EventModel();
        ke4k.date
                = new Date(today.getTime() + (1000 * 60 * 60 * 149));
        ke4k.isInternal = false;
        ke4k.mPlaceHolderView = phvEvents;
        ke4k.mContext = getContext();
        ke4k.group(ke2k);
        eventModels.add(ke4k);

        EventModel ke7k = new EventModel();
        ke7k.date
                = new Date(today.getTime() + (1000 * 60 * 60 * 189));
        ke7k.isInternal = false;
        ke7k.mPlaceHolderView = phvEvents;
        ke7k.mContext = getContext();
        ke7k.group(ke4k);
        eventModels.add(ke7k);

        for (EventModel eventModel : eventModels) {
            compactCalendarView.addEvent(eventModel.toCalendarEvent());
        }

    }

    private void drawLists() {
        phvEvents.addView(new TimeLineStartModel());
        for (EventModel eventModel : eventModels) {
            phvEvents.addView(eventModel);
        }
        phvEvents.addView(new TimelineEndModel());
    }
}
