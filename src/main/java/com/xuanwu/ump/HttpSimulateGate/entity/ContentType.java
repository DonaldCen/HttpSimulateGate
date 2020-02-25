package com.xuanwu.ump.HttpSimulateGate.entity;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/25
 * @Version 1.0.0
 */
public enum ContentType {
    XML(0,"application/xml"),
    JSON(1,"application/json"),
    FROM_URL_ENCODE(2,"application/x-www-form-urlencoded"),
    FROM_DATA(3,"multipart/form-data")
    ;
    private ContentType(int index,String contentType){
        this.index = index;
        this.contentType = contentType;
    }
    private int index;
    private String contentType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public static ContentType getContentTypeByName(String type){
        if(XML.getContentType().equalsIgnoreCase(type)){
            return XML;
        }else if(JSON.getContentType().equalsIgnoreCase(type)){
            return JSON;
        }else if(FROM_DATA.getContentType().equalsIgnoreCase(type)){
            return FROM_DATA;
        }else if(FROM_URL_ENCODE.getContentType().equalsIgnoreCase(type)){
            return FROM_URL_ENCODE;
        }else {
            return JSON;
        }
    }
}
