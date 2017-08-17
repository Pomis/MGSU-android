package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.val;
import ru.lodmisis.mgsu.App;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.AuthActivity;
import ru.lodmisis.mgsu.activities.DrawerActivity;
import ru.lodmisis.mgsu.api.AuthBody;
import ru.lodmisis.mgsu.api.ErrorHandler;
import ru.lodmisis.mgsu.base.InjectionFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends InjectionFragment {

    @BindView(R.id.piv_background)
    ParallaxImageView pivBackground;

    @BindView(R.id.met_login)
    MaterialEditText metLogin;

    @BindView(R.id.met_password)
    MaterialEditText metPassword;

    @BindView(R.id.b_login)
    Button bLogin;

    @BindView(R.id.b_reg)
    Button bReg;

    @BindView(R.id.ll_auth)
    LinearLayout llAuth;

    @BindView(R.id.met_date_birth)
    MaterialEditText metDateBirth;

    @BindView(R.id.met_date_graduate)
    MaterialEditText metDateGraduate;

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @BindView(R.id.met_phone)
    MaterialEditText metPhone;

    @BindView(R.id.ll_reg)
    LinearLayout llReg;


    private boolean registation = false;

    public AuthFragment() {
    }


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

    @OnClick(R.id.b_reg)
    public void openReg() {
        animate(false);
        ((AuthActivity) getActivity()).setOnBackPressedListener(() -> animate(true));

    }

    private void animate(boolean reversed) {
        val ANIMATION_DURATION = 500;

        llAuth.animate()
                .yBy(App.pxFromDp(getContext(), reversed ? 150 : -150))
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());

        ivLogo.animate()
                .yBy(App.pxFromDp(getContext(), reversed ? 120 : -120))
                .scaleX(reversed ? 1 / .8f : .8f).scaleY(reversed ? 1 / .8f : .8f)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());


        llReg.animate()
                .alpha(reversed ? 0 : 1)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());

        bLogin.animate().alpha(reversed ? 1 : 0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());


        bReg.animate().alpha(reversed ? 1 : 0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());
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
