package com.example.xumuxin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XDDN2 on 2018/5/7.
 */

public class brand_adapter  extends RecyclerView.Adapter<brand_adapter.ViewHolder> {

    private Context context;
    private List<brandInfo> list;

    private AdapterView.OnItemClickListener mOnItemClickLitener;
    /**
     * 构造函数
     * @param context
     * @param list
     */
    public brand_adapter(Context context, List<brandInfo> list) {
        this.context = context;
        this.list = list;
    }



    public void setOnItemClickLitener(AdapterView.OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand_content, parent, false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        brandInfo info = list.get(position);
        holder.name.setText(info.getName());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(null,holder.itemView, pos, holder.itemView.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public brandInfo getItem(int pos) {
        return list.get(pos);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }



    /**
     * static ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView brand;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.brand_name);
            brand = itemView.findViewById(R.id.brand_img);
        }
    }
}
