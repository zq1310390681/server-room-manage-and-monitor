package cn.edu.shou.monitor.service;
import cn.edu.shou.monitor.spring.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/4.
 */
@Repository
public class ZDataReceiveRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static final String RADAR_SQL = "SELECT common_radar.name,common_radar.time,common_radar.quantity,radar_quantity.day_quantity\n" +
            "from common_radar,radar_quantity where common_radar.name = radar_quantity.name;";
    static final String XRADAR_SQL = "SELECT xradar.name,xradar.time,xradar.quantity,xradar_quantity.day_quantity from xradar,xradar_quantity\n" +
            "where xradar.name = xradar_quantity.name and xradar.time != '--';";
    static final String VOS_SQL = "SELECT single_vos.name,single_vos.time,single_vos.quantity,vos_quantity.day_quantity from single_vos,vos_quantity\n" +
            "where single_vos.name = vos_quantity.name and single_vos.time != '--';";
    static final String BUOY_SQL = "SELECT buoy.name,buoy.time,buoy.quantity,buoy_quantity.day_quantity from buoy,buoy_quantity\n" +
            "where buoy.name = buoy_quantity.buoy_name and buoy.time != '--';";
    static final String STA_REAL_SQL = "SELECT sea_station.name,sea_station.real_time,sea_station.real_quantity as quantity,sea_quantity.real\n" +
            "from sea_station,sea_quantity where sea_station.name = sea_quantity.name and sea_station.real_time != '--';";
    static final String STA_HOURLY_SQL = "SELECT sea_station.name,sea_station.hourly_time,sea_station.hourly_quantity as quantity,sea_quantity.hourly from sea_station,sea_quantity \n" +
            "where sea_station.name = sea_quantity.name and sea_station.real_time != '--';";
    static final String STA_PUN_SQL = "SELECT sea_station.name,sea_station.pun_time,sea_station.pun_quantity as quantity,sea_quantity.pun \n" +
            "from sea_station,sea_quantity where sea_station.name = sea_quantity.name and sea_station.real_time != '--';";



    @TargetDataSource(name = "webdata")
    public List<Map<String,Object>> checkNum(String selectSql){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        if(selectSql.equals("radar")) {
            list = jdbcTemplate.queryForList(RADAR_SQL);
        }
        if(selectSql.equals("xradar")){
            list = jdbcTemplate.queryForList(XRADAR_SQL);
        }
        if(selectSql.equals("vos")){
            list = jdbcTemplate.queryForList(VOS_SQL);
        }
        if(selectSql.equals("buoy")){
            list = jdbcTemplate.queryForList(BUOY_SQL);
        }
        int sumCheckNum=0;
        int sumQuantity=0;
        for(Map<String,Object> hashMap: list){ //计算当前时间应收数；总数*当前小时数+1/24
            String quantity = hashMap.get("day_quantity").toString();
            int quty = Integer.valueOf(quantity);
            String timeStr = hashMap.get("time").toString();
            int index = timeStr.indexOf(" ");
            String hourStr = timeStr.substring(index+1,index+3);
            int hour = Integer.valueOf(hourStr);
            int a = Math.round(quty*(hour+1)/24); // a 为应收的个数
            hashMap.put("checkNum", a);
            sumCheckNum = sumCheckNum+a;
            int qutyGot = Integer.valueOf(hashMap.get("quantity").toString());
            sumQuantity = qutyGot + sumQuantity;
        }
        Map<String,Object> sumCheck = new HashMap<String,Object>();
        sumCheck.put("sumCheckNum",sumCheckNum);
        sumCheck.put("sumQuantity",sumQuantity);
        list.add(0,sumCheck);  //固定第0位为sumCheck
        return list;
    }

    @TargetDataSource(name = "webdata")
    public  List<Map<String,Object>> checkSeaNum(String selectSql){
        List<Map<String,Object>> seaList = new ArrayList<Map<String,Object>>();
        if(selectSql.equals("seaReal")){ // 计算当前时间应收数 海洋站实时数据
            seaList = jdbcTemplate.queryForList(STA_REAL_SQL);
            int sumCheckNum=0; //计算所有海洋站的应收数
            int sumQuantity=0; //计算所有海洋站的实际接受数     rate = checkNum/quntity
            for(Map<String,Object> hashMap: seaList){   //每小时60的数据
                String timeStr = hashMap.get("real_time").toString(); //	时间格式如：04/01 10:03
//                int indexHour = timeStr.indexOf(" ");
//                String hourStr = timeStr.substring(indexHour+1,indexHour+3); // 得到小时
                int index = timeStr.indexOf(":");
                String minStr = timeStr.substring(index+1,index+3); // 得到分钟
//                if(minStr.equals("00")){
//                    String insert = "INSERT INTO day_total (name,time,total,type) VALUES ('"+hashMap.get("name")+"','"+hashMap.get("real_time")+"','"+hashMap.get("quantity")+"',' sea_real');";
//                    jdbcTemplate.update(insert);
//                }
                int min = Integer.valueOf(minStr);// 标准为1分钟1个数据
                hashMap.put("checkNum", min+1);
                sumCheckNum = sumCheckNum+min;
                int qutyGot = Integer.valueOf(hashMap.get("quantity").toString());
                sumQuantity = qutyGot + sumQuantity;
            }

            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("sumCheckNum",sumCheckNum);
            sumCheck.put("sumQuantity",sumQuantity);
            seaList.add(0,sumCheck);
            return seaList;
        }

        if(selectSql.equals("seaHourly")){ // 计算应收数量 ；海洋站整点数据 ；数据每小时刷新一次，不会变
            seaList = jdbcTemplate.queryForList(STA_HOURLY_SQL);
            int sumCheckNum=0;
            int sumQuantity=0;
            for(Map<String,Object> hashMap: seaList){
                String quantity = hashMap.get("hourly").toString();
                int quty = Integer.valueOf(quantity);
                hashMap.put("checkNum", quty);
                sumCheckNum=sumCheckNum+quty;
                int qutyGot = Integer.valueOf(hashMap.get("quantity").toString());
                sumQuantity = qutyGot + sumQuantity;
            }
            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("sumCheckNum",sumCheckNum);
            sumCheck.put("sumQuantity",sumQuantity);
            seaList.add(0,sumCheck);
            return seaList;
        }

        if(selectSql.equals("seaPun")){ // 计算当前时间应收数 ；海洋站正点数据 2-8，8-14，14-20，20-2，分别应收1、2、3、4
            seaList = jdbcTemplate.queryForList(STA_PUN_SQL);
            int sumCheckNum=0;
            int sumQuantity=0;
            for(Map<String,Object> hashMap: seaList){
                String quantity = hashMap.get("pun").toString();
                int quty = Integer.valueOf(quantity);
                String timeStr = hashMap.get("pun_time").toString(); //	时间格式如：04/01 10:03
                int index = timeStr.indexOf(" ");
                String hourStr = timeStr.substring(index+1,index+3);
                int hour = Integer.valueOf(hourStr);
                int a;
                if(hour>=2 && hour<8){
                    a = quty/4;
                    hashMap.put("checkNum", a);
                }
                if(hour>=8 && hour<14){
                    a = 2*quty/4;
                    hashMap.put("checkNum", a);
                }
                if(hour>=14 && hour<20){
                    a = 3*quty/4;
                    hashMap.put("checkNum", a);
                }else{
                    a= 4*quty/4;
                    hashMap.put("checkNum", a);
                }
                sumCheckNum=sumCheckNum+a;
                int qutyGot = Integer.valueOf(hashMap.get("quantity").toString());
                sumQuantity = qutyGot + sumQuantity;
            }
            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("sumCheckNum",sumCheckNum);
            sumCheck.put("sumQuantity",sumQuantity);
            seaList.add(0,sumCheck);
            return seaList;
        }else {
            return seaList;
        }
    }

}
