package ru.lodmisis.mgsu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.nvanbenschoten.motion.ParallaxImageView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.lodmisis.mgsu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndowmentFragment extends Fragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

//    @BindView(R.id.piv_background)
//    ParallaxImageView pivBackground;

    public EndowmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_endowment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("Миссия", MissionFragment.class)
                .add("Цели", TargetsFragment.class)
                .add("Проекты", ProjectsFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setCurrentItem(1);
        viewPagerTab.setViewPager(viewPager);
    }


    //    @Override
//    public void onResume() {
//        super.onResume();
//        pivBackground.re();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        pivBackground.unregisterSensorManager();
//    }
}
