package com.example.sxj52.meizhigilr.view.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.adpter.GanHuoAdpter;
import com.example.sxj52.meizhigilr.adpter.MeiZhiAdpter;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.example.sxj52.meizhigilr.retrofit.GankRetrofit;
import com.example.sxj52.meizhigilr.retrofit.GankService;
import com.example.sxj52.meizhigilr.util.RecyclerItemClickListener;
import com.example.sxj52.meizhigilr.view.activity.GanHuoActivity;
import com.example.sxj52.meizhigilr.view.activity.MeiZhiActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnLoadMoreListener{
    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<GanHuo.Result> ganHuoList;
    private GanHuoAdpter ganHuoAdapter;
    private MeiZhiAdpter meiZhiAdapter;

    private boolean isNetWork = true;
    private String title;
    private int page = 1;
    private Handler handler = new Handler();
    //iOSList;androidList;welfareList;videoList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
    }
    public static MainFragment getInstance(String title){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ganHuoList = new ArrayList<>();
        noWIFILayout = (LinearLayout) view.findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),onItemClickListener));
        if (title.equals("福利")){
            final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    staggeredGridLayoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
                }
            });

            meiZhiAdapter = new MeiZhiAdpter(getContext());

            dealWithAdapter(meiZhiAdapter);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ganHuoAdapter = new GanHuoAdpter(getContext());
            //recyclerView.setAdapterWithProgress(ganHuoAdapter);
            dealWithAdapter(ganHuoAdapter);
        }

        recyclerView.setRefreshListener(this);
        onRefresh();
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent;
            if (title.equals("福利")){
                intent = new Intent(getContext(), MeiZhiActivity.class);
                intent.putExtra("desc",meiZhiAdapter.getItem(position).getDesc());
                intent.putExtra("url",meiZhiAdapter.getItem(position).getUrl());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.image), "sunzxyong");
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }else {
                intent = new Intent(getContext(), GanHuoActivity.class);
                intent.putExtra("desc",ganHuoAdapter.getItem(position).getDesc());
                intent.putExtra("url",ganHuoAdapter.getItem(position).getUrl());
                startActivity(intent);
            }


        }
    };

    private void dealWithAdapter( final RecyclerArrayAdapter<GanHuo.Result> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
    }
    private void getData(String type,int count,int page) {
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGanHuo(type,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuo>() {
                    @Override
                    public void onCompleted() {
                        Log.e("666","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                        Snackbar.make(recyclerView,"NO WIFI，不能愉快的看妹纸啦..",Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        ganHuoList = ganHuo.getResults();
                        if (title.equals("福利")){
                            meiZhiAdapter.addAll(ganHuoList);
                        }else {
                            ganHuoAdapter.addAll(ganHuoList);
                        }
                    }
                });
    }


    @Override
    public void onLoadMore() {
         handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")){
                    getData("福利",20,page);
                }else if (title.equals("Android")){
                    getData("Android",20,page);
                }else if (title.equals("iOS")){
                    getData("iOS",20,page);
                }
                else if (title.equals("休息视频")){
                    getData("休息视频",20,page);
                }
                page++;
            }
        }, 1000);

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")){
                    meiZhiAdapter.clear();
                    getData("福利",20,1);
                }else{
                    ganHuoAdapter.clear();
                    if (title.equals("Android")){
                        getData("Android",20,1);
                    }else if (title.equals("iOS")){
                        getData("iOS",20,1);
                    }
                    else if (title.equals("休息视频")){
                        getData("休息视频",20,1);
                    }
                }
                page = 2;
            }
        }, 1000);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
