package com.dirtyunicorns.certified.themes;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.substringAfter;

public abstract class Theme {

    protected int screenshot1 = -1;
    protected int screenshot2 = -1;
    protected int screenshot3 = -1;
    protected int icon = -1;
    protected int name = -1;
    protected int author = -1;
    protected int price = -1;
    protected int playStoreLink = -1;
    protected int width = -1;
    protected int height = -1;

    public int getScreenshot1() {
        return screenshot1;
    }

    public int getScreenshot2() {
        return screenshot2;
    }

    public int getScreenshot3() {
        return screenshot3;
    }

    public int getIcon() {
        return icon;
    }

    public int getName() {
        return name;
    }

    public int getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public int getPlayStoreLink() {
        return playStoreLink;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public String toPkg(Context context) {
        String playStoreLink = context.getString(getPlayStoreLink());
        return substringAfter(playStoreLink, "details?id=");
    }

}
