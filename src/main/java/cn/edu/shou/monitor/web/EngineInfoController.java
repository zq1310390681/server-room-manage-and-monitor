package cn.edu.shou.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by seky on 15/12/16.
 */
@Controller
@RequestMapping(value = "/engine")
public class EngineInfoController {

    @RequestMapping(value = "/getEngineInfo")
    public String getEngineInfo(){

        return "engineInfo";
    }
}
