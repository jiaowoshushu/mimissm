package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    @Autowired
    ProductInfoService productInfoService;
    String saveFileName="";

    public static final int PAGE_SIZE=5;

    //显示第一页的数据
    @RequestMapping("/split")
    public String split(@RequestParam(value = "page", defaultValue = "1")
                    Integer page, Model model,HttpServletRequest request) {
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if (vo!=null){
            info=productInfoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else{
            info = productInfoService.splitPage(page, PAGE_SIZE);}
        model.addAttribute("info", info);
        return "product";
    }

    //ajax分页显示
    @RequestMapping("/ajaxsplit")
    @ResponseBody
    public void ajaxsplit(ProductInfoVo vo,HttpSession session){
        PageInfo<ProductInfo> info =productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //ajax进行商品图片上传
    @RequestMapping("/ajaxImg")
    @ResponseBody
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生成文件名UUID+上传图片的后缀 .jpg, .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中存储图片的路径（image_big文件夹所在的路径,真实路径）
        String path =request.getServletContext().getRealPath("image_big");
        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回客户端JSON对象，封装图片的路径为了在页面实现图片的立即回显
        JSONObject object =new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }

    //新增商品
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int res =-1;
        try {
            res=productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (res>0){
            request.setAttribute("msg","商品添加成功！");
        }
        else {
            request.setAttribute("msg","商品添加失败！");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }

    //点击编辑商品后回显商品
     @RequestMapping("/one")
     public String one(Integer pid,ProductInfoVo vo,HttpServletRequest request){

       ProductInfo info= productInfoService.getProductInfo(pid);
       request.getSession().setAttribute("prod",info);
       //将多条件和页面放入session中，在进行更新处理后分页时读取条件和页码
       request.getSession().setAttribute("prodVo",vo);
       return "update" ;
     }

     //更新商品
     @RequestMapping("/update")
     public String update(ProductInfo info,HttpServletRequest request){
        //判断是否上传了图片,也就是是否更换了商品的图片
         // 如果进行了上传，那么ajax进行图片上传的action中saveFileName不为空，
         // 反之为saveFileName="",实体类info使用原先表单域标签上传的pImage的图片名称
         if (!saveFileName.equals("")){
             info.setpImage(saveFileName);
         }
       //更新商品
       int res= -1;
        try {
            res=productInfoService.updateProductInfo(info);
        }catch (Exception e){
          e.printStackTrace();
        }
       if (res>0){
           //此时说明更新成功
           request.setAttribute("msg","商品更新成功！");
       }else {
           //此时说明更新失败
           request.setAttribute("msg","商品更新识别");
       }
       //对saveFileName变量进行清空，因为下一次进行更新时会使用这个变量作为判断的依据
         saveFileName="";

         return "forward:/prod/split.action";
     }

     //a进行单个商品的删除
     @RequestMapping("/delete")
     public String delete(Integer pid,ProductInfoVo vo, HttpSession session) {
         int num = productInfoService.delete(pid);
         if (num > 0){
             session.setAttribute("msg", "删除成功！");
             session.setAttribute("deleteProdVo",vo);}

         else{
             session.setAttribute("msg", "删除失败！");}
         //删除后用重定向跳转
         return "forward:/prod/deleteajaxsplit.action";
     }
        //删除结束后进行分页显示
     @RequestMapping(value="/deleteajaxsplit",produces="text/html;charset=UTF-8")
     @ResponseBody
     public Object deleteAjaxSpilt(HttpServletRequest request){
        PageInfo<ProductInfo>info =null;
        Object vo =request.getSession().getAttribute("deleteProdVo");
        if (vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.removeAttribute("deleteProdVo");
        }
       else {
           info=productInfoService.splitPage(1,PAGE_SIZE);
       }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    //批量删除
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String str,HttpServletRequest request){
        String []id =str.split(",");
        int res=-1;
        try {
            res =productInfoService.deleteBatch(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res>0){
           request.setAttribute("msg","批量删除成功");

        }
        else {request.setAttribute("msg","批量删除失败");}

        return "forward:/prod/deleteajaxsplit.action";
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list =productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
