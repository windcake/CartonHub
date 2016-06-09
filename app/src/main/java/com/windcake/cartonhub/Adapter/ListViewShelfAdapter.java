package com.windcake.cartonhub.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.CollectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windcake on 16/6/8.
 */
public class ListViewShelfAdapter extends BaseAdapter
{
    private Context context;
    private List<CollectBean> list = new ArrayList<>();
    private LayoutInflater mInflater;

    public ListViewShelfAdapter(Context context,
                                List<CollectBean> list)
    {
        this.context = context;
        this.list = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_listview_fragment_shelf,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView_item_listView_shelf_title.setText(list.get(position).getTitle());
        viewHolder.textView_item_listView_shelf_author.setText(list.get(position).getAuthor());
        viewHolder.textView_item_listView_shelf_type.setText(list.get(position).getType());

        Glide.with(context).load(list.get(position).getUrl()).into(viewHolder.imageView_item_listView_shelf);




        return convertView;
    }

    class ViewHolder
    {
        private ImageView imageView_item_listView_shelf;
        private TextView textView_item_listView_shelf_title;
        private TextView textView_item_listView_shelf_author;
        private TextView textView_item_listView_shelf_type;
        private TextView textView_item_listView_shelf_last;

        public ViewHolder(View view)
        {
            imageView_item_listView_shelf = (ImageView) view.findViewById(R.id.imageView_item_listView_shelf);
            textView_item_listView_shelf_title = (TextView) view.findViewById(R.id.textView_item_listView_shelf_title);
            textView_item_listView_shelf_author = (TextView) view.findViewById(R.id.textView_item_listView_shelf_author);
            textView_item_listView_shelf_type = (TextView) view.findViewById(R.id.textView_item_listView_shelf_type);
            textView_item_listView_shelf_last = (TextView) view.findViewById(R.id.textView_item_listView_shelf_last);

        }

    }


}
