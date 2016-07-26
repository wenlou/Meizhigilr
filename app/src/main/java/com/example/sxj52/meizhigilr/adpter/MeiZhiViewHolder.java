package com.example.sxj52.meizhigilr.adpter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by sxj52 on 2016/7/25.
 */
public class MeiZhiViewHolder extends BaseViewHolder<GanHuo.Result> {
    private ImageView image;
    public MeiZhiViewHolder(ViewGroup parent) {
        super(parent, R.layout.meizhi_item);
        image=$(R.id.image);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        //上下文
        Glide.with(getContext())
                //地址
                .load(data.getUrl())
        //DiskCacheStrategy.SOURCE:缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }
}
