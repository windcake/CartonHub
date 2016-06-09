package com.windcake.cartonhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.windcake.cartonhub.Adapter.RecyclerViewAdapter;
import com.windcake.cartonhub.Constant;
import com.windcake.cartonhub.Helper.OkHttpClientHelper;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.HotBean;
import com.windcake.cartonhub.decoration.DividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class CategoryListActivity extends AppCompatActivity
{
    private Context mContext = this;

    private RecyclerView recyclerView_category_activity;
    private PtrFrameLayout ptrFrameLayout_category_activity;
    private RecyclerViewAdapter adapter;
    private int currentPage = 1;
    private int lastVisibleItem = 0;
    private Handler handler = new Handler();
    //  因为精彩港漫和热门的json结构一样，所以使用热门泛型，并重用RecyclerViewAdapter
    private List<HotBean.DataBean> totalList = new ArrayList<>();

    private int cateId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Intent intent = getIntent();
        cateId = intent.getIntExtra("cateId", 0);

        initView();
        initData();

    }


    private void initData()
    {
        OkHttpClientHelper.getDataAsync(mContext,
                                        String.format(Constant.URL_CLASSIFY_LIST,cateId,currentPage),
                                        new Callback()
                                        {
                                            @Override
                                            public void onFailure(Request request, IOException e)
                                            {
                                                handler.post(new Runnable()
                                                {
                                                    @Override
                                                    public void run()
                                                    {
                                                        Toast.makeText(mContext,
                                                                       "网络加载异常",
                                                                       Toast.LENGTH_SHORT)
                                                             .show();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onResponse(Response response) throws IOException
                                            {

                                                if (response.isSuccessful())
                                                {
                                                    ResponseBody body = response.body();
                                                    if (body != null)
                                                    {
                                                        final String jsonString = body.string();


                                                        HotBean beans = jsonStringToBeans(jsonString);
                                                        final List<HotBean.DataBean> list = beans.getData();
                                                        handler.post(new Runnable()
                                                        {
                                                            @Override
                                                            public void run()
                                                            {
                                                                if (currentPage == 1) {
                                                                    adapter.reloadListView(list, true);
                                                                } else {
                                                                    adapter.reloadListView(list, false);
                                                                }
                                                                // 刷新完成，让刷新Loading消失
                                                                ptrFrameLayout_category_activity.refreshComplete();
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }, "hot");
    }

    private void initView()
    {
        recyclerView_category_activity = (RecyclerView) findViewById(R.id.recyclerView_category_activity);
        ptrFrameLayout_category_activity = (PtrFrameLayout) findViewById(R.id.ptrFrameLayout_category_activity);
        recyclerView_category_activity.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView_category_activity.setLayoutManager(linearLayoutManager);
        recyclerView_category_activity.addItemDecoration(new DividerItemDecoration(mContext,
                                                                                 DividerItemDecoration.VERTICAL_LIST));


        adapter = new RecyclerViewAdapter(mContext, totalList,recyclerView_category_activity);
        recyclerView_category_activity.setAdapter(adapter);
        recyclerView_category_activity.setItemAnimator(new DefaultItemAnimator());


        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickedListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Intent intent = new Intent();
                String comicId = totalList.get(position).getComicId();
                intent.putExtra("comicId",comicId);
                intent.setClass(mContext, DetailActivity.class);
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(int position)
            {
                return false;
            }
        });

        //利用RecyclerView的滚动监听实现上拉加载下一页


        recyclerView_category_activity.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == adapter.getItemCount() - 1)
                {
                    currentPage++;
                    initData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }

        });

        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头标
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(mContext);


        //效果2：特殊效果，目前只支持英文字符（闪动的文字Header：闪动文字效果的header）
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(mContext);
        //storeHouseHeader.setPadding(0, 30, 0, 0);
        storeHouseHeader.setBackgroundColor(Color.BLACK);
        storeHouseHeader.setTextColor(Color.WHITE);
        // 文字只能是0-9,a-z不支持中文
        storeHouseHeader.initWithString("loading...");

        //设置头视图
        ptrFrameLayout_category_activity.setHeaderView(storeHouseHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_category_activity.addPtrUIHandler(storeHouseHeader);

        // 添加刷新动作监听
        ptrFrameLayout_category_activity.setPtrHandler(new PtrDefaultHandler()
        {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame)
            {
                currentPage = 1;
                initData();
            }
        });


    }



    private HotBean jsonStringToBeans(String string_json)
    {
        Gson gson = new Gson();
        HotBean bean = gson.fromJson(string_json, new TypeToken<HotBean>()
        {
        }.getType());
        return bean;
    }





}
