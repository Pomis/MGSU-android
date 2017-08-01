package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.InjectionFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends InjectionFragment {

    @BindView(R.id.tv_error)
    TextView tvError;

    Throwable throwable;

    public ErrorFragment() {
    }

    public ErrorFragment setModel(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_error, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvError.setText(throwable.getMessage() + "\n" + Arrays.toString(throwable.getStackTrace()));
    }
}
