package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.Group;
import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.service.GroupRepository;
import cn.edu.shou.monitor.service.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;

/**
 * Created by sqhe on 14-7-23.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Value("${spring.main.homedir}")
    String homedir;

    @Autowired
    UserRepository userDAO;

    @Autowired
    GroupRepository gr;

    @RequestMapping(value = "/info/{userid}.html")
    public String getUserInfo(Model model,@PathVariable("userid") Long userid)
    {
        String delayWarm="";
        User user=this.userDAO.findOne(userid);
        //添加延时处理信息
        if (user.delayWarm.equals("m")){
            delayWarm="邮件提醒："+user.delaynum+" 小时";
        }else if (user.delayWarm.equals("e")){
            delayWarm="短信提醒："+user.delaynum+" 小时";
        }else {
            delayWarm="不提醒";
        }
        model.addAttribute("user",user);
        model.addAttribute("delayWarm",delayWarm);
        return "userinfo";
    }
    @RequestMapping(value = "/userManage")
    public String getgroupTree(Model model,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        return "userManagement";
    }
    @RequestMapping(value = "/edit/{userid}.html")
    public String getUserInfoForEdit(Model model,@PathVariable Long userid)
    {
        model.addAttribute("user",userDAO.findOne(userid));
        return "userinfoedit";
    }
    @RequestMapping(value = "/edit/image",method = RequestMethod.POST)
    public String setUserInfo(Model model,@RequestParam String imagePath, @RequestParam Long id)
    {
        User user = userDAO.findOne(id);
        user.setImagePath(imagePath);
        userDAO.save(user);
        model.addAttribute("user",user);
        return "userinfoedit";
    }
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public String setUserInfo(Model model, @RequestParam String userName,@RequestParam String password,@RequestParam String msg,@RequestParam String emailSend,
                              @RequestParam String delay,@RequestParam String delayNum,@RequestParam String sex,@RequestParam String email,
                              @RequestParam String tel,@RequestParam Long id,@RequestParam Long group)
    {
        //修改 郑小罗 20141204 用户名不能修改
        User user = userDAO.findOne(id);
        user.setPassword(password);
        user.setSex(sex);
        user.setEmail(email);
        user.setTel(tel);
        user.setDelaynum(Integer.parseInt(delayNum));
        user.setEmailSend(emailSend.equals("1") );
        user.setMsgSend(msg.equals("1") );
        user.setDelayWarm(delay);
        Group gp=gr.findOne(group);
        user.setGroup(gp);
        userDAO.save(user);
        model.addAttribute("user",user);
        return "redirect:/user/info/"+id+".html";
    }
    @RequestMapping("/image/{id}")
    public ResponseEntity<byte[]> downFile(@PathVariable Long id)  {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //String path = req.getSession().getServletContext().getRealPath("/");

        //默认文件名称
        User user = userDAO.findOne(id);

        String path="";
        String downFileName="";
        String filePath="";//文件路径
        String defaultFile=homedir+"/userImg/default.jpg";

        downFileName = user.getImagePath();

        if(downFileName!=null){
            path = homedir+"/userImg/"+id;
        }else {
            //path=req.getSession().getServletContext().getRealPath("/")+"img";

//            path="/esicmissive_springboot/defalutImage";
            path=homedir+"/";
            downFileName="default.jpg";
        }


        try {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");//转码解决IE下文件名乱码问题
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Http响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", downFileName);
        filePath=path + "/" + downFileName;//头像文件，判断是否存在，不存在使用默认头像
        if (!FileOperate.exitFile(filePath)){
            filePath=defaultFile;//如果头像不存在，使用系统默认的头像文件
        }

        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                    headers,
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //日志

        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "error.txt");
        return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
    }
    //获取用户签名图片
    @RequestMapping("/getSignImg/{userId}")
    public ResponseEntity<byte[]>getUserSignImg(@PathVariable Long userId){
        //Http响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "");
        byte[] img=new byte[1024];//接收签名字符流
        try {
            //默认文件名称
            User user = userDAO.findOne(userId);
            if (user!=null) {
                img=user.getSignImg();

                return new ResponseEntity<byte[]>(img,
                        headers,
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //日志
            //TODO
        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "error.txt");
        return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
    }
}
