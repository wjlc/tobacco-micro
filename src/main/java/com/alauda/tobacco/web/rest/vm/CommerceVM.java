package com.alauda.tobacco.web.rest.vm;

public class CommerceVM {
    private String commpanyname;
    private String retailname;
    private Long industryId;

    public String getCommpanyname() {
        return commpanyname;
    }

    public void setCommpanyname(String commpanyname) {
        this.commpanyname = commpanyname;
    }

    public String getRetailname() {
        return retailname;
    }

    public void setRetailname(String retailname) {
        this.retailname = retailname;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }
}
