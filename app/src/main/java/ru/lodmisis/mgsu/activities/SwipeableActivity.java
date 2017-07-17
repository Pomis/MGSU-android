package ru.lodmisis.mgsu.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.io.Serializable;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.fragments.AuthFragment;
import ru.lodmisis.mgsu.fragments.ProjectFragment;
import ru.lodmisis.mgsu.viewmodels.Project;

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
        if (model instanceof Project) {
            currentFragment = new ProjectFragment().setProject((Project) model);
        }
        fTrans.add(R.id.fl_container, currentFragment, "current");
        fTrans.commit();

    }
}
