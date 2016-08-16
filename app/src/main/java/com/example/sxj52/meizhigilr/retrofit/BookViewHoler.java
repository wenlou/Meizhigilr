package com.example.sxj52.meizhigilr.retrofit;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sxj52.meizhigilr.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by sxj52 on 2016/8/6.
 */
public class BookViewHoler extends BaseViewHolder<Book> {
    private ImageView ivBook;
    private TextView tvTitle;
    private  TextView tvDesc;
    public BookViewHoler(ViewGroup parent) {
        super(parent, R.layout.book_item);
        ivBook = $(R.id.ivBook);
        tvTitle=$(R.id.tvTitle);
        tvDesc=$(R.id.tvDesc);
        Log.e("5555","333333");
    }

    @Override
    public void setData(Book data) {
        super.setData(data);
        tvTitle.setText(data.getTitle());
        String desc = "作者: " + data.getAuthor()[0]+ "\n副标题: " + data.getSubtitle()
                + "\n出版年: " + data.getPubdate() + "\n页数: " + data.getPages() + "\n定价:" + data.getPrice();
        tvDesc.setText(desc);
        Log.e("5555",desc);
        Glide.with(getContext())
                //地址
                .load(data.getImage())
                //DiskCacheStrategy.SOURCE:缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivBook);
    }
}
