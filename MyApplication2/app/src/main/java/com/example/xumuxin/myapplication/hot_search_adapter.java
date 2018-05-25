package com.example.xumuxin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XDDN2 on 2018/5/8.
 */

public class hot_search_adapter extends RecyclerView.Adapter<hot_search_adapter.ViewHolder> {
    private Context context;
    private List<hot_search_item> list;

    /**
     * 构造函数
     * @param context
     * @param list
     */
    public hot_search_adapter(Context context, List<hot_search_item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public hot_search_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_brand_item, parent, false);
        return new hot_search_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(hot_search_adapter.ViewHolder holder, int position) {
        hot_search_item info = list.get(position);
        holder.brand_name.setText(info.getName());
        holder.search_time.setText(info.getSearch_time());
    }

    public List<hot_search_item> getList() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * static ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView brand_name;
        TextView search_time;
        ImageView brand_img;


        public ViewHolder(View itemView) {
            super(itemView);
            brand_name = itemView.findViewById(R.id.brand_name);
            search_time = itemView.findViewById(R.id.search_num);
            brand_img = itemView.findViewById(R.id.brand_img);
        }
    }
}
