package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.DrawerActivity;
import ru.lodmisis.mgsu.base.BaseFragment;
import ru.lodmisis.mgsu.api.AuthBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends BaseFragment {


    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth();
    }

    private void auth() {
        api.login(new AuthBody("boris@mail.ru", "qwerty1234"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(user -> {
                    System.out.println(user.firstName);
                    DrawerActivity.start(getContext());
                });
    }

}
