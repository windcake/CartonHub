package com.windcake.cartonhub;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by windcake on 16/6/6.
 */
public class NonScrollViewPager extends ViewPager
{
    private boolean noScroll = true;

    public NonScrollViewPager(Context context)
    {
        super(context);
    }

    public NonScrollViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll)
    {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y)
    {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (noScroll)
        {
            return false;
        } else
        {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (noScroll)
            return false;
        else
        {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll)
    {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item)
    {
        super.setCurrentItem(item);
    }
}
