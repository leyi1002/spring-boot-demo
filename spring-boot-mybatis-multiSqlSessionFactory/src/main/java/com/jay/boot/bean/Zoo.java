package com.jay.boot.bean;

/**
 * Created by Administrator on 2018/3/2.
 */
public class Zoo {

    private Integer id;
    private String place;
    private Integer animalsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getAnimalsCount() {
        return animalsCount;
    }

    public void setAnimalsCount(Integer animalsCount) {
        this.animalsCount = animalsCount;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", animalsCount=" + animalsCount +
                '}';
    }
}
