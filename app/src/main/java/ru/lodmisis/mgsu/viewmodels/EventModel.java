package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import ru.lodmisis.mgsu.R;

@Layout(R.layout.item_event)
@Animate(Animation.SCALE_UP_ASC)
public class EventModel implements Serializable {

    @PrimaryKey
    public String id;

    public String content;

    public int quota;           // Общее количество мест

    public int reserved;        // Количство занятых мест

    public String title;

    public boolean isInternal;  // Внешнее событие = false, внутренее = true

    public ImageModel img;

    public Date startDate;      // Начало мероприятия

    public Date finishDate;     // Окончание мероприятия (если оно продолжительное)


    @Ignore
    transient public Context mContext;

    @Ignore
    transient public PlaceHolderView mPlaceHolderView;

    transient private boolean grouped = false; // Группировать с предыдущим элементом

    @Ignore
    transient public boolean selected = false;// День события выбран

    @View(R.id.ll_row_event)
    LinearLayout llEvent;
    @View(R.id.tv_row_date)
    TextView tvDate;
    @View(R.id.tv_row_month)
    TextView tvMonth;
    @View(R.id.v_event)
    android.view.View vEvent;
    @View(R.id.cv_project)
    CardView cvProject;
    @View(R.id.v_padding)
    android.view.View vPadding;


    public Event toCalendarEvent() {
        return new Event(
                isInternal ?
                        mContext.getResources().getColor(R.color.colorAccent) :
                        mContext.getResources().getColor(R.color.colorInverse),
                startDate.getTime()
        );
    }

    public void group(@Nullable EventModel previousEvent) {
        if (previousEvent != null && startDate != null && previousEvent.startDate != null) {
            if (previousEvent.startDate.getDate() == startDate.getDate()) {
                grouped = true;
            }
        }
    }

    @Resolve
    void onResolved() {
        if (startDate!=null) {
            Locale russian = new Locale("ru");
            String[] newMonths = {
                    "января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
            dfs.setMonths(newMonths);
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
            SimpleDateFormat sdf = (SimpleDateFormat) df;
            sdf.setDateFormatSymbols(dfs);

            String date = sdf.format(startDate);
            String[] dateSplit = date.split(" ");
            tvDate.setText(dateSplit[0]);
            tvMonth.setText(dateSplit[1]);
        }


        if (grouped) {
            tvDate.setVisibility(android.view.View.INVISIBLE);
            tvMonth.setVisibility(android.view.View.INVISIBLE);
            vEvent.setVisibility(android.view.View.INVISIBLE);
            vPadding.setVisibility(android.view.View.GONE);
        } else {
            tvDate.setVisibility(android.view.View.VISIBLE);
            tvMonth.setVisibility(android.view.View.VISIBLE);
            vEvent.setVisibility(android.view.View.VISIBLE);
            vPadding.setVisibility(android.view.View.VISIBLE);
        }

        resolveSelected();
    }

    private void resolveSelected() {
        if (selected) {
            if (!grouped)
                llEvent.setBackground(mContext.getResources().getDrawable(R.drawable.background_date_selected));
            else
                llEvent.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

        } else {
            llEvent.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
    }

}
