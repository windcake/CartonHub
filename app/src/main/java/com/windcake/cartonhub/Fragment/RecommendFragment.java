package com.windcake.cartonhub.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windcake.cartonhub.Adapter.RecomFragmentAdapter;
import com.windcake.cartonhub.Fragment.RecomSubFragment.EditorRecomFragment;
import com.windcake.cartonhub.Fragment.RecomSubFragment.FancyFragment;
import com.windcake.cartonhub.Fragment.RecomSubFragment.HotFragment;
import com.windcake.cartonhub.Fragment.RecomSubFragment.RecentFragment;
import com.windcake.cartonhub.R;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment
{
    private TabLayout tabLayout_recomfagment;
    private ViewPager viewPager_recomfagment;
    private List<Fragment> totalList = new ArrayList<>();
    private Fragment fragment_hot;
    private Fragment fragment_editorRecom;
    private Fragment fragment_fancy;
    private Fragment fragment_recent;


    public RecommendFragment()
{
}


    public static RecommendFragment newInstance()
    {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initFragment();




    }

    private void initFragment()
    {
        fragment_hot = HotFragment.newInstance("hot");
        fragment_editorRecom = EditorRecomFragment.newInstance("editor");
        fragment_fancy = FancyFragment.newInstance("fancy");
        fragment_recent = RecentFragment.newInstance("recent");
        totalList.add(fragment_hot);
        totalList.add(fragment_editorRecom);
        totalList.add(fragment_fancy);
        totalList.add(fragment_recent);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView()
    {
        tabLayout_recomfagment = (TabLayout) getActivity().findViewById(R.id.tabLayout_recomfagment);
        viewPager_recomfagment = (ViewPager) getActivity().findViewById(R.id.viewPager_recomfragment);

        String[] TabTitles = getResources().getStringArray(R.array.TabTitles);
//      嵌套的fragment 管理器不一样 getChildFragmentManager
        viewPager_recomfagment.setAdapter(new RecomFragmentAdapter(getChildFragmentManager(),
                                                                   totalList, TabTitles));
        tabLayout_recomfagment.setupWithViewPager(viewPager_recomfagment);

    }

}
