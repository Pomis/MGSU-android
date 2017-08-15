package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.canvas.DonationView;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {

    @BindView(R.id.dv_donations)
    DonationView dvDonations;

    @BindView(R.id.iv_project)
    ImageView ivProject;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_short_descr)
    TextView tvShortDescr;

    @BindView(R.id.wv_full_description)
    WebView wvDescr;

    ProjectModel project;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    public ProjectFragment setProject(ProjectModel project) {
        this.project = project;
        return this;
    }

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (project != null) {

//            if (project.need != null && project.need != 0 && project.given != null)
            dvDonations.setPercentage(((float) project.given / (float)project.need));

            if (project.img != null)
                Glide.with(getContext()).load(project.img.getOriginal()).into(ivProject);

            tvName.setText(project.name);

            tvShortDescr.setText(project.shortDescription);

            if (project.content != null)
                wvDescr.loadData(project.content, "text/html; charset=utf-8", "utf-8");

            tvMoney.setText(project.given + "/" + project.need);
//                tvDescr.setText(project.content);
        }


    }
}
