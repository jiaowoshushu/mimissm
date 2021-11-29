package com.bjpowernode.service;

import com.bjpowernode.pojo.Admin;

public interface AdminService {
    Admin login(String username,String password);
}
