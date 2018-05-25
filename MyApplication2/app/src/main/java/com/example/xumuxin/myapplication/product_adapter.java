package com.example.xumuxin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XDDN2 on 2018/5/7.
 */

public class product_adapter extends RecyclerView.Adapter<product_adapter.ViewHolder> {
    private Context context;
    private List<product_item> list;

    /**
     * 构造函数
     * @param context
     * @param list
     */
    public product_adapter(Context context, List<product_item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public product_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new product_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(product_adapter.ViewHolder holder, int position) {
        product_item info = list.get(position);
        holder.product_name.setText(info.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * static ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView description;
        ImageView producet_img;


        public ViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_introduce);
            producet_img = itemView.findViewById(R.id.product_img);
        }
    }
}
