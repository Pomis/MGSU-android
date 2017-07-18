package ru.lodmisis.mgsu.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;

import java.io.Serializable;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.fragments.ProjectFragment;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;

public class SwipeableActivity extends SwipeBackActivity {

    public static void start(Context context, Serializable model) {
        Intent starter = new Intent(context, SwipeableActivity.class);
        starter.putExtra("model", model);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeable);
//        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        readModel();

    }

    private void readModel() {
        Serializable model = getIntent().getExtras().getSerializable("model");
        Fragment currentFragment = new Fragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (model instanceof ProjectModel) {
            currentFragment = new ProjectFragment().setProject((ProjectModel) model);
        }
        fTrans.add(R.id.fl_container, currentFragment, "current");
        fTrans.commit();

    }
}
