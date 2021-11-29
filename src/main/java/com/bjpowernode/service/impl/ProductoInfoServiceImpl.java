package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {

        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //使用分页插件PageHelper完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo对象的封装
        //进行有条件的的查询，必须创建ProductInfoExample对象的创建
        ProductInfoExample productInfoExample =new ProductInfoExample();
        //设置降序排列
        productInfoExample.setOrderByClause("p_id desc");
        //取集合，一定要先设置PageHelper
        List<ProductInfo> list =productInfoMapper.selectByExample(productInfoExample);
        //将查到的集合封装到PageInfo中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getProductInfo(Integer pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateProductInfo(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list =productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }
}

