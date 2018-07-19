package com.ags.survey.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ags.survey.R;
import com.ags.survey.fragment.GetSurvey;
import com.ags.survey.fragment.SurveyCreator;
import com.ags.survey.fragment.SurveyQuestionCreator;
import com.ags.survey.fragment.AdminResultSetter;
import com.ags.survey.fragment.UserResultSetter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (getIntent().getStringExtra("USER_TYPE").contentEquals("Admin")){
            setupWithAdminViewPager(viewPager);
        }else {
            setupWithUSerViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupWithUSerViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GetSurvey(MainActivity.this), "SURVEY");
        adapter.addFragment(new UserResultSetter(MainActivity.this), "RESULT");
        viewPager.setAdapter(adapter);
    }

    private void setupWithAdminViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SurveyCreator(MainActivity.this), "CREATE");
        adapter.addFragment(new SurveyQuestionCreator(MainActivity.this), "QUESTIONAIRE");
        adapter.addFragment(new AdminResultSetter(MainActivity.this), "RESULT");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
