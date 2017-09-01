package ru.lodmisis.mgsu.fragments;


import android.animation.Animator;
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
import ru.lodmisis.mgsu.api.RegBody;
import ru.lodmisis.mgsu.base.InjectionFragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    boolean animationProcessing = false;
    private RegBody regBody;

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
        disableAuthControls();
        api.login(validate(metLogin, metPassword))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    DrawerActivity.start(getContext());
                    prefs.setIsLogged(true);
                    getActivity().finish();
                }, throwable -> {
                    ErrorHandler.handle(throwable, getContext());
                    enableAuthControls();
                });
    }

    @OnClick(R.id.b_reg)
    public void openReg() {
        animate(false);
        ((AuthActivity) getActivity()).setOnBackPressedListener(() -> {
            if (registation) animate(true);
        });


    }

    private void animate(boolean reversed) {
        if (!animationProcessing) {

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
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            animationProcessing = true;
                            bLogin.setVisibility(VISIBLE);
                            bReg.setVisibility(VISIBLE);
                            llReg.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationProcessing = false;
                            registation = !reversed;
                            if (reversed) {
                                llReg.setVisibility(GONE);
                            } else {
                                bLogin.setVisibility(GONE);
                                bReg.setVisibility(GONE);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

        }
    }


    @OnClick(R.id.b_submit)
    void continueRegistration() {
        regBody = new RegBody();
        if (regBody.preValidate(
                metLogin, metPassword, metPhone, metDateBirth, metDateGraduate
        )) {
            animateNext();
        }
    }

    private void animateNext() {

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

    void disableAuthControls() {
        metLogin.setEnabled(false);
        metPassword.setEnabled(false);
        bLogin.setEnabled(false);
    }

    void enableAuthControls() {
        metLogin.setEnabled(true);
        metPassword.setEnabled(true);
        bLogin.setEnabled(true);
    }

    void disableRegControls() {

    }

    void enableRegControls() {

    }
}
