package com.windcake.cartonhub.beans;

import java.util.List;

/**
 * Created by windcake on 16/6/7.
 */
public class DetailBean
{

    /**
     * status : 0
     * data : {"title":"unimate的王","comicId":51737,"thumb":"http://csimg.dm300.com/images/spider/20160607/14652782627571.jpg","authorName":"佚名","comicType":"搞笑,侦探","areaName":"国产","updateTime":"2016-06-07","comicSrc":[{"title":"漫画台漫画","id":"12","lastCharpterTitle":"第1话","lastCharpterId":"1941196","lastCharpterUpdateTime":1465278265}],"tucaos":"0","intro":"unimate的王","charpters":[]}
     */

    private int status;
    /**
     * title : unimate的王
     * comicId : 51737
     * thumb : http://csimg.dm300.com/images/spider/20160607/14652782627571.jpg
     * authorName : 佚名
     * comicType : 搞笑,侦探
     * areaName : 国产
     * updateTime : 2016-06-07
     * comicSrc : [{"title":"漫画台漫画","id":"12","lastCharpterTitle":"第1话","lastCharpterId":"1941196","lastCharpterUpdateTime":1465278265}]
     * tucaos : 0
     * intro : unimate的王
     * charpters : []
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
        private String title;
        private int comicId;
        private String thumb;
        private String authorName;
        private String comicType;
        private String areaName;
        private String updateTime;
        private String tucaos;
        private String intro;
        /**
         * title : 漫画台漫画
         * id : 12
         * lastCharpterTitle : 第1话
         * lastCharpterId : 1941196
         * lastCharpterUpdateTime : 1465278265
         */

        private List<ComicSrcBean> comicSrc;
        private List<?> charpters;

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public int getComicId()
        {
            return comicId;
        }

        public void setComicId(int comicId)
        {
            this.comicId = comicId;
        }

        public String getThumb()
        {
            return thumb;
        }

        public void setThumb(String thumb)
        {
            this.thumb = thumb;
        }

        public String getAuthorName()
        {
            return authorName;
        }

        public void setAuthorName(String authorName)
        {
            this.authorName = authorName;
        }

        public String getComicType()
        {
            return comicType;
        }

        public void setComicType(String comicType)
        {
            this.comicType = comicType;
        }

        public String getAreaName()
        {
            return areaName;
        }

        public void setAreaName(String areaName)
        {
            this.areaName = areaName;
        }

        public String getUpdateTime()
        {
            return updateTime;
        }

        public void setUpdateTime(String updateTime)
        {
            this.updateTime = updateTime;
        }

        public String getTucaos()
        {
            return tucaos;
        }

        public void setTucaos(String tucaos)
        {
            this.tucaos = tucaos;
        }

        public String getIntro()
        {
            return intro;
        }

        public void setIntro(String intro)
        {
            this.intro = intro;
        }

        public List<ComicSrcBean> getComicSrc()
        {
            return comicSrc;
        }

        public void setComicSrc(List<ComicSrcBean> comicSrc)
        {
            this.comicSrc = comicSrc;
        }

        public List<?> getCharpters()
        {
            return charpters;
        }

        public void setCharpters(List<?> charpters)
        {
            this.charpters = charpters;
        }

        public static class ComicSrcBean
        {
            private String title;
            private String id;
            private String lastCharpterTitle;
            private String lastCharpterId;
            private int lastCharpterUpdateTime;

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getLastCharpterTitle()
            {
                return lastCharpterTitle;
            }

            public void setLastCharpterTitle(String lastCharpterTitle)
            {
                this.lastCharpterTitle = lastCharpterTitle;
            }

            public String getLastCharpterId()
            {
                return lastCharpterId;
            }

            public void setLastCharpterId(String lastCharpterId)
            {
                this.lastCharpterId = lastCharpterId;
            }

            public int getLastCharpterUpdateTime()
            {
                return lastCharpterUpdateTime;
            }

            public void setLastCharpterUpdateTime(int lastCharpterUpdateTime)
            {
                this.lastCharpterUpdateTime = lastCharpterUpdateTime;
            }
        }
    }
}
