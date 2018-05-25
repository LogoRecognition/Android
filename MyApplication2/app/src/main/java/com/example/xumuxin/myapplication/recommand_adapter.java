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

public class recommand_adapter extends RecyclerView.Adapter<recommand_adapter.ViewHolder> {
    private Context context;
    private List<recommand_item> list;

    /**
     * 构造函数
     * @param context
     * @param list
     */
    public recommand_adapter(Context context, List<recommand_item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public recommand_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommand_item, parent, false);
        return new recommand_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(recommand_adapter.ViewHolder holder, int position) {
        recommand_item info = list.get(position);
        holder.brand_name.setText(info.getName());
        holder.category.setText(info.getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * static ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView category, brand_name;
        ImageView brand_img;



        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            brand_name = itemView.findViewById(R.id.brand_name);
            brand_img = itemView.findViewById(R.id.brand_img);
        }
    }
}
