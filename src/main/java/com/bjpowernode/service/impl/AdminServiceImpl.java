package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
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
