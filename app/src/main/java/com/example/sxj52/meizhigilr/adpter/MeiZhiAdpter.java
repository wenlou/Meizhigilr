package com.example.sxj52.meizhigilr.adpter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.sxj52.meizhigilr.model.GanHuo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by sxj52 on 2016/7/25.
 */
public class MeiZhiAdpter extends RecyclerArrayAdapter<GanHuo.Result> {
    public MeiZhiAdpter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeiZhiViewHolder(parent);
    }
}
