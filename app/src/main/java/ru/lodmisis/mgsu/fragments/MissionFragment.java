package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.parallax.ScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.viewmodels.Enumeration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionFragment extends Fragment {

    @BindView(R.id.phv_aims)
    PlaceHolderView phvAims;

    @BindView(R.id.sv_background)
    ScrollView svBackground;

    @BindView(R.id.v_tabs_background)
    View vTabsBackground;

    public MissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phvAims.addView(new Enumeration("обеспечение комплексного развития университета;"));
        phvAims.addView(new Enumeration("поддержка молодых специалистов, ученых, преподавателей;"));
        phvAims.addView(new Enumeration("возрождение деятельности Ассоциации выпускников (АВ) МГСУ-МИСИ;"));
        phvAims.addView(new Enumeration("взаимодействие и сотрудничество с бизнес-сообществом и крупнейшими компаниями строительного комплекса;"));
        phvAims.addView(new Enumeration("подготовка и реализация различных масштабных проектов;"));
        phvAims.addView(new Enumeration("подготовка университета к главному событию – 100-летие НИУ МГСУ."));

        scroll();
    }

    private void scroll() {
        svBackground.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                float alpha = scrollY / 300f;
                Log.d("kek", "alpha"+alpha);
                vTabsBackground.setAlpha(alpha);
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {

            }
        });
    }

}
