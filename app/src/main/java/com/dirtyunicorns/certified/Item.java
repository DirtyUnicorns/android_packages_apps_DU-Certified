package com.dirtyunicorns.certified;

public class Item {

    String theme_name;
    String theme_author;
    String theme_summary;
    String paid;
    String themeready;
    Uri uri;

    public Item(String theme_name, String theme_author, String theme_summary, String paid, String themeready) {

        this.theme_name = theme_name;
        this.theme_author = theme_author;
        this.theme_summary = theme_summary;
        this.paid = paid;
        this.themeready = themeready;

        uri = new Uri();

    }
}
