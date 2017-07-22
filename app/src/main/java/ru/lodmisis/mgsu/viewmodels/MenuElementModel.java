package ru.lodmisis.mgsu.viewmodels;

import android.support.v4.app.Fragment;
import android.support.annotation.IdRes;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.DrawerActivity;

/**
 * Created by romanismagilov on 22.07.17.
 */

@Animate(Animation.FADE_IN_ASC)
@Layout(R.layout.item_menu)
public class MenuElementModel {
    transient private DrawerActivity drawerActivity;

    boolean selected = false;

    int index;

    Fragment fragment;

    @IdRes
    int icon;

    @View(R.id.v_row_padding)
    android.view.View vPadding;
    @View(R.id.tv_row_title)
    TextView tvTitle;

    String text;

    public MenuElementModel(DrawerActivity drawerActivity, Fragment fragment, int icon, String text, int index) {
        this.drawerActivity = drawerActivity;
        this.fragment = fragment;
        this.icon = icon;
        this.text = text;
        this.index = index;
    }

    @Resolve
    void onResolve() {
        if (index % 2 == 0)
            vPadding.getLayoutParams().width = 0;
        tvTitle.setText(text);

        tvTitle.setTextColor(selected?
                drawerActivity.getResources().getColor(R.color.colorAccent):
                drawerActivity.getResources().getColor(R.color.colorTransparent)
        );
    }


    public MenuElementModel(int index) {
        this.index = index;
    }

    @Click(R.id.rl_row_menu_item)
    void onClick() {
        try {
            selected = true;
            drawerActivity.handleFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
