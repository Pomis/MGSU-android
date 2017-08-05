package ru.lodmisis.mgsu.viewmodels;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ru.lodmisis.mgsu.App;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.activities.DrawerActivity;

/**
 * Created by romanismagilov on 22.07.17.
 */

@Animate(Animation.FADE_IN_ASC)
@Layout(R.layout.item_menu)
public class MenuItemModel {
    transient private DrawerActivity drawerActivity;

    public boolean selected = false;

    int index;

    Class fragmentClass;

    public String text;

    @DrawableRes
    int icon;

    @View(R.id.v_row_padding)
    android.view.View vPadding;
    @View(R.id.tv_row_title)
    TextView tvTitle;
    @View(R.id.iv_row_icon)
    ImageView ivIcon;
    @View(R.id.iv_row_hexagon)
    ImageView ivHexagon;


    public <T extends Fragment> MenuItemModel(DrawerActivity drawerActivity, Class<T> fragmentClass, int icon, String text, int index, boolean selected) {
        this.drawerActivity = drawerActivity;
        this.fragmentClass = fragmentClass;
        this.icon = icon;
        this.text = text;
        this.index = index;
        this.selected = selected;
    }

    @Resolve
    void onResolve() {
        if (index % 2 == 0)
            vPadding.getLayoutParams().width = 0;
        else
            vPadding.getLayoutParams().width = App.pxFromDp(drawerActivity, 30);

        tvTitle.setText(text);

        ivHexagon.setColorFilter(selected ?
                drawerActivity.getResources().getColor(R.color.colorAccent) :
                drawerActivity.getResources().getColor(R.color.colorWhite)
        );

        ivIcon.setImageDrawable(drawerActivity.getResources().getDrawable(icon));
    }


    public MenuItemModel(int index) {
        this.index = index;
    }

    @Click(R.id.rl_row_menu_item)
    void onClick() {
        try {
            if (!selected) {
                drawerActivity.phvMenu.getAllViewResolvers().stream().filter(o -> o instanceof MenuItemModel).forEach(o -> ((MenuItemModel) o).selected = false);
                selected = true;
                drawerActivity.setTitle(text);
                drawerActivity.handleFragment(fragmentClass);
            } else {
                drawerActivity.closeDrawer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
