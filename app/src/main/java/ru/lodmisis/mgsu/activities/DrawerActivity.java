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
import android.widget.Toast;

import java.util.Stack;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.fragments.EndowmentFragment;
import ru.lodmisis.mgsu.fragments.EventsFragment;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EndowmentFragment endowmentFragment;
    EventsFragment eventsFragment;

    Stack<Fragment> backStack;

    public static void start(Context context) {
        Intent starter = new Intent(context, DrawerActivity.class);
//        starter.putExtra();
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
        navigationView.setNavigationItemSelectedListener(this);

        backStack = new Stack<>();
//        initFragments();
    }

    //    private void initFragments() {
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentTransaction fTrans = getSupportFragmentManager()
                    .beginTransaction();
            if (backStack.size() != 0) {
                fTrans.remove(backStack.pop());
            } else {
                Toast.makeText(getBaseContext(), "Нажмите ещё раз назад для выхода", Toast.LENGTH_SHORT)
                        .show();
            }
            if (backStack.size() != 0) {
                fTrans.show(backStack.peek());
                backStack.peek().onViewCreated(null, null);

            }
            fTrans.commit();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        switch (id) {
            case R.id.nav_about:
//                if (endowmentFragment == null)
                    endowmentFragment = new EndowmentFragment();
                addToStack(transaction, endowmentFragment);
                break;
            case R.id.nav_events:
//                if (eventsFragment == null)
                    eventsFragment = new EventsFragment();
                addToStack(transaction, eventsFragment);

                break;
        }

        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addToStack(FragmentTransaction transaction, Fragment fragment) {
        if (backStack.size() == 0 ||
                !(backStack.peek().getClass().equals(fragment.getClass()))) {

            if (backStack.size() != 0)
                transaction.hide(backStack.peek());
            if (fragment.isAdded()) {
                transaction.show(fragment);
                backStack.add(fragment);
                fragment.onViewCreated(null, null);
            } else {
                transaction.add(R.id.fl_container, fragment);
                backStack.add(fragment);

            }
        }
    }
}
