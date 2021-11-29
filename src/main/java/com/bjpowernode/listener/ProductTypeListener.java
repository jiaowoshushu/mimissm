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
        //手工从spring容器中获取ProductTypeServiceIml的对象
        ApplicationContext context =new ClassPathXmlApplicationContext("applicationContext-*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("productTypeServiceImpl");
        List<ProductType> ptlist= productTypeService.getAll();
        //将数据放入全局作用域中，供前端使用
        servletContextEvent.getServletContext().setAttribute("ptlist",ptlist);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
