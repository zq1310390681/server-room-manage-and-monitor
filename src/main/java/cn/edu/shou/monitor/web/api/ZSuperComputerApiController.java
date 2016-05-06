package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.service.ZSuperComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by light on 2016/5/4.
 */
@RestController
@RequestMapping(value = "/predictCenter/screen")
public class ZSuperComputerApiController {
    @Autowired
    ZSuperComputerRepository superComputer;

    @RequestMapping(value = "/superComputerShort")
    public String sendScDataShort() throws Exception{
        return superComputer.sendSuComputer("qstat \n");
    }

    @RequestMapping(value = "/superComputerLong")
    public String sendScDataLong() throws Exception{
        return superComputer.sendSuComputer("pbsnodes \n");
    }
}
