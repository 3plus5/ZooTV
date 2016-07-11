package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class Category implements Serializable{
    private String name;        //分类名
    private String picUrl;        //图片地址
    private String douyuUrl;    //斗鱼请求地址
    private String huyaUrl;        //虎牙请求地址
    private String pandaUrl;    //熊猫请求地址


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPicUrl() {
        return picUrl;
    }


    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    public String getDouyuUrl() {
        return douyuUrl;
    }


    public void setDouyuUrl(String douyuUrl) {
        this.douyuUrl = douyuUrl;
    }


    public String getHuyaUrl() {
        return huyaUrl;
    }


    public void setHuyaUrl(String huyaUrl) {
        this.huyaUrl = huyaUrl;
    }


    public String getPandaUrl() {
        return pandaUrl;
    }


    public void setPandaUrl(String pandaUrl) {
        this.pandaUrl = pandaUrl;
    }


    public Category() {
    }

    public Category(String name, String picUrl, String douyuUrl, String huyaUrl, String pandaUrl) {
        this.name = name;
        this.picUrl = picUrl;
        this.douyuUrl = douyuUrl;
        this.huyaUrl = huyaUrl;
        this.pandaUrl = pandaUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", douyuUrl='" + douyuUrl + '\'' +
                ", huyaUrl='" + huyaUrl + '\'' +
                ", pandaUrl='" + pandaUrl + '\'' +
                '}';
    }
}