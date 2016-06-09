package com.windcake.cartonhub.beans;

import java.util.List;

/**
 * Created by windcake on 16/6/8.
 */
public class AddressBean
{

    /**
     * status : 0
     * data : {"charpterId":1935326,"title":"妖怪名单 266集","sid":266,"updateTime":1464687426,"counts":11,"size":1.65,"addrs":["http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0001_10607.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0002_14046.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0003_14527.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0004_54992.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0005_57010.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0006_16786.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0007_11813.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0008_18302.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0009_15524.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0010_37868.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0011_42904.JPG"],"surl":"","iszm":0}
     */

    private int status;
    /**
     * charpterId : 1935326
     * title : 妖怪名单 266集
     * sid : 266
     * updateTime : 1464687426
     * counts : 11
     * size : 1.65
     * addrs : ["http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0001_10607.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0002_14046.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0003_14527.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0004_54992.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0005_57010.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0006_16786.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0007_11813.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0008_18302.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0009_15524.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0010_37868.JPG","http://tkpic.tukucc.com/proxyh/dm03//ok-comic03/y/27874/act_266/z_0011_42904.JPG"]
     * surl :
     * iszm : 0
     */

    private DataBean data;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public static class DataBean
    {
        private int charpterId;
        private String title;
        private int sid;
        private int updateTime;
        private int counts;
        private double size;
        private String surl;
        private int iszm;
        private List<String> addrs;

        public int getCharpterId()
        {
            return charpterId;
        }

        public void setCharpterId(int charpterId)
        {
            this.charpterId = charpterId;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public int getSid()
        {
            return sid;
        }

        public void setSid(int sid)
        {
            this.sid = sid;
        }

        public int getUpdateTime()
        {
            return updateTime;
        }

        public void setUpdateTime(int updateTime)
        {
            this.updateTime = updateTime;
        }

        public int getCounts()
        {
            return counts;
        }

        public void setCounts(int counts)
        {
            this.counts = counts;
        }

        public double getSize()
        {
            return size;
        }

        public void setSize(double size)
        {
            this.size = size;
        }

        public String getSurl()
        {
            return surl;
        }

        public void setSurl(String surl)
        {
            this.surl = surl;
        }

        public int getIszm()
        {
            return iszm;
        }

        public void setIszm(int iszm)
        {
            this.iszm = iszm;
        }

        public List<String> getAddrs()
        {
            return addrs;
        }

        public void setAddrs(List<String> addrs)
        {
            this.addrs = addrs;
        }
    }
}
