package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public void onResume() {
        super.onResume();
        phvAims.addView(new Enumeration("обеспечение комплексного развития университета;"));
        phvAims.addView(new Enumeration("поддержка молодых специалистов, ученых, преподавателей;"));
        phvAims.addView(new Enumeration("возрождение деятельности Ассоциации выпускников (АВ) МГСУ-МИСИ;"));
        phvAims.addView(new Enumeration("взаимодействие и сотрудничество с бизнес-сообществом и крупнейшими компаниями строительного комплекса;"));
        phvAims.addView(new Enumeration("подготовка и реализация различных масштабных проектов;"));
        phvAims.addView(new Enumeration("подготовка университета к главному событию – 100-летие НИУ МГСУ."));
    }

}
