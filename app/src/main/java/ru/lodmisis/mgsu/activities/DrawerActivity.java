package ru.lodmisis.mgsu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.fragments.CalendarFragment;
import ru.lodmisis.mgsu.fragments.EndowmentFragment;
import ru.lodmisis.mgsu.fragments.EventsFragment;
import ru.lodmisis.mgsu.fragments.FAQFragment;
import ru.lodmisis.mgsu.fragments.NewsFragment;
import ru.lodmisis.mgsu.viewmodels.Enumeration;
import ru.lodmisis.mgsu.viewmodels.MenuElementModel;

public class DrawerActivity extends AppCompatActivity{

    Fragment fragment;


//    @BindView(R.id.phv_menu)
//    PlaceHolderView phvMenu;

    public static void start(Context context) {
        Intent starter = new Intent(context, DrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getViewTreeObserver().addOnGlobalLayoutListener(this::initDrawer);
//        ButterKnife.bind(this);

        initDrawer();
        openDefaultTab(savedInstanceState);
    }



    void initDrawer() {
        PlaceHolderView phvMenu = (PlaceHolderView) findViewById(R.id.phv_menu);

        phvMenu.addView(new Enumeration(""));
        phvMenu.addView(new MenuElementModel(this, new EndowmentFragment(), 0, "Об Эндаумент фонде",0));
        phvMenu.addView(new MenuElementModel(this, new NewsFragment(), 0, "Новости",1));
        phvMenu.addView(new MenuElementModel(this, new EventsFragment(), 0, "Мероприятия",0));
        phvMenu.addView(new MenuElementModel(this, new CalendarFragment(), 0, "Календарь",1));
        phvMenu.addView(new MenuElementModel(this, new FAQFragment(), 0, "Задать вопрос",0));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    void openDefaultTab(Bundle savedInstanceState) {
        int tabId = 0;
        if (savedInstanceState == null)
            tabId = R.id.nav_about;
        else
            tabId = savedInstanceState.getInt("currentTab", R.id.nav_about);


//        handleFragmentById(tabId);
        handleFragment(new EndowmentFragment());
    }


    public void handleFragment(Fragment fragment) {
        clearViews();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (fragment != null)
            fTrans.detach(fragment);
        this.fragment = fragment;
        fTrans.add(R.id.fl_container, fragment, "current");
        fTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

//    private void handleFragmentById(int id) {
//        clearViews();
//        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
//        if (fragment != null)
//            fTrans.detach(fragment);
////        currentTab = id;
//        switch (id) {
//
//            case R.id.nav_about:
//                fragment = new EndowmentFragment();
//                break;
//
//            case R.id.nav_news:
//                fragment = new NewsFragment();
//                break;
//
//            case R.id.nav_events:
//                fragment = new EventsFragment();
//                break;
//
//            case R.id.nav_faq:
//                fragment = new FAQFragment();
//                break;
//
//            case R.id.nav_calendar:
//                fragment = new CalendarFragment();
//                break;
//
//
//        }
//        fTrans.add(R.id.fl_container, fragment, "current");
//        fTrans.commit();
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//
//    }

    private void clearViews() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getFragmentManager().findFragmentByTag("current") != null)
            transaction.detach(getFragmentManager().findFragmentByTag("current"));
        transaction.commit();
        ((FrameLayout) findViewById(R.id.fl_container)).removeAllViews();
        fragment = null;
    }
}
