package com.example.xumuxin.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.brand_detail)
public class brand_details extends AppCompatActivity {
    @ViewInject(R.id.products)
    private RecyclerView recyclerView_product;
    private List<product_item> datas_product = new ArrayList<>();
    private product_adapter adapter_product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        try {
            initDatas();
            initViews();
        } catch (Exception e) {
            Log.e("brand_detail", "Wrong!", e);
        }
    }

    void initDatas() {
        datas_product.add(new product_item("acura", "ss", "sd"));
        datas_product.add(new product_item("audi", "ss", "sd"));
        datas_product.add(new product_item("benz", "ss", "sd"));
        datas_product.add(new product_item("karas", "ss", "sd"));


    }

    void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_product.setLayoutManager(linearLayoutManager);
        adapter_product = new product_adapter(this, datas_product);
        recyclerView_product.setAdapter(adapter_product);
    }
}
