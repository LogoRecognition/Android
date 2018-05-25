package com.example.xumuxin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by XDDN2 on 2018/5/16.
 */


@ContentView(R.layout.most_hot_activity)
public class Most_hot_activity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        try {
//            initDatas();
//            initViews();
        } catch (Exception e) {
            Log.e("brand_detail", "Wrong!", e);
        }
    }

    private void initDatas() {

    }
}
