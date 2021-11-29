package com.bjpowernode;

import com.bjpowernode.utils.MD5Util;
import org.junit.Test;

import java.sql.SQLOutput;

public class Test01 {
    @Test
    public void test01(){
        String pass= MD5Util.stringToMD5("000000");
        System.out.println(pass);
    }
}
