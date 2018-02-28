package com.yingwumeijia.baseywmj.function.web.jsbean;

import java.io.Serializable;

/**
 * Created by Jam on 2017/4/13 下午8:11.
 * Describe:
 */

public class JsAreaCallbackBean implements Serializable{


    /**
     * province : {"name":"四川","code":11}
     * city : {"name":"成都","code":22}
     * area : {"name":"高新区","code":33}
     */

    private ProvinceBean province;
    private CityBean city;
    private AreaBean area;

    public JsAreaCallbackBean(ProvinceBean province, CityBean city, AreaBean area) {
        this.province = province;
        this.city = city;
        this.area = area;
    }

    public ProvinceBean getProvince() {
        return province;
    }

    public void setProvince(ProvinceBean province) {
        this.province = province;
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public static class ProvinceBean implements Serializable{
        public ProvinceBean(String name, int code) {
            this.name = name;
            this.code = code;
        }

        /**
         * name : 四川
         * code : 11
         */

        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class CityBean implements Serializable {

        public CityBean(String name, int code) {
            this.name = name;
            this.code = code;
        }

        /**
         * name : 成都
         * code : 22
         */

        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class AreaBean implements Serializable{
        public AreaBean(String name, int code) {
            this.name = name;
            this.code = code;
        }

        /**
         * name : 高新区
         * code : 33
         */



        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
