package com.windcake.cartonhub.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.windcake.cartonhub.Constant;
import com.windcake.cartonhub.R;

public class MoreFragment extends Fragment
{
    private WebView webView_fragment_more = null;


    public MoreFragment()
    {
    }


    public static MoreFragment newInstance(String param1)
    {
        MoreFragment fragment = new MoreFragment();
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

        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        webView_fragment_more = (WebView) getActivity().findViewById(R.id.webView_fragment_more);

        initWebView();
    }

    private void initWebView() {
        //让WebView充当url打开的客户端，避免打开浏览器软件
        webView_fragment_more.setWebViewClient(new WebViewClient());
        //让WebView支持alert这些特殊的javascript语句
        webView_fragment_more.setWebChromeClient(new WebChromeClient());
        //让WebView支持普通的JavaScript语句
        webView_fragment_more.getSettings().setJavaScriptEnabled(true);
        //加载url所对应的数据
        webView_fragment_more.loadUrl(Constant.URL_BILIBILI);
    }


}
