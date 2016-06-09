package com.windcake.cartonhub.beans;

/**
 * Created by windcake on 16/6/8.
 */
public class CollectBean
{
    private int id;
    private String url;
    private String title;
    private String author;
    private String area;
    private String type;
    private String intro;


    public CollectBean()
    {

    }
    public CollectBean(String url,
                       String title,
                       String author,
                       String area,
                       String type,
                       String intro)
    {
        this.url = url;
        this.title = title;
        this.author = author;
        this.area = area;
        this.type = type;
        this.intro = intro;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }
}
