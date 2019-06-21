package com.elashry.omggroup;

public class responseModel {


    private String tv_url;
    private String radio_url;

    public responseModel(String tv_url, String radio_url) {
        this.tv_url = tv_url;
        this.radio_url = radio_url;
    }

    public void setTv_url(String tv_url) {
        this.tv_url = tv_url;
    }

    public void setRadio_url(String radio_url) {
        this.radio_url = radio_url;
    }

    public String getTv_url() {
        return tv_url;
    }

    public String getRadio_url() {
        return radio_url;
    }
}
