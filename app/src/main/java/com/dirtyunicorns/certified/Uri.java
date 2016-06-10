package com.dirtyunicorns.certified;

public class Uri {

    private String card_thumbnail = null;
    private String collapsing_toolbar_thumbnail = null;
    private String screenshot1 = null;
    private String screenshot2 = null;
    private String screenshot3 = null;
    private String playstore = null;
    private String contact = null;

    public Uri() {

    }

    public void setCard_thumbnail(String ctUrl) {
        card_thumbnail = ctUrl;
    }

    public void setCollapsing_toolbar_thumbnail (String cttUrl) {
        collapsing_toolbar_thumbnail = cttUrl;
    }

    public void setScreenshot1(String s1Url) {
        screenshot1 = s1Url;
    }

    public void setScreenshot2(String s2Url) {
        screenshot2 = s2Url;
    }

    public void setScreenshot3(String s3Url) {
        screenshot3 = s3Url;
    }

    public void setPlaystore(String playstoreUrl) {
        playstore = playstoreUrl;
    }

    public void setContact(String contactUrl) {
        contact = contactUrl;
    }

    public String getCard_thumbnail() {
        return card_thumbnail;
    }

    public String getCollapsing_toolbar_thumbnail() {
        return collapsing_toolbar_thumbnail;
    }

    public String getScreenshot1() {
        return screenshot1;
    }

    public String getScreenshot2() {
        return screenshot2;
    }

    public String getScreenshot3() {
        return screenshot3;
    }

    public String getPlaystore() {
        return playstore;
    }

    public String getContact() {
        return contact;
    }
}
