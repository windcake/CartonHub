package com.windcake.cartonhub.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.windcake.cartonhub.R;

import java.util.List;

/**
 * Created by windcake on 16/6/8.
 */
public class ImagePagerAdapter extends PagerAdapter
{
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public ImagePagerAdapter(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = inflater.inflate(R.layout.item_image_viewpager,container,false);

        ImageView imageView_item_image_viewpager  = (ImageView) view.findViewById(R.id.imageView_item_image_viewpager);

        Picasso.with(context).load(list.get(position)).into(imageView_item_image_viewpager);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
