package ru.lodmisis.mgsu.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import ru.lodmisis.mgsu.SettingsPrefs;
import ru.lodmisis.mgsu.api.Endpoints;
import ru.lodmisis.mgsu.viewmodels.Emptyable;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;

import static ru.lodmisis.mgsu.App.getEndowmentService;
import static ru.lodmisis.mgsu.App.getPrefsInstance;
import static ru.lodmisis.mgsu.App.getRealmInstance;


public class InjectionFragment extends android.support.v4.app.Fragment {

    protected Realm realm;
    protected Endpoints api;
    protected SettingsPrefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = getRealmInstance(getContext());
        api = getEndowmentService(getContext());
        prefs = getPrefsInstance(getContext());
    }

    protected <T extends Emptyable> Observable<T> async(Observable<List<T>> observable) {
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable);
    }

    protected <T> Single<T> async(Single<T> single) {
        return single
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}