package com.windcake.cartonhub.Fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.windcake.cartonhub.Activity.CategoryListActivity;
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

import in.srain.cube.views.ptr.PtrFrameLayout;

public class ClassifyFragment extends Fragment
{


    private Handler handler = new Handler();
    private List<HotBean.DataBean> totalList = new ArrayList<>();
    private RecyclerView recyclerView_classifyfragment;
    private RecyclerViewAdapter adapter;


    public ClassifyFragment()
    {
    }

    public static ClassifyFragment newInstance(String param1)
    {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle args = new Bundle();
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

//      加载布局
        return inflater.inflate(R.layout.fragment_classify, container, false);
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
                                        Constant.URL_CLASSIFY,
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
                                                        totalList = beans.getData();

                                                        handler.post(new Runnable()
                                                        {
                                                            @Override
                                                            public void run()
                                                            {
                                                                adapter.reloadListView(totalList,false);
                                                            }
                                                        });

                                                    }
                                                }
                                            }
                                        }, "classify");
    }


    private void initView()
    {
        recyclerView_classifyfragment = (RecyclerView) getActivity().findViewById(R.id.recyclerView_classifyfragment);
        recyclerView_classifyfragment.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView_classifyfragment.setLayoutManager(gridLayoutManager);
        recyclerView_classifyfragment.addItemDecoration(new DividerItemDecoration(getContext(),
                                                                                  DividerItemDecoration.VERTICAL_LIST));


        adapter = new RecyclerViewAdapter(getContext(), totalList, recyclerView_classifyfragment);
        recyclerView_classifyfragment.setAdapter(adapter);
        recyclerView_classifyfragment.setItemAnimator(new DefaultItemAnimator());



        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickedListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Intent intent = new Intent();
                int cateId = totalList.get(position).getCateId();
                intent.putExtra("cateId",cateId);
                intent.setClass(getContext(), CategoryListActivity.class);
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(int position)
            {
                return false;
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
        OkHttpClientHelper.cancelCall("classify");
    }
}
