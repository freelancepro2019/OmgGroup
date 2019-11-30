package com.elashry.omggroup.models;

import java.io.Serializable;
import java.util.List;

public class AdsDataModel implements Serializable {

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public class Payload implements Serializable
    {
        private List<ItemModel> items;

        public List<ItemModel> getItems() {
            return items;
        }
    }
    public class ItemModel implements Serializable
    {
        private int id;
        private String title;
        private String url;
        private Media media;
        private Event events;
        private String type;
        private String brand_name;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public Media getMedia() {
            return media;
        }

        public Event getEvents() {
            return events;
        }

        public String getType() {
            return type;
        }

        public String getBrand_name() {
            return brand_name;
        }
    }



    public class Media implements Serializable
    {
        private String type;
        private String url;

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Event implements Serializable
    {
        private List<Click> click;
        private List<Impression> impression;

        public List<Click> getClick() {
            return click;
        }

        public List<Impression> getImpression() {
            return impression;
        }
    }

    public class Click implements Serializable
    {
        private String type;
        private Value value;

        public String getType() {
            return type;
        }

        public Value getValue() {
            return value;
        }
    }

    public class Value implements Serializable
    {
        private String url;

        public String getUrl() {
            return url;
        }
    }

    public class Impression implements Serializable
    {
        private String url;
        private Body body;

        public String getUrl() {
            return url;
        }

        public Body getBody() {
            return body;
        }


    }



    public class Body implements Serializable
    {
        private int box_id;
        private int campaign_id;
        private int domain_id;
        private String token;
        private String widget_id;

        public int getBox_id() {
            return box_id;
        }

        public int getCampaign_id() {
            return campaign_id;
        }

        public int getDomain_id() {
            return domain_id;
        }

        public String getToken() {
            return token;
        }

        public String getWidget_id() {
            return widget_id;
        }
    }
}
