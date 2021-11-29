package com.zhangwenjie.service.impl;

import com.zhangwenjie.mapper.ProductInfoMapper;
import com.zhangwenjie.pojo.ProductInfo;
import com.zhangwenjie.pojo.ProductInfoExample;
import com.zhangwenjie.pojo.vo.ProductInfoVo;
import com.zhangwenjie.service.ProductInfoService;
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
        //ʹ�÷�ҳ���PageHelper��ɷ�ҳ����
        PageHelper.startPage(pageNum,pageSize);

        //����PageInfo����ķ�װ
        //�����������ĵĲ�ѯ�����봴��ProductInfoExample����Ĵ���
        ProductInfoExample productInfoExample =new ProductInfoExample();
        //���ý�������
        productInfoExample.setOrderByClause("p_id desc");
        //ȡ���ϣ�һ��Ҫ������PageHelper
        List<ProductInfo> list =productInfoMapper.selectByExample(productInfoExample);
        //���鵽�ļ��Ϸ�װ��PageInfo��
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

