package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.canvas.DonationView;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {

    @BindView(R.id.skv_loading_indicator)
    SpinKitView skvIndicator;

    @BindView(R.id.b_donate)
    Button bDonate;

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

    @OnClick(R.id.b_donate)
    void initDonate() {
        skvIndicator.setVisibility(View.VISIBLE);
        bDonate.setVisibility(View.GONE);
        Log.d("kek", "http://185.189.13.148:4000/project/"+project.id);
        wvDescr.getSettings().setJavaScriptEnabled(true);
        wvDescr.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                wvDescr.loadUrl
//                        ("javascript:document.getElementsByClassName('donation-form')[0]" +
//                                ".scrollIntoView();");
                skvIndicator.setVisibility(View.GONE);
                wvDescr.loadUrl("javascript:var kek = document.getElementsByTagName(\"form\")[0].innerHTML;\n" +
                        "var divs = document.getElementsByTagName(\"div\");\n" +
                        "for(var i = 0; i < divs.length; i++){\n" +
                        "   divs[i].innerHTML = \"\";\n" +
                        "}\n" +
                        "document.body.innerHTML = kek;" +
                        "undefined;");

            }
        });
        wvDescr.loadUrl("http://185.189.14.156/project/"+project.id);
    }
}

// RAW JS CODE:
//var kek = document.getElementsByTagName("form")[0].innerHTML;
//    var divs = document.getElementsByTagName("div");
//for(var i = 0; i < divs.length; i++){
//        divs[i].innerHTML = "";
//        }
//        document.body.innerHTML = kek;
//  undefined;