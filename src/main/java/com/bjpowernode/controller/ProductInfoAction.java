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

    //��ʾ��һҳ������
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

    //ajax��ҳ��ʾ
    @RequestMapping("/ajaxsplit")
    @ResponseBody
    public void ajaxsplit(ProductInfoVo vo,HttpSession session){
        PageInfo<ProductInfo> info =productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //ajax������ƷͼƬ�ϴ�
    @RequestMapping("/ajaxImg")
    @ResponseBody
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //��ȡ�����ļ���UUID+�ϴ�ͼƬ�ĺ�׺ .jpg, .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //�õ���Ŀ�д洢ͼƬ��·����image_big�ļ������ڵ�·��,��ʵ·����
        String path =request.getServletContext().getRealPath("image_big");
        //ת��
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //���ؿͻ���JSON���󣬷�װͼƬ��·��Ϊ����ҳ��ʵ��ͼƬ����������
        JSONObject object =new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }

    //������Ʒ
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
            request.setAttribute("msg","��Ʒ��ӳɹ���");
        }
        else {
            request.setAttribute("msg","��Ʒ���ʧ�ܣ�");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }

    //����༭��Ʒ�������Ʒ
     @RequestMapping("/one")
     public String one(Integer pid,ProductInfoVo vo,HttpServletRequest request){

       ProductInfo info= productInfoService.getProductInfo(pid);
       request.getSession().setAttribute("prod",info);
       //����������ҳ�����session�У��ڽ��и��´�����ҳʱ��ȡ������ҳ��
       request.getSession().setAttribute("prodVo",vo);
       return "update" ;
     }

     //������Ʒ
     @RequestMapping("/update")
     public String update(ProductInfo info,HttpServletRequest request){
        //�ж��Ƿ��ϴ���ͼƬ,Ҳ�����Ƿ��������Ʒ��ͼƬ
         // ����������ϴ�����ôajax����ͼƬ�ϴ���action��saveFileName��Ϊ�գ�
         // ��֮ΪsaveFileName="",ʵ����infoʹ��ԭ�ȱ����ǩ�ϴ���pImage��ͼƬ����
         if (!saveFileName.equals("")){
             info.setpImage(saveFileName);
         }
       //������Ʒ
       int res= -1;
        try {
            res=productInfoService.updateProductInfo(info);
        }catch (Exception e){
          e.printStackTrace();
        }
       if (res>0){
           //��ʱ˵�����³ɹ�
           request.setAttribute("msg","��Ʒ���³ɹ���");
       }else {
           //��ʱ˵������ʧ��
           request.setAttribute("msg","��Ʒ����ʶ��");
       }
       //��saveFileName����������գ���Ϊ��һ�ν��и���ʱ��ʹ�����������Ϊ�жϵ�����
         saveFileName="";

         return "forward:/prod/split.action";
     }

     //a���е�����Ʒ��ɾ��
     @RequestMapping("/delete")
     public String delete(Integer pid,ProductInfoVo vo, HttpSession session) {
         int num = productInfoService.delete(pid);
         if (num > 0){
             session.setAttribute("msg", "ɾ���ɹ���");
             session.setAttribute("deleteProdVo",vo);}

         else{
             session.setAttribute("msg", "ɾ��ʧ�ܣ�");}
         //ɾ�������ض�����ת
         return "forward:/prod/deleteajaxsplit.action";
     }
        //ɾ����������з�ҳ��ʾ
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

    //����ɾ��
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
           request.setAttribute("msg","����ɾ���ɹ�");

        }
        else {request.setAttribute("msg","����ɾ��ʧ��");}

        return "forward:/prod/deleteajaxsplit.action";
    }

    //��������ѯ
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list =productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
