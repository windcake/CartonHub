package com.windcake.cartonhub.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.exception.DbException;
import com.windcake.cartonhub.Adapter.ListViewShelfAdapter;
import com.windcake.cartonhub.MainActivity;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.CollectBean;

import java.util.ArrayList;
import java.util.List;

public class ShelfFragment extends Fragment
{

    private List<CollectBean> list = new ArrayList<>();
    private ListView listView;
    private ListViewShelfAdapter adapter;
    public ShelfFragment()
    {
    }


    public static ShelfFragment newInstance(String param1)
    {
        ShelfFragment fragment = new ShelfFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            list = MainActivity.dbUtils.findAll(CollectBean.class);
        } catch (DbException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_shelf, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.listView_fragment_shelf);
        adapter = new ListViewShelfAdapter(getContext(),list);
       listView.setAdapter(adapter);

    }

    @Override
    public void onStart()
    {
        super.onStart();


    }
}
