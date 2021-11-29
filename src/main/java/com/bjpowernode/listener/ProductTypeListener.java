package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //�ֹ���spring�����л�ȡProductTypeServiceIml�Ķ���
        ApplicationContext context =new ClassPathXmlApplicationContext("applicationContext-*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("productTypeServiceImpl");
        List<ProductType> ptlist= productTypeService.getAll();
        //�����ݷ���ȫ���������У���ǰ��ʹ��
        servletContextEvent.getServletContext().setAttribute("ptlist",ptlist);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
