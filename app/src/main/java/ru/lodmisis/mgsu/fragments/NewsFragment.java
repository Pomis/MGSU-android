package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import ru.lodmisis.mgsu.viewmodels.NewsModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends InjectionFragment {

    @BindView(R.id.phv_projects)
    PlaceHolderView phvProjects;

    @BindView(R.id.skv_loading_indicator)
    SpinKitView skvIndicator;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPosts();
    }

    private void clearViews() {
        phvProjects.removeAllViews();
        skvIndicator.setVisibility(View.VISIBLE);
    }

    private void loadPosts() {
        clearViews();
        api.getPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .onErrorReturnItem(new NewsModel(getContext(), this::loadPosts))
                .switchIfEmpty(Observable.just(new NewsModel(getContext(), this::loadPosts)))
                .subscribe(item -> {

                    item.mContext = getContext();
                    item.mPlaceHolderView = phvProjects;
                    phvProjects.addView(item);
                    phvProjects.refresh();
                    skvIndicator.setVisibility(View.INVISIBLE);

                }, throwable -> {

                    ErrorHandler.handle(throwable, getContext());
                })

        ;

    }
}
