package com.example.sxj52.meizhigilr.adpter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by sxj52 on 2016/5/27.
 */
public class MeizhiViewHolder extends BaseViewHolder<GanHuo.Result> {
    private ImageView imageView;

    public MeizhiViewHolder(ViewGroup parent) {
       super(parent, R.layout.meizhi_item);
        imageView=$(R.id.image);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        Glide.with(getContext())
                //地址
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
