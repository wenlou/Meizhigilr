package com.example.sxj52.meizhigilr.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxj52 on 2016/8/6.
 */
public class BookAPi {
    @SerializedName("books") @Expose
    public List<Book> mHeWeatherDataService30s
            = new ArrayList<>();
}
