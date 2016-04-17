package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sqhe on 14-7-7.
 */
@Controller
@RequestMapping(value="")
//@SessionAttributes(value = {"userbase64","user"})
public class IndexController {

    @RequestMapping(value = "/")
    public String Default(){
        return "serverManagement";
    }
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @ModelAttribute(value = "user")
    public User addUsertoPage()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object temp = ((auth != null) ? auth.getPrincipal() :  null);
        if (temp!=null && temp.getClass()==User.class )
        {
            return (User)temp;
        }
        else
            return null;

    }
    @RequestMapping(value = "/front")
    public String getGroupTree(Model model)
    {
        return "index";
    }

}
