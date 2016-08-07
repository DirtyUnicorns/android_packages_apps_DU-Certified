package com.dirtyunicorns.certified;

public class Item {

    public String theme_name;
    public String theme_author;
    public String theme_summary;
    public String paid;
    public String arcus;
    public String themeready;
    public String theme_long_summary;
    public Uri uri;

    public Item(String theme_name, String theme_author, String theme_summary, String paid, String arcus, String themeready, String theme_long_summary) {

        this.theme_name = theme_name;
        this.theme_author = theme_author;
        this.theme_summary = theme_summary;
        this.paid = paid;
        this.arcus = arcus;
        this.themeready = themeready;
        this.theme_long_summary = theme_long_summary;

        uri = new Uri();

    }
}
