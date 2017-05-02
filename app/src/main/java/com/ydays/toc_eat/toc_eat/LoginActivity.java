package com.ydays.toc_eat.toc_eat;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.ydays.toc_eat.Adapter.*;

public class LoginActivity extends AppCompatActivity {

    private LoginAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        mSectionsPagerAdapter = new LoginAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.fragment_container_login);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabsLogin);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(0);
    }
}
