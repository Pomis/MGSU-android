package ru.lodmisis.mgsu.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;

import java.io.Serializable;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.fragments.ErrorFragment;
import ru.lodmisis.mgsu.fragments.EventFragment;
import ru.lodmisis.mgsu.fragments.ProjectFragment;
import ru.lodmisis.mgsu.viewmodels.EventModel;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;

public class SwipeableActivity extends SwipeBackActivity {

    static Class currentFragmentClass;

    public static <T extends Fragment> void start(Context context, Class<T> fragmentClass,
                                                  @Nullable Serializable model) {
        currentFragmentClass = fragmentClass;
        Intent starter = new Intent(context, SwipeableActivity.class);
        if (model != null) starter.putExtra("model", model);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeable);
//        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        try {
            readModel();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void readModel() throws IllegalAccessException, InstantiationException {
        Serializable model = getIntent().getExtras().getSerializable("model");
        Fragment currentFragment = new Fragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (model instanceof ProjectModel) {
            currentFragment = new ProjectFragment().setProject((ProjectModel) model);
        } else if (model instanceof Throwable) {
            currentFragment = new ErrorFragment().setModel((Throwable) model);
        } else if (model instanceof EventModel) {
            currentFragment = new EventFragment().setModel((EventModel) model);
        } else {
            currentFragment = (Fragment) currentFragmentClass.newInstance();
        }
        fTrans.add(R.id.fl_container, currentFragment, "current");
        fTrans.commit();

    }
}
