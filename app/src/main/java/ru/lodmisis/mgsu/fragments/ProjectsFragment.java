package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ru.lodmisis.mgsu.base.ListLoadingFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;
import ru.lodmisis.mgsu.viewmodels.NewsModel;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;

/*
 * Проекты из описания
 */
public class ProjectsFragment extends InjectionFragment implements ListLoadingFragment {

    @BindView(R.id.phv_projects)
    PlaceHolderView phvProjects;
    @BindView(R.id.skv_loading_indicator)
    SpinKitView skvIndicator;

    public ProjectsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load();

    }


    private void clearViews() {
        phvProjects.removeAllViews();
        skvIndicator.setVisibility(View.VISIBLE);
    }

    public void load() {
        clearViews();
        async(api.getProjects())
                .onErrorReturnItem(ProjectModel.getEmptyPlaceholder(getContext(), this::load))
                .defaultIfEmpty(ProjectModel.getEmptyPlaceholder(getContext(), this::load))
                .doFinally(() -> {
                    phvProjects.refresh();
                    skvIndicator.setVisibility(View.INVISIBLE);
                })
                .subscribe(item -> {
                    item.mContext = getContext();
                    item.mPlaceHolderView = phvProjects;
                    phvProjects.addView(item);

                }, throwable -> {
                    ErrorHandler.handle(throwable, getContext());
                });
    }

}
