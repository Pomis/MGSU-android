package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.nvanbenschoten.motion.ParallaxImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.Settings;
import ru.lodmisis.mgsu.SettingsPrefs;
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

    @BindView(R.id.met_login)
    MaterialEditText metLogin;

    @BindView(R.id.met_password)
    MaterialEditText metPassword;

    @BindView(R.id.b_login)
    Button bLogin;


    public AuthFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @OnClick(R.id.b_login)
    public void auth() {
        disableControls();
        api.login(validate(metLogin, metPassword))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    DrawerActivity.start(getContext());
                    prefs.setIsLogged(true);
                    getActivity().finish();
                }, throwable -> {
                    ErrorHandler.handle(throwable, getContext());
                    enableControls();
                });
    }

    private AuthBody validate(MaterialEditText metLogin, MaterialEditText metPassword) {
        boolean valid = true;
        if (metLogin.getText().length() == 0) {
            metLogin.setError("Поле не может быть пустым!");
            valid = false;
        }
        if (metPassword.getText().length() == 0) {
            metPassword.setError("Поле не может быть пустым");
            valid = false;
        }
        return valid ? new AuthBody(metLogin.getText().toString(), metPassword.getText().toString())
                : null;
    }

    void disableControls() {
        metLogin.setEnabled(false);
        metPassword.setEnabled(false);
        bLogin.setEnabled(false);
    }

    void enableControls() {
        metLogin.setEnabled(true);
        metPassword.setEnabled(true);
        bLogin.setEnabled(true);
    }

}
