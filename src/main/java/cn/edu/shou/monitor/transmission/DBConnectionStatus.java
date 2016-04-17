package cn.edu.shou.monitor.transmission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by light on 2016/3/24.
 */
@Controller
@RestController
@RequestMapping(value = "/predictCenter/screen")
public class DBConnectionStatus {
    //OceanStation 共51个
    @RequestMapping(value = "/OceanStation")
    public static ArrayList<Integer> getOceanStation(){
        return getStatus.getStatusArray(51);
    }

    //voluntary observation ship 共13个
    @RequestMapping(value = "/VOShip")
    public static ArrayList<Integer> getVOShip(){
        return getStatus.getStatusArray(13);
    }

    //floating mark共13个
    @RequestMapping(value = "/floatingMark")
    public static ArrayList<Integer> getFloatingMark(){
        return getStatus.getStatusArray(7);
    }

    // Radar 共14个
    @RequestMapping(value = "/radars")
    public static ArrayList<Integer> getRadar(){
        return getStatus.getStatusArray(14);
    }

    // 得到receiveRate
    public static String getReceiveRate(){
        return "ReceiveRate:"+ getStatus.getRate();
    }

    // 得到reportRate
    public static String getReportRate(){
        return "ReportRate:"+ getStatus.getRate();
    }

    // 得到入库率DBInflowRate
    @RequestMapping(value = "/DBInflowRate")
    public static String getDBInflowRate(){
        return "DBInflowRate:"+ getStatus.getRate();
    }

    public static class getStatus {
        public static ArrayList<Integer> getStatusArray(int arraySize) {
            ArrayList<Integer> connStatus = new ArrayList<Integer>();
            for (int i = 0; i < arraySize; i++) {
                int connectionStatus = (int) (1 + Math.random() * 3);
                connStatus.add(i,connectionStatus);
            }
            return connStatus;
        }

        public static int getRate(){
            return (int)(Math.random()*100);
        }
    }
}
