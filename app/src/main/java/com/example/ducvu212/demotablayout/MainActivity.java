package com.example.ducvu212.demotablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter ;
    private ViewPager mViewPager ;
    private TabLayout mTabLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds() ;
        initComponent() ;
    }

    private void initComponent() {

        FragmentManager manager = getSupportFragmentManager() ;
        mPagerAdapter = new PagerAdapter(manager) ;
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mTabLayout.setTabTextColors(R.color.white, R.color.tabselect);

    }

    private void findViewByIds() {
        mViewPager = findViewById(R.id.view_pager) ;
        mTabLayout = findViewById(R.id.tab_layout) ;
    }
}
