package com.elashry.omggroup.models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoAdsModel {

    private Integer id;
    private String name;
    private Integer period;
    private List<Video> videos = null;
    private String createdAt;
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<Video> getVideos() {
        return videos;
    }


    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public class Video {

        private String downloadLink;
        private String originalName;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }


    }
}