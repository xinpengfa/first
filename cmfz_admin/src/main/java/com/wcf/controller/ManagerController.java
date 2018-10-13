package com.wcf.controller;

import com.wcf.service.ManagerService;
import com.wcf.utils.ImageUtil;
import com.wcf.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Resource
    private ManagerService managerService;
    @ResponseBody
    @RequestMapping("/login")
    public Result login(HttpSession session,String name,String password,boolean ischeck,String code,HttpServletResponse response){
        Result result = new Result();
        String validationCode = (String) session.getAttribute("validationCode");
        System.out.println(code);
        System.out.println(validationCode);
        System.out.println(ischeck);
        // 通过安全工具类获取主体对象
        Subject subject = SecurityUtils.getSubject();
        if(validationCode.equalsIgnoreCase(code)){
            try {
                subject.login(new UsernamePasswordToken(name,password,ischeck)); // true 使用记住我的功能 false 不使用
                result.setSuccess(true);
                /*if(ischeck){
                    String encode = URLEncoder.encode(manager.getName(), "utf-8");
                    Cookie cookie = new Cookie("name",encode);
                    //setPath "/" 代表cookie可以应用于整个项目
                    cookie.setPath("/");
                    cookie.setMaxAge(60*60*24*7);
                    response.addCookie(cookie);
                } else{
                    Cookie cookie = new Cookie("name",null);
                    cookie.setMaxAge(0);
                }*/

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage(e.getMessage());
                return result;
            }
        } else{
            result.setSuccess(false);
            result.setMessage("验证码输入错误");
            return result;
        }

    }

    @RequestMapping("/valicode")
    public void valicode(HttpServletResponse response, HttpSession session) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = ImageUtil.createImage();
        //将验证码存入Session
        session.setAttribute("validationCode",objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}
