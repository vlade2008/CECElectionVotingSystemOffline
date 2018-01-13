package com.example.xtian.cecelectionvotingsystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.AuditorFragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.Firstyr_fragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.Fourthyr_fragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.PresidentFragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.Pro_fragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.Secondyr_fragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.SecretaryFragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.Thirdyr_fragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.TreasureFragment;
import com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage.VicePresidentFragment;
import com.example.xtian.cecelectionvotingsystem.R;

/**
 * Created by Xtian on 11/18/2015.
 */
public class CandidatesFragment extends Fragment  {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 10 ;


    public CandidatesFragment(){}

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_candidates, container, false);
        tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return rootView;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PresidentFragment();
                case 1:
                    return new VicePresidentFragment();
                case 2:
                    return new SecretaryFragment();
                case 3:
                    return new TreasureFragment();
                case 4:
                    return new AuditorFragment();
                case 5:
                    return new Pro_fragment();
                case 6:
                    return new Firstyr_fragment();
                case 7:
                    return new Secondyr_fragment();
                case 8:
                    return new Thirdyr_fragment();
                case 9:
                    return new Fourthyr_fragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "President";
                case 1 :
                    return "Vice-President";
                case 2 :
                    return "Secretary";
                case 3 :
                    return "Treasurer";
                case 4 :
                    return "Auditor";
                case 5 :
                    return "Pro";
                case 6 :
                    return "1st year Representative";
                case 7 :
                    return "2nd year Representative";
                case 8 :
                    return "3rd year Representative";
                case 9 :
                    return "4th year Representative";
            }
            return null;
        }
    }
}
