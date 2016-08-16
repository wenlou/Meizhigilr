package com.example.sxj52.meizhigilr.retrofit;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by sxj52 on 2016/8/6.
 */
public class BookAdpter extends RecyclerArrayAdapter<Book> {


    public BookAdpter(Context context) {
        super(context);
        Log.e("6666","333333");
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("7777","333333");
        return new BookViewHoler(parent);
    }
}
