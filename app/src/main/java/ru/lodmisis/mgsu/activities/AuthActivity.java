package ru.lodmisis.mgsu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.lodmisis.mgsu.App;
import ru.lodmisis.mgsu.R;

public class AuthActivity extends AppCompatActivity {

    Runnable callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.getPrefsInstance(this).isLogged()) {
            DrawerActivity.start(this);
            finish();
        } else {
            setContentView(R.layout.activity_auth);
        }
    }

    @Override
    public void onBackPressed() {
        if (callback != null) callback.run();
        else super.onBackPressed();
    }

    public void setOnBackPressedListener(Runnable callback) {
        this.callback = callback;
    }
}
