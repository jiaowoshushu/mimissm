package com.zhangwenjie.service.impl;

import com.zhangwenjie.mapper.AdminMapper;
import com.zhangwenjie.pojo.Admin;
import com.zhangwenjie.pojo.AdminExample;
import com.zhangwenjie.service.AdminService;
import com.zhangwenjie.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String username, String password) {

        AdminExample example =new AdminExample();

        example.createCriteria().andANameEqualTo(username);

        List<Admin> list =adminMapper.selectByExample(example);

        if (list.size()>0){
            Admin admin=list.get(0);
            String pass =MD5Util.stringToMD5(password);
            if (pass.equals(admin.getaPass())){
                return admin;
            }
        }

        return null;
    }
}
