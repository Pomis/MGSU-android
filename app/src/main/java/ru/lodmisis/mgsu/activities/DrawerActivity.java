package ru.lodmisis.mgsu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.lodmisis.mgsu.App;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.api.ErrorHandler;
import ru.lodmisis.mgsu.fragments.CalendarFragment;
import ru.lodmisis.mgsu.fragments.EndowmentFragment;
import ru.lodmisis.mgsu.fragments.EventsFragment;
import ru.lodmisis.mgsu.fragments.FAQFragment;
import ru.lodmisis.mgsu.fragments.NewsFragment;
import ru.lodmisis.mgsu.fragments.UserFragment;
import ru.lodmisis.mgsu.viewmodels.Enumeration;
import ru.lodmisis.mgsu.viewmodels.MenuItemModel;

public class DrawerActivity extends AppCompatActivity {

    Fragment selectedFragment;
    MenuItemModel userMenuItem;


    @BindView(R.id.phv_menu)
    public PlaceHolderView phvMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    public static void start(Context context) {
        Intent starter = new Intent(context, DrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initDrawer();
        openDefaultTab(savedInstanceState);
        loadCurrentUserInfo();
        App.testCookies(this);
    }


    void initDrawer() {
        phvMenu.addView(new Enumeration(""));

        userMenuItem = new MenuItemModel(this,
                UserFragment.class,
                R.drawable.ic_login,
                "Вход не выполнен", 0, false);
        phvMenu.addView(userMenuItem);

        phvMenu.addView(new Enumeration(""));
        phvMenu.addView(new Enumeration(""));

        phvMenu.addView(new MenuItemModel(this,
                EndowmentFragment.class,
                R.drawable.ic_fund,
                "Об Эндаумент фонде", 0, true));
        phvMenu.addView(new MenuItemModel(this,
                NewsFragment.class,
                R.drawable.ic_news,
                "Новости", 1, false));
        phvMenu.addView(new MenuItemModel(this,
                EventsFragment.class,
                R.drawable.ic_events,
                "Мероприятия", 0, false));
        phvMenu.addView(new MenuItemModel(this,
                CalendarFragment.class,
                R.drawable.ic_calendar,
                "Календарь", 1, false));
        phvMenu.addView(new MenuItemModel(this,
                FAQFragment.class,
                R.drawable.ic_faq,
                "Задать вопрос", 0, false));
        phvMenu.addView(new MenuItemModel(this,
                FAQFragment.class,
                R.drawable.ic_sales,
                "Скидки", 1, false));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "Нажмите ещё раз для выхода", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }


    void openDefaultTab(Bundle savedInstanceState) {
//        int tabId = 0;
//        if (savedInstanceState == null)
//            tabId = R.id.nav_about;
//        else
//            tabId = savedInstanceState.getInt("currentTab", R.id.nav_about);

        handleFragment(EndowmentFragment.class);
    }


    public <T extends Fragment> void handleFragment(Class<T> fragmentClass) {
        // view updating
        refreshDraweWithDelay();
        Fragment fragment;
        try {
            fragment = fragmentClass.newInstance();
            clearViews();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (selectedFragment != null)
                transaction.detach(selectedFragment);
            this.selectedFragment = fragment;
            transaction.add(R.id.fl_container, fragment, "current");
            transaction.commit();

            closeDrawer();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void refreshDraweWithDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                runOnUiThread(() -> phvMenu.refresh());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);

    }

    private void clearViews() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getFragmentManager().findFragmentByTag("current") != null)
            transaction.detach(getFragmentManager().findFragmentByTag("current"));
        transaction.commit();
        ((FrameLayout) findViewById(R.id.fl_container)).removeAllViews();
        selectedFragment = null;
    }

    private void loadCurrentUserInfo() {
        App.getEndowmentService(this).getCurrentUser().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    userMenuItem.text = user.firstName + " " + user.lastName;
                }, throwable -> {
                    ErrorHandler.handle(throwable, this);
                });
    }
}
