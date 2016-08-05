package com.dirtyunicorns.certified.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Uri implements Serializable {

    @SerializedName("card_thumbnail")
    private String cardThumbnail = null;
    @SerializedName("collapsing_toolbar_thumbnail")
    private String collapsingToolbarThumbnail = null;
    private String screenshot1 = null;
    private String screenshot2 = null;
    private String screenshot3 = null;
    private String screenshot4 = null;
    private String screenshot5 = null;
    private String screenshot6 = null;
    private String playstore = null;
    private String contact = null;
    @SerializedName("contact_background")
    private String contactBackground = null;
    @SerializedName("contact_image")
    private String contactImage = null;

    public String getCardThumbnail() {
        return cardThumbnail;
    }

    public void setCardThumbnail(String cardThumbnail) {
        this.cardThumbnail = cardThumbnail;
    }

    public String getCollapsingToolbarThumbnail() {
        return collapsingToolbarThumbnail;
    }

    public void setCollapsingToolbarThumbnail(String collapsingToolbarThumbnail) {
        this.collapsingToolbarThumbnail = collapsingToolbarThumbnail;
    }

    public String getScreenshot1() {
        return screenshot1;
    }

    public void setScreenshot1(String screenshot1) {
        this.screenshot1 = screenshot1;
    }

    public String getScreenshot2() {
        return screenshot2;
    }

    public void setScreenshot2(String screenshot2) {
        this.screenshot2 = screenshot2;
    }

    public String getScreenshot3() {
        return screenshot3;
    }

    public void setScreenshot3(String screenshot3) {
        this.screenshot3 = screenshot3;
    }

    public String getScreenshot4() {
        return screenshot4;
    }

    public void setScreenshot4(String screenshot4) {
        this.screenshot4 = screenshot4;
    }

    public String getScreenshot5() {
        return screenshot5;
    }

    public void setScreenshot5(String screenshot5) {
        this.screenshot5 = screenshot5;
    }

    public String getScreenshot6() {
        return screenshot6;
    }

    public void setScreenshot6(String screenshot6) {
        this.screenshot6 = screenshot6;
    }

    public String getPlaystore() {
        return playstore;
    }

    public void setPlaystore(String playstore) {
        this.playstore = playstore;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactBackground() {
        return contactBackground;
    }

    public void setContactBackground(String contactBackground) {
        this.contactBackground = contactBackground;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }
}
