package ru.lodmisis.mgsu.viewmodels;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import io.realm.annotations.PrimaryKey;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.SwipeableActivity;
import ru.lodmisis.mgsu.fragments.EventFragment;
import ru.lodmisis.mgsu.utils.DatetimeUtil;

import static ru.lodmisis.mgsu.utils.DatetimeUtil.getRuMonth;

@Layout(R.layout.item_event)
@Animate(Animation.SCALE_UP_ASC)
public class EventModel implements Serializable, Emptyable {

    @PrimaryKey
    public String id;

    public String content;

//    public int quota;           // Общее количество мест
//
//    public int reserved;        // Количство занятых мест

    public boolean userAttends; // Пользователь зареган на событие

    public String title;

    public boolean isInternal;  // Внешнее событие = false, внутренее = true

    public ImageModel img;

    public Date date;           // Просто дата

//    public Date date;      // Начало мероприятия
//
//    public Date finishDate;     // Окончание мероприятия (если оно продолжительное)


    transient public Context mContext;
    transient public PlaceHolderView mPlaceHolderView;
    transient boolean isEmptyPlaceholder = false;
    transient Runnable callback;
    transient private boolean grouped = false; // Группировать с предыдущим элементом
    transient public boolean selected = false;// День события выбран

    @View(R.id.ll_row_event)
    transient LinearLayout llEvent;

    @View(R.id.tv_row_title)
    transient TextView tvTitle;

    @View(R.id.tv_row_date)
    transient TextView tvDate;

    @View(R.id.tv_row_month)
    transient TextView tvMonth;

    @View(R.id.v_event)
    transient android.view.View vEvent;

    @View(R.id.cv_project)
    transient CardView cvProject;

    @View(R.id.v_padding)
    transient android.view.View vPadding;


    static public EventModel getEmptyPlaceholder(Context mContext, Runnable callback) {
        EventModel eventModel = new EventModel();
        eventModel.mContext = mContext;
        eventModel.callback = callback;
        eventModel.isEmptyPlaceholder = true;
        return eventModel;
    }

    public Event toCalendarEvent() {
        return new Event(
                isInternal ?
                        mContext.getResources().getColor(R.color.colorAccent) :
                        mContext.getResources().getColor(R.color.colorInverse),
                date.getTime()
        );
    }

    public void group(@Nullable EventModel previousEvent) {
        if (previousEvent != null && date != null && previousEvent.date != null) {
            if (previousEvent.date.getDate() == date.getDate()) {
                grouped = true;
            }
        }
    }

    @Resolve
    void onResolved() {
        if (isEmptyPlaceholder) {
            tvDate.setText("404");
//            tvTitle.setText("Ничего не найдено.");
        } else if (date != null) {

            tvDate.setText(""+date.getDate());
            tvMonth.setText(DatetimeUtil.getRuMonth(date));
            tvTitle.setText(title != null ? title : "Без названия");
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

    @Click(R.id.cv_project)
    void onClick() {
        SwipeableActivity.start(mContext, EventFragment.class, this);

//        if (isEmptyPlaceholder) {
//            callback.run();
//        } else {
//            SwipeableActivity.start(mContext, EventFragment.class, null);
//        }
    }

}
