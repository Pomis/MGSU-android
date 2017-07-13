package ru.lodmisis.mgsu.viewmodels;

import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ru.lodmisis.mgsu.R;


@Layout(R.layout.item_enum)
public class Enumeration {

    public String text;

    @View(R.id.tv_row_enum)
    private TextView tvText;

    @Resolve
    private void onResolved() {
        tvText.setText(text);
    }

    public Enumeration(String text) {
        this.text = text;
    }
}
