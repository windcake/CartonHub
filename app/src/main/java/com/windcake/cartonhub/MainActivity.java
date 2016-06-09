package com.windcake.cartonhub;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.DbUtils;
import com.windcake.cartonhub.Adapter.MainFragmentPagerAdapter;
import com.windcake.cartonhub.Fragment.ClassifyFragment;
import com.windcake.cartonhub.Fragment.MoreFragment;
import com.windcake.cartonhub.Fragment.RecommendFragment;
import com.windcake.cartonhub.Fragment.ShelfFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{

    private Context mContext = this;
    private RadioGroup radioGroup_tab;
    private RadioButton[] radioButtons;
    private Fragment fragment_recommend;
    private Fragment fragment_shelf;
    private Fragment fragment_classify;
    private Fragment fragment_more;
    private List<Fragment> totalList = new ArrayList<>();
    public NonScrollViewPager viewPager_main;
    public static DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbUtils = DbUtils.create(this,"collect.db");

        initView();
        initButton();
        initFragment();
        initViewPager();
    }

    private void initFragment()
    {
//       把每个Fragment实例化，加入 totalList里，等待传值给ViewPager的适配器
        fragment_recommend = RecommendFragment.newInstance();
        fragment_shelf = ShelfFragment.newInstance("shelf");
        fragment_classify = ClassifyFragment.newInstance("classify");
        fragment_more = MoreFragment.newInstance("more");
        totalList.add(fragment_recommend);
        totalList.add(fragment_shelf);
        totalList.add(fragment_classify);
        totalList.add(fragment_more);
    }

    private void initViewPager()
    {
//        初始化ViewPager，加上适配器
        viewPager_main = (NonScrollViewPager) findViewById(R.id.viewPager_main);
        FragmentManager manager =getSupportFragmentManager();
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(manager , totalList);
        viewPager_main.setAdapter(adapter);

    }


    private void initView()
    {
        radioButtons = new RadioButton[4];

        radioGroup_tab = (RadioGroup) findViewById(R.id.radioGroup_tab);
        radioButtons[0] = (RadioButton) findViewById(R.id.radioButton_recommend);
        radioButtons[1]  = (RadioButton) findViewById(R.id.radioButton_shelf);
        radioButtons[2] = (RadioButton) findViewById(R.id.radioButton_classify);
        radioButtons[3] = (RadioButton) findViewById(R.id.radioButton_more);




    }

    private void initButton()
    {
        /*
        给Group加上选中状态改变监听器
        单击一个Button设置这个Button选中并让viewPager跳到这一页
          */
        radioGroup_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                for (int i = 0; i < radioButtons.length; i++)
                {
                    radioButtons[i].setChecked(false);
                    if (radioButtons[i].getId() == checkedId)
                    {
                        radioButtons[i].setChecked(true);
                        viewPager_main.setCurrentItem(i);
                    }
                }
            }
        });


    }



}
