package com.example.xumuxin.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XDDN2 on 2018/5/7.
 */

public class brand_detail extends Fragment {
    private RecyclerView recyclerView_recent, recyclerView_hot;
    private List<brandInfo> datas_hot, datas_recent;
    private brand_adapter adapter_recent, adapter_hot;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_recent.setLayoutManager(linearLayoutManager);
        adapter_recent = new brand_adapter(getContext(), datas_recent);
        recyclerView_recent.setAdapter(adapter_recent);

        recyclerView_hot.setLayoutManager(linearLayoutManager);
        adapter_hot = new brand_adapter(getContext(), datas_hot);
        recyclerView_hot.setAdapter(adapter_hot);
        return view;
    }
    void initDatas() {
        datas_recent.add(new brandInfo("ss", "acura"));
        datas_recent.add(new brandInfo("ss", "audi"));
        datas_recent.add(new brandInfo("ss", "benz"));
        datas_recent.add(new brandInfo("ss", "karas"));

        datas_hot.add(new brandInfo("ss", "acura"));
        datas_hot.add(new brandInfo("ss", "audi"));
        datas_hot.add(new brandInfo("ss", "benz"));
        datas_hot.add(new brandInfo("ss", "karas"));
    }

    void initViews(View view) {
        recyclerView_recent = view.findViewById(R.id.recent_brand);
        recyclerView_hot = view.findViewById(R.id.hot_brand);
        EditText input = view.findViewById(R.id.search_input);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    // search
                    return true;
                }
                return false;
            }
        });
    }
}
