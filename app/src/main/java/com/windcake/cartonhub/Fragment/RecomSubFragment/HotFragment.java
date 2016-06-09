package com.windcake.cartonhub.Fragment.RecomSubFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.windcake.cartonhub.Activity.DetailActivity;
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


public class HotFragment extends Fragment
{

    private static final String ARG_PARAM1 = "param1";

    private GridView gridView_hotfragment;
    private ImageView imageView_hotfragment;
    private RecyclerView recyclerView_hotfragment;
    private PtrFrameLayout ptrFrameLayout_recycler;
    private RecyclerViewAdapter adapter;
    private int currentPage = 1;
    private int lastVisibleItem = 0;
    private Handler handler = new Handler();
    private List<HotBean.DataBean> totalList = new ArrayList<>();

    public HotFragment()
    {
    }


    public static HotFragment newInstance(String param1)
    {
        HotFragment fragment = new HotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sub_hot, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initView();


    }

    @Override
    public void onStart()
    {
        super.onStart();
        initData();
    }

    private void initData()
    {
        OkHttpClientHelper.getDataAsync(getContext(),
                                        String.format(Constant.URL_HOT, currentPage),
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
                                                        Toast.makeText(getContext(),
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
//                                                                  让 totalList和adapter数据保持一致。
                                                                    totalList.clear();
                                                                    totalList.addAll(list);
                                                                } else {
                                                                    adapter.reloadListView(list, false);
                                                                    totalList.addAll(list);
                                                                }
                                                                // 刷新完成，让刷新Loading消失
                                                                ptrFrameLayout_recycler.refreshComplete();
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }, "hot");
    }

    private void initView()
    {
//        imageView_hotfragment = (ImageView) getActivity().findViewById(R.id.imageView_hotfragment);
        recyclerView_hotfragment = (RecyclerView) getActivity().findViewById(R.id.recyclerView_hotfragment);
        ptrFrameLayout_recycler = (PtrFrameLayout) getActivity().findViewById(R.id.ptrFrameLayout_hotfragment);
        recyclerView_hotfragment.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView_hotfragment.setLayoutManager(gridLayoutManager);
        recyclerView_hotfragment.addItemDecoration(new DividerItemDecoration(getContext(),
                                                                             DividerItemDecoration.VERTICAL_LIST));


        adapter = new RecyclerViewAdapter(getContext(), totalList,recyclerView_hotfragment);
        recyclerView_hotfragment.setAdapter(adapter);
        recyclerView_hotfragment.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickedListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Intent intent = new Intent();
                String comicId = totalList.get(position).getComicId();
                intent.putExtra("comicId",comicId);
                intent.setClass(getContext(), DetailActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int position)
            {
                return false;
            }
        });

        //利用RecyclerView的滚动监听实现上拉加载下一页


        recyclerView_hotfragment.addOnScrollListener(new RecyclerView.OnScrollListener()
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
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }

        });

        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头标
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getContext());


        //效果2：特殊效果，目前只支持英文字符（闪动的文字Header：闪动文字效果的header）
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getContext());
        //storeHouseHeader.setPadding(0, 30, 0, 0);
        storeHouseHeader.setBackgroundColor(Color.BLACK);
        storeHouseHeader.setTextColor(Color.WHITE);
        // 文字只能是0-9,a-z不支持中文
        storeHouseHeader.initWithString("loading...");

        //设置头视图
        ptrFrameLayout_recycler.setHeaderView(storeHouseHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_recycler.addPtrUIHandler(storeHouseHeader);

        // 添加刷新动作监听
        ptrFrameLayout_recycler.setPtrHandler(new PtrDefaultHandler()
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

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        OkHttpClientHelper.cancelCall("hot");
    }
}
