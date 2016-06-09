package com.windcake.cartonhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.windcake.cartonhub.Adapter.ImagePagerAdapter;
import com.windcake.cartonhub.Constant;
import com.windcake.cartonhub.Helper.OkHttpClientHelper;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.AddressBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity
{
    private Context mContext = this;
    private String chapterId;
    private Handler handler = new Handler();
    private List<String> list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager_image;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        chapterId = intent.getStringExtra("chapterId");

        initData();

    }


    private void initData()
    {

        OkHttpClientHelper.getDataAsync(mContext,
                                        String.format(Constant.URL_ADDRESS,
                                                      chapterId),
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

                                                        AddressBean beans = jsonStringToBeans(
                                                                jsonString);

                                                        list = beans.getData()
                                                                    .getAddrs();


                                                        runOnUiThread(new Runnable()
                                                        {
                                                            @Override
                                                            public void run()
                                                            {
                                                                initViewPager();
                                                            }
                                                        });


                                                    }
                                                }
                                            }
                                        }, "chapterList");
    }




    private void initViewPager()
    {
        viewPager_image = (ViewPager) findViewById(R.id.viewPager_image);
        ImagePagerAdapter adapter = new ImagePagerAdapter(mContext,list);
        viewPager_image.setAdapter(adapter);

    }


    private AddressBean jsonStringToBeans(String string_json)
    {
        Gson gson = new Gson();
        AddressBean bean = gson.fromJson(string_json, new TypeToken<AddressBean>()
        {
        }.getType());
        return bean;
    }

}
