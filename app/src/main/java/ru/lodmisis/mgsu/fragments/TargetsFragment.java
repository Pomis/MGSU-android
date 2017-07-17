package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.viewmodels.Enumeration;

/**
 * A simple {@link Fragment} subclass.
 */
public class TargetsFragment extends Fragment {


    @BindView(R.id.phv_keys)
    PlaceHolderView phvAims;

    public TargetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_targets, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phvAims.addView(new Enumeration("поддержка студентов (образование, помощь в трудоустройстве, спорт, культурно-массовые мероприятия);"));
        phvAims.addView(new Enumeration("поддержка профессорско-преподавательского состава;"));
        phvAims.addView(new Enumeration("поддержка научно-исследовательской деятельности;"));
        phvAims.addView(new Enumeration("развитие инфраструктуры."));
    }
}
