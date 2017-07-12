package ru.lodmisis.mgsu.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.realm.Realm;
import ru.lodmisis.mgsu.api.Endpoints;

import static ru.lodmisis.mgsu.App.getEndowmentService;
import static ru.lodmisis.mgsu.App.getRealmInstance;


public class BaseFragment extends android.support.v4.app.Fragment {

    protected Realm realm;
    protected Endpoints api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = getRealmInstance(getContext());
        api = getEndowmentService(getContext());
    }
}