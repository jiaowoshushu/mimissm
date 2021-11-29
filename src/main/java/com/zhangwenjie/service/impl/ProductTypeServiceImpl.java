package com.zhangwenjie.service.impl;

import com.zhangwenjie.mapper.ProductTypeMapper;
import com.zhangwenjie.pojo.ProductType;
import com.zhangwenjie.pojo.ProductTypeExample;
import com.zhangwenjie.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
