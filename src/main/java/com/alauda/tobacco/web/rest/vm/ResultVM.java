package com.alauda.tobacco.web.rest.vm;

import com.alauda.tobacco.domain.Commerce;

import java.time.ZonedDateTime;
import java.util.List;

public class ResultVM {

    private String name;
    private String manufacturer;
    private ZonedDateTime date;
    private String desc;
    private List<Commerce> commerces;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Commerce> getCommerces() {
        return commerces;
    }

    public void setCommerces(List<Commerce> commerces) {
        this.commerces = commerces;
    }

}
