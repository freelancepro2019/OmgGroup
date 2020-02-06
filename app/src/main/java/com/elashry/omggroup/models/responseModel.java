package com.elashry.omggroup.models;

public class responseModel {

    private String tv_url;
    private Boolean ads;

    private String radio_url;
    private String app_background;
    private Boolean active_logo;
    private Boolean active_image_ads;
    private Boolean active_time;
    private Boolean active_hash_tag;
    private String hashtag;

    public String getHashtag() {
        return hashtag;
    }

    public Boolean getActive_logo() {
        return active_logo;
    }

    public Boolean getActive_image_ads() {
        return active_image_ads;
    }

    public Boolean getActive_time() {
        return active_time;
    }

    public Boolean getActive_hash_tag() {
        return active_hash_tag;
    }

    public String getTv_url() {
        return tv_url;
    }

    public String getRadio_url() {
        return radio_url;
    }

    public String getApp_background() {
        return app_background;
    }

    public Boolean getAds() {
        return ads;
    }
}
