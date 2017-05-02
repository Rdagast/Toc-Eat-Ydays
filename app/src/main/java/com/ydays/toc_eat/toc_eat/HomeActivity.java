package com.ydays.toc_eat.toc_eat;

/**
 * Created by clemb on 22/02/2017.
 */


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.ydays.toc_eat.Adapter.*;

public class HomeActivity extends AppCompatActivity {

    private NavigationAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    private TabLayout tabLayout;
    public static boolean test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        mSectionsPagerAdapter = new NavigationAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.fragment_container_login);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabsLogin);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);

    }
}