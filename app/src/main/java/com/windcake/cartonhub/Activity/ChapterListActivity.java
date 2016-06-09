package com.windcake.cartonhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.windcake.cartonhub.Constant;
import com.windcake.cartonhub.Helper.OkHttpClientHelper;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.ChapterListBean;
import com.windcake.cartonhub.beans.DetailBean;
import com.windcake.cartonhub.beans.HotBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity
{
    private Context mContext = this;
    private String comicId;
    private String comicSrcId;
    private List<ChapterListBean.DataBean> dataBeanList = new ArrayList<>();
    private ListView listView_chapter_list;
    private List<String> chapterTitleList = new ArrayList<>();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        Intent intent = getIntent();
        comicId = intent.getStringExtra("comicId");
        comicSrcId = intent.getStringExtra("comicSrcId");

        initView();
        initData();
    }

    private void initView()
    {
        listView_chapter_list = (ListView) findViewById(R.id.listView_chapter_list);

        listView_chapter_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String chapterId = dataBeanList.get(position).getId()+"";
                Intent intent = new Intent();
                intent.putExtra("chapterId",chapterId);
                intent.setClass(mContext,ImageActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initData()
    {

        OkHttpClientHelper.getDataAsync(mContext,
                                        String.format(Constant.URL_CHAPTER_LIST,
                                                      comicSrcId,
                                                      comicId),
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

                                                        ChapterListBean beans = jsonStringToBeans(
                                                                jsonString);
                                                        dataBeanList = beans.getData();
//                                                      拿到章节title的list数据源，为ArrayAdapter做准备
                                                        for (int i = 0; i < dataBeanList.size(); i++)
                                                        {
                                                            chapterTitleList.add(dataBeanList.get(i).getTitle());
                                                        }

                                                        runOnUiThread(new Runnable()
                                                        {
                                                            @Override
                                                            public void run()
                                                            {
                                                                ArrayAdapter adapter = new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,chapterTitleList);
                                                                listView_chapter_list.setAdapter(adapter);
                                                            }
                                                        });



                                                    }
                                                }
                                            }
                                        }, "chapterList");
    }


    private ChapterListBean jsonStringToBeans(String string_json)
    {
        Gson gson = new Gson();
        ChapterListBean bean = gson.fromJson(string_json, new TypeToken<ChapterListBean>()
        {
        }.getType());
        return bean;
    }

}
