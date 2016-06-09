package com.windcake.cartonhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;
import com.windcake.cartonhub.Constant;
import com.windcake.cartonhub.Helper.OkHttpClientHelper;
import com.windcake.cartonhub.MainActivity;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.CollectBean;
import com.windcake.cartonhub.beans.DetailBean;
import com.windcake.cartonhub.beans.HotBean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity
{
    private Context context = this;

    private ImageView imageView_detail;
    private TextView textView_detail_title;
    private TextView textView_detail_author;
    private TextView textView_detail_area;
    private TextView textView_detail_type;
    private TextView textView_detail_scroll_intro;

    private String url;
    private String title;
    private String author;
    private String area;
    private String type;
    private String intro;


    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    List<DetailBean.DataBean.ComicSrcBean> comicSrcBeanList;
    private ListView listView_detail;

    private DetailBean.DataBean dataBean;

    private String comicId;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        comicId = intent.getStringExtra("comicId");

        initView();
        initData();
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.textView_detail_collect:
                try
                {
                    CollectBean collectBean = new CollectBean(url,
                                                              title,
                                                              author,
                                                              area,
                                                              type,
                                                              intro);
                    MainActivity.dbUtils.save(collectBean);
                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT)
                         .show();

                } catch (DbException e)
                {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void initView()
    {
        imageView_detail = (ImageView) findViewById(R.id.imageView_detail);
        textView_detail_title = (TextView) findViewById(R.id.textView_detail_title);
        textView_detail_author = (TextView) findViewById(R.id.textView_detail_author);
        textView_detail_area = (TextView) findViewById(R.id.textView_detail_area);
        textView_detail_type = (TextView) findViewById(R.id.textView_detail_type);
        textView_detail_scroll_intro = (TextView) findViewById(R.id.textView_detail_scroll_intro);
        listView_detail = (ListView) findViewById(R.id.listView_detail);


        listView_detail.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                DetailBean.DataBean.ComicSrcBean comicSrcBean = comicSrcBeanList.get(position);

                String comicId = dataBean.getComicId() + "";
                String comicSrcId = comicSrcBean.getId();

                Intent intent = new Intent();
                intent.putExtra("comicId", comicId);
                intent.putExtra("comicSrcId", comicSrcId);
                intent.setClass(context, ChapterListActivity.class);
                startActivity(intent);


            }
        });


    }


    private void initData()
    {
        OkHttpClientHelper.getDataAsync(context,
                                        String.format(Constant.URL_DETAIL, comicId),
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
                                                        Toast.makeText(context,
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

                                                        DetailBean beans = jsonStringToBeans(
                                                                jsonString);
                                                        dataBean = beans.getData();

                                                        runOnUiThread(new Runnable()
                                                        {
                                                            @Override
                                                            public void run()
                                                            {
                                                                url = dataBean.getThumb();
                                                                title = dataBean.getTitle();
                                                                author = dataBean.getAuthorName();
                                                                area = dataBean.getAreaName();
                                                                type = dataBean.getComicType();
                                                                intro = dataBean.getIntro();


                                                                textView_detail_title.setText(title);
                                                                textView_detail_author.setText(
                                                                        author);
                                                                textView_detail_area.setText(area);
                                                                textView_detail_type.setText(type);
                                                                textView_detail_scroll_intro.setText(
                                                                        intro);
//                                                               更新simpleAdapter的数据源
                                                                comicSrcBeanList = dataBean.getComicSrc();
                                                                DateFormat format = new SimpleDateFormat(
                                                                        "yyyy-MM-dd");
                                                                for (int i = 0; i < comicSrcBeanList.size(); i++)
                                                                {
                                                                    DetailBean.DataBean.ComicSrcBean comicSrcBean = comicSrcBeanList.get(
                                                                            i);
                                                                    Map<String, Object> map = new HashMap<String, Object>();
                                                                    map.put("sourceTitle",
                                                                            comicSrcBean.getTitle());
                                                                    map.put("lastChapterTitle",
                                                                            comicSrcBean.getLastCharpterTitle());
//                                                                  时间戳转换成正常时间
                                                                    long time = Long.valueOf(
                                                                            comicSrcBean.getLastCharpterUpdateTime());
                                                                    map.put("lastChapterUpdateTime",
                                                                            format.format(new Date(
                                                                                    time * 1000L)));
                                                                    list.add(map);

                                                                }

                                                                Picasso.with(context)
                                                                       .load(url)
                                                                       .into(imageView_detail);

                                                                SimpleAdapter adapter = new SimpleAdapter(
                                                                        context,
                                                                        list,
                                                                        R.layout.item_simpleadapter_detailactivity,
                                                                        new String[]{"sourceTitle", "lastChapterTitle", "lastChapterUpdateTime"},
                                                                        new int[]{R.id.textView_item_detail_sourceTitle, R.id.textView_item_detail_lastChapterTitle, R.id.textView_item_detail_lastChapterUpdateTime}
                                                                );

                                                                listView_detail.setAdapter(adapter);


                                                            }
                                                        });

                                                    }
                                                }
                                            }
                                        }, "detail");
    }


    private DetailBean jsonStringToBeans(String string_json)
    {
        Gson gson = new Gson();
        DetailBean bean = gson.fromJson(string_json, new TypeToken<DetailBean>()
        {
        }.getType());
        return bean;
    }


}
