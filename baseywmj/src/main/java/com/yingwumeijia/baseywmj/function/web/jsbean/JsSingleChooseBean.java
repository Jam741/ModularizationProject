package com.yingwumeijia.baseywmj.function.web.jsbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jam on 2017/4/14 上午11:44.
 * Describe:
 */

public class JsSingleChooseBean implements Serializable {


    /**
     * title : 选择类型
     * array : [{"code":1,"desc":"房产中介工作者"},{"code":2,"desc":"物业公司工作者"},{"code":3,"desc":"装饰公司工作者"},{"code":4,"desc":"社会自由人员"}]
     */

    private String title;
    private List<ArrayBean> array;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArrayBean> getArray() {
        return array;
    }

    public void setArray(List<ArrayBean> array) {
        this.array = array;
    }

    public static class ArrayBean implements Serializable{
        public ArrayBean(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public ArrayBean() {
        }

        /**
         * code : 1
         * desc : 房产中介工作者
         */


        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
