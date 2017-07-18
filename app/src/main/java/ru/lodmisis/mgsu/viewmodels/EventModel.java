package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;
import android.graphics.Color;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import ru.lodmisis.mgsu.R;

@Layout(R.layout.item_event)
public class EventModel implements Serializable {
    @Ignore
    transient public Context mContext;

    @Ignore
    transient public PlaceHolderView mPlaceHolderView;

    @PrimaryKey
    public String id;

    public String content;

    public int quota;           // Общее количество мест

    public int reserved;        // Количство занятых мест

    public String title;

    public boolean isInternal;  // Внешнее событие = false, внутренее = true

    public ImageModel img;

    public Date startDate;    // Начало мероприятия

    public Date finishDate;   // Окончание мероприятия (если оно продолжительное)

    public Event toCalendarEvent() {
        return new Event(
                isInternal?
                        mContext.getResources().getColor(R.color.colorAccent):
                        mContext.getResources().getColor(R.color.colorInverse),
                startDate.getTime()
        );
    }

}
