package com.example.xumuxin.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.search)
public class search extends Fragment {
    @ViewInject(R.id.recent_brand)
    private RecyclerView recyclerView_recent;
    @ViewInject(R.id.hot_brand)
    private RecyclerView recyclerView_hot;
    private List<brandInfo> datas_hot = new ArrayList<>(), datas_recent = new ArrayList<>();
    private brand_adapter adapter_recent, adapter_hot;

    private Brand_search_items hot_brands, recent_brands;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        try {
            initDatas();
            initViews(view);
        } catch (Exception e) {
            Log.e("search", "wrong!", e);
        }

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
        askForData();
    }

    @Event(value = {R.id.search_input}, type = TextView.OnEditorActionListener.class)
    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == 0 || actionId == 3) && event != null) {
            // search
            return true;
        }
        return false;
    }

    void initViews(View view) {



//        EditText input = view.findViewById(R.id.search_input);
//        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((actionId == 0 || actionId == 3) && event != null) {
//                    // search
//                    return true;
//                }
//                return false;
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_recent.setLayoutManager(linearLayoutManager);
        adapter_recent = new brand_adapter(getContext(), datas_recent);
        recyclerView_recent.setAdapter(adapter_recent);

        LinearLayoutManager anotherLinearLayout = new LinearLayoutManager(getContext());
        anotherLinearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_hot.setLayoutManager(anotherLinearLayout);
        adapter_hot = new brand_adapter(getContext(), datas_hot);
        recyclerView_hot.setAdapter(adapter_hot);
    }

    private void askForData() {
        askForSearchData("brand_heat");
        askForSearchData("recent_searching");
    }

    private void askForSearchData(final String t) {
        String url = "http://private-anon-12c211a2e0-logorecognition.apiary-mock.com/" + t + "/?num=4";
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                if (t.equals("brand_heat")) {
                    hot_brands = gson.fromJson(result, Brand_search_items.class);
                    Log.e("get index", "Content: " + hot_brands.getBrand_names().get(0)+"...");
                } else {
                    recent_brands = gson.fromJson(result, Brand_search_items.class);
                    Log.e("get index", "Content: " + recent_brands.getBrand_names().get(0)+"...");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
