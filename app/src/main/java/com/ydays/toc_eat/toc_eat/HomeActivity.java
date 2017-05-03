package com.ydays.toc_eat.toc_eat;

/**
 * Created by clemb on 22/02/2017.
 */


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(Html.fromHtml("<font color='#ffffff'> " + getResources().getString(R.string.app_name) + "</font>"));


       // Window window = this.getWindow();
       // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        mSectionsPagerAdapter = new NavigationAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.fragment_container_login);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabsLogin);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setIcon(R.mipmap.create);

        tabLayout.getTabAt(1).setIcon(R.mipmap.search);

        tabLayout.getTabAt(2).setIcon(R.mipmap.messagerie);

        tabLayout.getTabAt(3).setIcon(R.mipmap.profil);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight(15);

        mViewPager.setCurrentItem(1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("auth_token", null); // Storing string
                editor.putInt("user_id", 0);
                editor.apply();

                finish();
                startActivity(new Intent(this,LoginActivity.class));
               return super.onOptionsItemSelected(item);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        tabLayout.getTabAt(1).select();
    }
}