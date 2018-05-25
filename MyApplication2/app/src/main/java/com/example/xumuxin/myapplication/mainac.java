package com.example.xumuxin.myapplication;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.main)
public class mainac extends AppCompatActivity {
    @ViewInject(R.id.viewPager)
    private ViewPager vp;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private brand_detect detect_activity;
    private search search_activity;
    private statistic_activity statistic_activities;
    private personal_activity personal_activities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            x.view().inject(this);
            initViews();
            verifyPermission(this);
            Log.e("main", "success");
        } catch (Exception e) {
            Log.e("main", "something wrong", e);
        }
    }

    @Event(value = {R.id.personal, R.id.branch, R.id.search, R.id.statistic})
    private void getEvent(View v) {
        // 测试按钮
        try {
            switch (v.getId()) {
                case R.id.branch:
                    vp.setCurrentItem(0);
                    break;
                case R.id.search:
                    vp.setCurrentItem(1);
                    break;
                case R.id.statistic:
                    vp.setCurrentItem(2);
                    break;
                case R.id.personal:
                    vp.setCurrentItem(3);
                    break;
                default:
                    Log.e("something", "Wrong!!!");
                    break;
            }
        } catch (Exception e) {
            Log.e("jump", "wrong", e);
        }
//        Log.e("click function",String.valueOf(p));
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    private void initViews() {
//        ImageView branch = (ImageView) findViewById(R.id.branch);
//        ImageView search = (ImageView) findViewById(R.id.search);
//        ImageView statistic = (ImageView) findViewById(R.id.statistic);
//        ImageView personal = (ImageView) findViewById(R.id.personal);
//
//
//
//        branch.setOnClickListener(this);
//        search.setOnClickListener(this);
//        statistic.setOnClickListener(this);
//        personal.setOnClickListener(this);
        detect_activity = new brand_detect();
        search_activity = new search();
        statistic_activities = new statistic_activity();
        personal_activities = new personal_activity();


        vp = (ViewPager)findViewById(R.id.viewPager);


        fragmentList.add(detect_activity);
        fragmentList.add(search_activity);
        fragmentList.add(statistic_activities);
        fragmentList.add(personal_activities);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(fragmentAdapter);
        vp.setCurrentItem(0);
    }

    private static void verifyPermission(Activity activity) {
        List<String> permissions = new ArrayList<>();
        permissions.add("android.permission.INTERNET");
        permissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
        permissions.add("android.permission.ACCESS_NETWORK_STATE");
        permissions.add("android.permission.ACCESS_WIFI_STATE");
        permissions.add("android.permission.READ_PHONE_STATE");
        permissions.add("android.permission.ACCESS_COARSE_LOCATION");
        for (String s : permissions) {
            int p = ActivityCompat.checkSelfPermission(activity, s);
            if (p != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[] {s}, PackageManager.PERMISSION_GRANTED);
            }
        }
    }
}
