package com.example.sxj52.meizhigilr.retrofit;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sxj52 on 2016/8/4.
 */
public interface BookServer {
    String HOST = "https://api.douban.com/v2/";
    public static String readEBook="http://read.douban.com/reader/ebook/";
    @GET("book/search")
    Observable<BookAPi> getBookInfo(@Query("q") String name,
                                     @Query("tag") String tag, @Query("start") int start,
                                     @Query("count") int count);
}
