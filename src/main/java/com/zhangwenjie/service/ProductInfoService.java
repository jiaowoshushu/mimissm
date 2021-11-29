package com.zhangwenjie.service;

import com.zhangwenjie.pojo.ProductInfo;
import com.zhangwenjie.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    //��ʾ������Ʒ������ҳ��
    List<ProductInfo> getAll();
    //ʵ�ַ�ҳ����
    PageInfo splitPage(int pageNum,int pageSize);
    //ʵ����Ʒ�ϴ�
    int save(ProductInfo info);
    // ��������ѯ��Ʒ
    ProductInfo getProductInfo(Integer pid);
    //������Ʒ
    int updateProductInfo(ProductInfo info);
    //ɾ��������Ʒ
    int delete(int pid);
    //����ɾ����Ʒ
    int deleteBatch(String []ids);
    //��������ѯ
    List<ProductInfo> selectCondition(ProductInfoVo vo);
    //��������ҳ��ѯ
    public PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
