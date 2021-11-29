package com.zhangwenjie;

import com.zhangwenjie.mapper.ProductInfoMapper;
import com.zhangwenjie.pojo.ProductInfo;
import com.zhangwenjie.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml","classpath:applicationContext-service.xml"})
public class Test02 {
    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void  test(){
        ProductInfoVo vo =new ProductInfoVo();
        vo.setPtype(1);
        List<ProductInfo> list = mapper.selectCondition(vo);

        list.forEach(ProductInfo -> System.out.println(ProductInfo));
    }
}
