package cn.edu.shou.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/24.
 */
@Controller
@RequestMapping(value = "/equip")
public class EquipInfoController {
    @RequestMapping(value = "/getEquipInfo")
    public String getEquipInfo(){

        return "equipInfo";
    }
}
