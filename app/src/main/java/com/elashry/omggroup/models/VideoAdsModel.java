package com.elashry.omggroup.models;


import java.util.List;

public class VideoAdsModel {


    private Integer id;
    private String name;
    private int period;
    private List<Video> videos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public Integer getPeriod() {
        return period;
    }


    public List<Video> getVideos() {
        return videos;
    }







    public class Video {

        private String download_link;
        private String original_name;

        public String getDownloadLink() {
            return download_link;
        }


        public String getOriginalName() {
            return original_name;
        }



    }
}