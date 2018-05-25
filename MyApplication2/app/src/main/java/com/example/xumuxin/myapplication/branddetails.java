package com.example.xumuxin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.thinkcool.circletextimageview.CircleTextImageView;

/**
 * Created by XDDN2 on 2018/2/22.
 */

public class branddetails extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branddetails);

        CircleTextImageView brand = (CircleTextImageView)findViewById(R.id.brandcircle);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrend();
            }
        });

    }

    private void showTrend() {
        CircleTextImageView brand = (CircleTextImageView)findViewById(R.id.brandcircle);
        String names = brand.getTextString();
        Intent intent = new Intent(branddetails.this, datachart.class);
        intent.putExtra("name", names);
        startActivity(intent);
    }

}
