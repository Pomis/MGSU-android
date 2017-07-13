package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.BaseFragment;
import ru.lodmisis.mgsu.viewmodels.Project;
/*
 * Проекты из описания
 */
public class ProjectsFragment extends BaseFragment {

    @BindView(R.id.phv_projects)
    PlaceHolderView phvProjects;

    public ProjectsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProjects();
    }

    private void loadProjects() {
        api.getProjects().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .flatMap(Observable::fromIterable)
                .subscribe(item -> {

                    item.mContext = getContext();
                    item.mPlaceHolderView = phvProjects;
                    phvProjects.addView(item);
                    phvProjects.refresh();

                });
    }

}
