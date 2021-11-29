package com.zhangwenjie;

import com.zhangwenjie.utils.MD5Util;
import org.junit.Test;

public class Test01 {
    @Test
    public void test01(){
        String pass= MD5Util.stringToMD5("000000");
        System.out.println(pass);
    }
}
