package cn.edu.shou.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by seky on 15/12/16.
 */
@Controller
@RequestMapping(value = "/cpu")
public class CPUInfoController {

    @RequestMapping(value = "/getCpuInfo")
    public String getCpuInfo(){

        return "cpuInfo";
    }
}
