package com.dirtyunicorns.certified.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme implements Serializable {

    @SerializedName("theme_name")
    private String themeName;
    @SerializedName("theme_author")
    private String themeAuthor;
    @SerializedName("theme_summary")
    private String themeSummary;
    @SerializedName("theme_long_summary")
    private String themeLongSummary;
    private String paid;
    private String themeready;
    private String arcus;
    private Uri uri;

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeAuthor() {
        return themeAuthor;
    }

    public void setThemeAuthor(String themeAuthor) {
        this.themeAuthor = themeAuthor;
    }

    public String getThemeSummary() {
        return themeSummary;
    }

    public void setThemeSummary(String themeSummary) {
        this.themeSummary = themeSummary;
    }

    public String getThemeLongSummary() {
        return themeLongSummary;
    }

    public void setThemeLongSummary(String themeLongSummary) {
        this.themeLongSummary = themeLongSummary;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getThemeready() {
        return themeready;
    }

    public void setThemeready(String themeready) {
        this.themeready = themeready;
    }

    public String getArcus() {
        return arcus;
    }

    public void setArcus(String arcus) {
        this.arcus = arcus;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
