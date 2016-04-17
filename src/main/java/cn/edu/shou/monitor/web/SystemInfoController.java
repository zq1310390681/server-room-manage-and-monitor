package cn.edu.shou.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by seky on 15/12/16.
 */
@Controller
@RequestMapping(value = "/system")
public class SystemInfoController {

    @RequestMapping(value = "/getSystemInfo")
    public String getSystemInfo(){

        return "systemInfo";
    }
}
