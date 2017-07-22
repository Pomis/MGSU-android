package ru.lodmisis.mgsu.viewmodels;

import android.app.Fragment;
import android.support.annotation.IdRes;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ru.lodmisis.mgsu.R;

/**
 * Created by romanismagilov on 22.07.17.
 */

@Animate(Animation.FADE_IN_ASC)
@Layout(R.layout.item_menu)
public class MenuElementModel {
    int index;

    Fragment fragment;

    @IdRes int icon;

    @View(R.id.v_row_padding) android.view.View vPadding;
    @View(R.id.tv_row_title) TextView tvTitle;

    String text;

    public MenuElementModel(Fragment fragment, @IdRes int icon, String text, int index) {
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

    }


    public MenuElementModel(int index) {
        this.index = index;
    }
}
