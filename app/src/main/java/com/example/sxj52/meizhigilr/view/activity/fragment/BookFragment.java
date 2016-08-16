package com.example.sxj52.meizhigilr.view.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.retrofit.Book;
import com.example.sxj52.meizhigilr.retrofit.BookAPi;
import com.example.sxj52.meizhigilr.retrofit.BookRetrofit;
import com.example.sxj52.meizhigilr.retrofit.BookServer;
import com.example.sxj52.meizhigilr.retrofit.MyAdapter;
import com.example.sxj52.meizhigilr.view.activity.BookDetailActivity;
import com.example.sxj52.meizhigilr.util.RecyclerItemClickListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BookFragment extends Fragment
       {
    private List<Book> mBookList=new ArrayList<Book>();
    private String title;
           private RecyclerView mRecyclerView;
           private MyAdapter mAdapter;
           private LinearLayout noWIFILayout;
           private FloatingActionButton mFabButton;
           private AVLoadingIndicatorView mProgressBar;
           private static final int ANIM_DURATION_FAB = 400;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
    }
    public static BookFragment getInstance(String title){
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bookFragment.setArguments(bundle);
        return bookFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        initView(view);
        return view;
    }

           @Override
           public void onActivityCreated(@Nullable Bundle savedInstanceState) {
               super.onActivityCreated(savedInstanceState);
               getData("小王子",0,50);
           }

           private void setUpFAB(View view) {
               mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal);
               mFabButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       new MaterialDialog.Builder(getActivity())
                               .title(R.string.search)
                               //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                               .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                   @Override
                                   public void onInput(MaterialDialog dialog, CharSequence input) {
                                       // Do something
                                       if (!TextUtils.isEmpty(input)) {
                                           getData(input.toString(),0,50);
                                       }
                                   }
                               }).show();
                   }
               });
           }


           private void startFABAnimation() {
               mFabButton.animate()
                       .translationY(0)
                       .setInterpolator(new OvershootInterpolator(1.f))
                       .setStartDelay(500)
                       .setDuration(ANIM_DURATION_FAB)
                       .start();
           }
    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noWIFILayout= (LinearLayout) view.findViewById(R.id.no_network);
        mProgressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progress);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));

        mAdapter = new MyAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        setUpFAB(view);
    }
           private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
               @Override
               public void onItemClick(View view, int position) {
                   Book book = mAdapter.getBook(position);
                   Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                   intent.putExtra("book", book);
                  startActivity(intent);

               }
           };
    private void getData(String type, int count, final int page) {
                mProgressBar.setVisibility(View.VISIBLE);
                mAdapter.clearItems();
                BookRetrofit.getRetrofit(getContext()).
                        create(BookServer.class)
                        .getBookInfo(type, "",count, page)
                        .map(new Func1<BookAPi, Book>() {
                            @Override
                            public Book call(BookAPi bookAPi) {
                                mBookList=bookAPi.mHeWeatherDataService30s;
                                return bookAPi.mHeWeatherDataService30s.get(0);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Book>() {
                            @Override
                            public void onCompleted() {
                                if(mBookList!=null){
                                    noWIFILayout.setVisibility(View.GONE);
                                }
                                mProgressBar.setVisibility(View.GONE);

                                mAdapter.updateItems(mBookList,true);
                                setUpFAB(getView());
                                Snackbar.make(getView(),"加载完成",Snackbar.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(Throwable e) {
                                noWIFILayout.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onNext(Book ganHuo) {
                                mBookList.add(ganHuo);
                            }
                        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
