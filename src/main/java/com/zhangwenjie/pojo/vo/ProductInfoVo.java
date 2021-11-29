package com.zhangwenjie.pojo.vo;

public class ProductInfoVo {

    private String pname;
    private Integer ptype;
    private Integer lestPrice;
    private Integer maxPrice;
    //����ҳ��
    private Integer page=1;

    public ProductInfoVo(String pname, Integer ptype, Integer lestPrice, Integer maxPrice, Integer page) {
        this.pname = pname;
        this.ptype = ptype;
        this.lestPrice = lestPrice;
        this.maxPrice = maxPrice;
        this.page = page;
    }

    public ProductInfoVo() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getLestPrice() {
        return lestPrice;
    }

    public void setLestPrice(Integer lestPrice) {
        this.lestPrice = lestPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", ptype=" + ptype +
                ", lestPrice=" + lestPrice +
                ", maxPrice=" + maxPrice +
                ", page=" + page +
                '}';
    }
}
