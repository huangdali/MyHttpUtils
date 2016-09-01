package com.hdl.myhttputilsdemo.bean;

/**
 * Created by HDL on 2016/8/31.
 */
public class IPBean {

    /**
     * code : 0
     * data : {"country":"中国","country_id":"CN","area":"华南","area_id":"800000","region":"广东省","region_id":"440000","city":"广州市","city_id":"440100","county":"","county_id":"-1","isp":"腾讯网络","isp_id":"1000153","ip":"182.254.34.74"}
     */

    private int code;
    /**
     * country : 中国
     * country_id : CN
     * area : 华南
     * area_id : 800000
     * region : 广东省
     * region_id : 440000
     * city : 广州市
     * city_id : 440100
     * county :
     * county_id : -1
     * isp : 腾讯网络
     * isp_id : 1000153
     * ip : 182.254.34.74
     */

    private DataBean data;

    @Override
    public String toString() {
        return "IPBean{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String country;
        private String country_id;
        private String area;
        private String area_id;
        private String region;
        private String region_id;
        private String city;
        private String city_id;
        private String county;
        private String county_id;
        private String isp;
        private String isp_id;
        private String ip;

        @Override
        public String toString() {
            return "DataBean{" +
                    "country='" + country + '\'' +
                    ", country_id='" + country_id + '\'' +
                    ", area='" + area + '\'' +
                    ", area_id='" + area_id + '\'' +
                    ", region='" + region + '\'' +
                    ", region_id='" + region_id + '\'' +
                    ", city='" + city + '\'' +
                    ", city_id='" + city_id + '\'' +
                    ", county='" + county + '\'' +
                    ", county_id='" + county_id + '\'' +
                    ", isp='" + isp + '\'' +
                    ", isp_id='" + isp_id + '\'' +
                    ", ip='" + ip + '\'' +
                    '}';
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCounty_id() {
            return county_id;
        }

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getIsp_id() {
            return isp_id;
        }

        public void setIsp_id(String isp_id) {
            this.isp_id = isp_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
