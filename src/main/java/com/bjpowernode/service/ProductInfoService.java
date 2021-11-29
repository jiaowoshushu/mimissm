package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    //显示所有商品（不分页）
    List<ProductInfo> getAll();
    //实现分页功能
    PageInfo splitPage(int pageNum,int pageSize);
    //实现商品上传
    int save(ProductInfo info);
    // 按主键查询商品
    ProductInfo getProductInfo(Integer pid);
    //更新商品
    int updateProductInfo(ProductInfo info);
    //删除单个商品
    int delete(int pid);
    //批量删除商品
    int deleteBatch(String []ids);
    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);
    //多条件分页查询
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
