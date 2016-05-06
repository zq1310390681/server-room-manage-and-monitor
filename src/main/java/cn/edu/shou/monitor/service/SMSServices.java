package cn.edu.shou.monitor.service;


import cn.edu.shou.monitor.util.SmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by sqhe18 on 15/12/18.
 */
@Service
public class SMSServices {

    @Autowired
    private SmsSend smssend;
    @Value("${spring.main.sendsms}")
    String sendsms;

    public Boolean sendSMS(String mobilePhones,String Content)
    {
        if (!sendsms.equals("false")){
            smssend.sendMessage(mobilePhones,Content);
        }
        return true;
    }
}
