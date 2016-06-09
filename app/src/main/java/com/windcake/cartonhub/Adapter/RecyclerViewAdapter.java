package com.windcake.cartonhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.windcake.cartonhub.R;
import com.windcake.cartonhub.beans.HotBean;

import java.util.List;

/**
 * Created by windcake on 16/6/7.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context = null;
    private List<HotBean.DataBean> list;
    private LayoutInflater inflater = null;
    private OnItemClickedListener listener;
    private RecyclerView recyclerView;
    //  1热门 推荐 精彩 2 最近更新 3 分类
    private static final int STATE1 = 1, STATE2 = 2, STATE3 = 3;


    public interface OnItemClickedListener
    {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener _listener)
    {
        this.listener = _listener;
    }

    public RecyclerViewAdapter(Context context,
                               List<HotBean.DataBean> list, RecyclerView recyclerView)
    {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position)
    {
//       这个判断类型的方法不好，应该在构造器里传入一个标志来判断。
        String author = list.get(position)
                            .getAuthorName();

        String comicID = list.get(position).getComicId();

            if (comicID == null)
            {
                return STATE3;
            }else if (author ==null)
            {
                return STATE1;
            }

        return STATE2;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        switch (viewType)
        {
            case STATE1:
                view = inflater.inflate(R.layout.item_recyclerview_hotfragment, parent, false);
                return new ViewHolder1(view);
            case STATE2:
                view = inflater.inflate(R.layout.item_recyclerview_recentfragment, parent, false);
                return new ViewHolder2(view);

            case STATE3:
                view = inflater.inflate(R.layout.item_recycleview_classifyfragment, parent, false);
                return new ViewHolder3(view);


        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder1)
        {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.textView_recycler.setText(list.get(position)
                                                      .getTitle());
            Picasso.with(context)
                   .load(list.get(position)
                             .getThumb())
                   .into(viewHolder1.imageView_recycler);
        } else if (holder instanceof ViewHolder2)
        {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            String title = list.get(position)
                               .getTitle();
            String author = list.get(position)
                                .getAuthorName();
            String type = list.get(position)
                              .getComicType();
            String last = list.get(position)
                              .getLastCharpter()
                              .getTitle();
            String url = list.get(position)
                             .getThumb();
            Log.i("aaaa", "---->>" + title);

            viewHolder2.textView_item_recycler_title.setText(title);
            viewHolder2.textView_item_recycler_author.setText(author);
            viewHolder2.textView_item_recycler_type.setText(type);
            viewHolder2.textView_item_recycler_last.setText(last);

            Picasso.with(context)
                   .load(url)
                   .into(viewHolder2.imageView_item_recycler);

        } else if (holder instanceof ViewHolder3)
        {
            ViewHolder3 viewHolder3 = (ViewHolder3) holder;
            String url = list.get(position)
                             .getThumb();
            String title = list.get(position)
                               .getTitle();

            viewHolder3.textView_item_recycler_classify.setText(title);
            Picasso.with(context)
                   .load(url)
                   .into(viewHolder3.roundImageView_item_recycler_classify);
        }


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public void reloadListView(List<HotBean.DataBean> _list, boolean isClear)
    {
        if (isClear)
        {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }

    //       热门 推荐 精彩 ViewHolder
    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener
    {

        private ImageView imageView_recycler;
        private TextView textView_recycler;

        public ViewHolder1(View convertView)
        {
            super(convertView);

            imageView_recycler = (ImageView) convertView.findViewById(R.id.imageView_recycler);
            textView_recycler = (TextView) convertView.findViewById(R.id.textView_recycler);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                return listener.onItemLongClick(position);
            }
            return false;
        }
    }


    class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener
    {

        private ImageView imageView_item_recycler;
        private TextView textView_item_recycler_title;
        private TextView textView_item_recycler_author;
        private TextView textView_item_recycler_type;
        private TextView textView_item_recycler_last;

        //       最近更新的ViewHolder
        public ViewHolder2(View convertView)
        {
            super(convertView);

            imageView_item_recycler = (ImageView) convertView.findViewById(R.id.imageView_item_recycler);
            textView_item_recycler_author = (TextView) convertView.findViewById(R.id.textView_item_recycler_author);
            textView_item_recycler_type = (TextView) convertView.findViewById(R.id.textView_item_recycler_type);
            textView_item_recycler_last = (TextView) convertView.findViewById(R.id.textView_item_recycler_last);
            textView_item_recycler_title = (TextView) convertView.findViewById(R.id.textView_item_recycler_title);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                return listener.onItemLongClick(position);
            }
            return false;
        }
    }

    //   分类页面的ViewHolder
    class ViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener
    {

        private RoundedImageView roundImageView_item_recycler_classify;
        private TextView textView_item_recycler_classify;

        public ViewHolder3(View convertView)
        {
            super(convertView);

            roundImageView_item_recycler_classify = (RoundedImageView) convertView.findViewById(R.id.roundImageView_item_recycler_classify);
            textView_item_recycler_classify = (TextView) convertView.findViewById(R.id.textView_item_recycler_classify);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v)
        {
            if (listener != null)
            {
                int position = recyclerView.getChildAdapterPosition(v);
                return listener.onItemLongClick(position);
            }
            return false;
        }
    }

}
