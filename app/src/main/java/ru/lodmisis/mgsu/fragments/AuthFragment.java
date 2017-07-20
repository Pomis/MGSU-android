package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nvanbenschoten.motion.ParallaxImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.DrawerActivity;
import ru.lodmisis.mgsu.api.ErrorHandler;
import ru.lodmisis.mgsu.base.BaseFragment;
import ru.lodmisis.mgsu.api.AuthBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends BaseFragment {

    @BindView(R.id.piv_background)
    ParallaxImageView pivBackground;


    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //auth();
    }

    @Override
    public void onResume() {
        super.onResume();
        pivBackground.registerSensorManager();
    }

    @Override
    public void onPause() {
        super.onPause();
        pivBackground.unregisterSensorManager();
    }

    private void auth() {
        api.login(new AuthBody("admin@mail.ru", "qwerty1234"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    System.out.println(user.firstName);
                    DrawerActivity.start(getContext());
                }, throwable -> ErrorHandler.handle(throwable, getContext()));
    }

}
