package cn.edu.shou.monitor.service;
import cn.edu.shou.monitor.spring.TargetDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.util.*;

/**
 * Created by light on 2016/4/4.
 */
@Repository
public class ZDataReceiveRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static final String RADAR_SQL = "SELECT common_radar.name,common_radar.time,common_radar.quantity as fenzi,radar_quantity.day_quantity,common_radar.parent_name\n" +
            "from common_radar,radar_quantity where common_radar.name = radar_quantity.name;";
    static final String XRADAR_SQL = "SELECT xradar.name,xradar.time,xradar.quantity as fenzi,xradar_quantity.day_quantity,xradar.parent_name from xradar,xradar_quantity\n" +
            "where xradar.name = xradar_quantity.name and xradar.time != '--';";
    static final String VOS_SQL = "SELECT single_vos.name,single_vos.time,single_vos.quantity as fenzi,vos_quantity.day_quantity from single_vos,vos_quantity\n" +
            "where single_vos.name = vos_quantity.name and single_vos.time != '--';";
    static final String BUOY_SQL = "SELECT buoy.name,buoy.time,buoy.quantity as fenzi,buoy_quantity.day_quantity from buoy,buoy_quantity\n" +
            "where buoy.name = buoy_quantity.buoy_name and buoy.time != '--';";
    static final String STA_REAL_SQL = "SELECT sea_station.name,sea_station.real_time,sea_station.real_quantity as fenzi,sea_quantity.real,sea_station.parent_name\n" +
            "from sea_station,sea_quantity where sea_station.name = sea_quantity.name and sea_station.real_time != '--';";
    static final String STA_HOURLY_SQL = "SELECT sea_station.name,sea_station.hourly_time,sea_station.hourly_quantity as fenzi,sea_quantity.hourly,sea_station.parent_name " +
            "from sea_station,sea_quantity where sea_station.name = sea_quantity.name and sea_station.hourly_time != '--';";
    static final String STA_PUN_SQL = "SELECT sea_station.name,sea_station.pun_time,sea_station.pun_quantity as fenzi,sea_quantity.pun,sea_station.parent_name \n" +
            "from sea_station,sea_quantity where sea_station.name = sea_quantity.name and sea_station.pun_time != '--';";

    Calendar cal = Calendar.getInstance();
    int h = cal.get(Calendar.HOUR_OF_DAY);
    int m = cal.get(Calendar.MINUTE);

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
            hashMap.put("fenmu", a);
            int qutyGot = Integer.valueOf(hashMap.get("fenzi").toString());
            if(qutyGot>a){
                qutyGot = a;
            }
            hashMap.put("fenzi", qutyGot);
            sumCheckNum = sumCheckNum+a;
            sumQuantity = qutyGot + sumQuantity;
            hashMap.remove("time");
            hashMap.remove("day_quantity");
            hashMap.put("time",String.valueOf(h)+":"+String.valueOf(m));
        }
        Map<String,Object> sumCheck = new HashMap<String,Object>();
        sumCheck.put("fenmuAll",sumCheckNum);  //fenmu
        sumCheck.put("fenziAll",sumQuantity);  //fenzi
        list.add(0,sumCheck);  //固定第0位为sumCheck
        return list;
    }

    @TargetDataSource(name = "webdata")
    public  List<Map<String,Object>> checkSeaNum(String selectSql){
        List<Map<String,Object>> seaList = new ArrayList<Map<String,Object>>();
        if(selectSql.equals("seaReal")){ // 计算当前时间应收数 海洋站实时数据
            seaList = jdbcTemplate.queryForList(STA_REAL_SQL);
            int fenmu=0;
            int fenzi=0;
            for(Map<String,Object> hashMap: seaList){   //每小时60的数据
                String timeStr = hashMap.get("real_time").toString(); //	时间格式如：04/01 10:03
                int index = timeStr.indexOf(":");
                String minStr = timeStr.substring(index+1,index+3); // 得到分钟
                int min = Integer.valueOf(minStr);// 标准为1分钟1个数据
                hashMap.put("fenmu", min+1);
                int qutyGot = Integer.valueOf(hashMap.get("fenzi").toString());
                if(qutyGot> min+1){
                    qutyGot =  min+1;
                }
                hashMap.put("fenzi",qutyGot);
                fenmu = fenmu+min;
                fenzi = qutyGot + fenzi;
            }
            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("fenmuAll",fenmu);  // 分母
            sumCheck.put("fenziAll",fenzi);  // 分子
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
                hashMap.put("fenmu", quty);
                int qutyGot = Integer.valueOf(hashMap.get("fenzi").toString());
                if(qutyGot>quty){
                    qutyGot=quty;
                }
                hashMap.put("fenzi", qutyGot);
                sumCheckNum=sumCheckNum+quty;
                sumQuantity = qutyGot + sumQuantity;
            }
            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("fenmuAll",sumCheckNum);
            sumCheck.put("fenziAll",sumQuantity);
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
                    hashMap.put("fenmu", a);
                }
                if(hour>=8 && hour<14){
                    a = 2*quty/4;
                    hashMap.put("fenmu", a);
                }
                if(hour>=14 && hour<20){
                    a = 3*quty/4;
                    hashMap.put("fenmu", a);
                }else{
                    a= 4*quty/4;
                    hashMap.put("fenmu", a);
                }
                int qutyGot = Integer.valueOf(hashMap.get("fenzi").toString());
                if(qutyGot>a){
                    qutyGot=a;
                }
                hashMap.put("fenzi", qutyGot);
                sumCheckNum=sumCheckNum+a;
                sumQuantity = qutyGot + sumQuantity;
            }
            Map<String,Object> sumCheck = new HashMap<String,Object>();
            sumCheck.put("fenmuAll",sumCheckNum);
            sumCheck.put("fenziAll",sumQuantity);
            seaList.add(0,sumCheck);
            return seaList;
        }else {
            return seaList;
        }
    }

    // 定时执行 不用这个了
    @TargetDataSource(name = "webdata")
    public  void updStationNum(){
        String selectSql = "SELECT SUM(real_quantity) AS sum_sta, parent_name FROM webdata.sea_station GROUP BY parent_name;";
        List<Map<String,Object>> allStaNum = new ArrayList<Map<String,Object>>();
        allStaNum = jdbcTemplate.queryForList(selectSql);

        if(allStaNum.size()>0) {
            String[] stationName = {"南通", "上海", "宁波", "温州", "宁德", "厦门"};
            Calendar cal = Calendar.getInstance();
            int hour=cal.get(Calendar.HOUR);
            for (Map map : allStaNum) {
                for (String name : stationName) {
                    if (map.toString().contains(name)) {
                        String updSql = "UPDATE center_station SET quantity = '" + map.get("sum_sta") + "' WHERE station_name = '" + name + "'and hour =" + hour + ";";
                        jdbcTemplate.update(updSql);
                    }
                }
            }
        }
    }

    @TargetDataSource(name = "webdata")  // schedule
    public void updSeaStaNum(){
        String dailyInitSql = "UPDATE sea_quantity SET last_real=0, current_num=0;"; //一天开始的初始化
        String hourlyInitSql = "UPDATE sea_quantity SET last_real=0;"; //每小时初始化

        Date date = new Date();
        DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
        String currentTime = df3.format(date);
        if(currentTime.equals("00:00:00")){
            jdbcTemplate.update(dailyInitSql);
        }
        //sql sea station
        //sql sea quantity
        String sql = "SELECT sea_station.name,sea_quantity.last_real,sea_quantity.current_num,sea_station.real_quantity,sea_station.real_time,sea_station.parent_name\n" +
                "FROM webdata.sea_quantity,sea_station WHERE sea_station.name = sea_quantity.name;";
        //compare last and real quantity
        List<Map<String,Object>> compareList = jdbcTemplate.queryForList(sql);
        for(Map element : compareList){
            int lastValue = Integer.parseInt(element.get("last_real").toString());
            int realQuantity = Integer.parseInt(element.get("real_quantity").toString());
            int currentNumOld = Integer.parseInt(element.get("current_num").toString());
            int currentNumNew;
            String name = element.get("name").toString();
            if(lastValue<=realQuantity){
                String updLastValue = "UPDATE sea_quantity SET last_real="+realQuantity+" WHERE name = '"+name+"';";
                jdbcTemplate.update(updLastValue);
            }else{                   //last + currentNumOld = current
                currentNumNew = lastValue + currentNumOld; //需要新加上去的
                String updTwoValue ="UPDATE sea_quantity SET last_real="+ realQuantity +",current_num="+ currentNumNew +" WHERE name ='"+name+"';";
                jdbcTemplate.update(updTwoValue);
            }
        }
    }

    //得到每个海洋站当前的统计数据
    @TargetDataSource(name = "webdata")
    public List<Map<String,Object>> getStationNum(){
        String sql = "SELECT quty.name,quty.current_num,sta.hourly_quantity,sta.pun_quantity,sta.parent_name,quty.real, quty.hourly,quty.pun\n" +
                "FROM sea_station as sta,sea_quantity as quty WHERE quty.name = sta.name;";
        List<Map<String,Object>> listSta = jdbcTemplate.queryForList(sql);

        int fenzi = 0;
        int fenmu = 0;
        for(Map<String,Object> element : listSta){
            int realSum = Integer.parseInt(element.get("current_num").toString());
            int hourly = Integer.parseInt(element.get("hourly_quantity").toString());
            int pun = Integer.parseInt(element.get("pun_quantity").toString());
            int realNeed = Integer.parseInt(element.get("real").toString());
            int hourNeed = Integer.parseInt(element.get("hourly").toString());
            int punNeed = Integer.parseInt(element.get("pun").toString());
//            int stationSum = realSum + hourly + pun;  //正式上线用这个
            int stationSum = (realSum + 60 * h) + hourly + pun;  //测试展示数据,上线去掉
            int stationNeed = Math.round(m * realNeed/1440 + (realNeed * h/24)) + Math.round(hourNeed * h/24) + Math.round(punNeed * h/24);
            if(stationSum>stationNeed){
                stationSum=stationNeed;
            }

            fenzi = fenzi + stationSum;
            fenmu = fenmu + stationNeed;

            element.put("fenzi",stationSum);
            element.put("fenmu", stationNeed);
            element.put("time",String.valueOf(h)+":"+String.valueOf(m));
            element.remove("current_num");
            element.remove("hourly_quantity");
            element.remove("pun_quantity");
            element.remove("real");
            element.remove("hourly");
            element.remove("pun");
        }
        Map<String,Object> check = new HashMap<>();
        check.put("fenziAll",fenzi);
        check.put("fenmuAll",fenmu);
        listSta.add(0,check);
        return listSta;
    }

    @TargetDataSource(name = "webdata")
    public Map<String,Object> getDbStatus(){
        String sql = "SELECT status FROM db_status ORDER BY time DESC limit 1 ;";
        Map<String,Object> status = jdbcTemplate.queryForMap(sql);
        return status;
    }

    @TargetDataSource(name = "webdata")
    public Map<String,Object> getDbDataNum(){
        String sql = "SELECT total FROM day_total WHERE id = 1 ;";
        return  jdbcTemplate.queryForMap(sql);
    }

    @TargetDataSource(name = "webdata")
    public List<Map<String,Object>> getGpsInfo(){
        String sql = "SELECT name,fenzi,fenmu,time FROM gps;";
        List<Map<String,Object>> gpsInfo = jdbcTemplate.queryForList(sql);
        return gpsInfo;
    }

    //network real-time data
    @TargetDataSource(name = "webdata")
    public  List<Map<String,Object>> getNetworkRealData(){
        String sql = "SELECT s.station_name,s.type,s.parent_name,n.ob_date,n.wind_direction,n.wind_speed,n.air_temp,n.air_pressure,n.humidity,n.water_temp,\n" +
                "n.salinity,n.tide,n.wv_height3,n.wv_height10,n.wv_height_max \n" +
                "FROM webdata.network_data n,station_all s \n" +
                "WHERE s.station_num IN (SELECT station_num FROM webdata.station_all \n" +
                "WHERE flag = 1 AND\n" +
                "station_num NOT IN (SELECT view_object FROM webdata.network_error GROUP BY view_object)) \n" +
                "AND s.station_num =n.station;";
        return jdbcTemplate.queryForList(sql);
    }

    //network error
    @TargetDataSource(name = "webdata")
    public  List<Map<String,Object>> getNetworkError(){
        String sql = "select s.station_name,s.parent_name,e.* from station_all s,network_error e\n" +
                "where s.station_num in (\n" +
                "SELECT view_object FROM network_error\n" +
                "WHERE (happen_time,view_object) IN \n" +
                "(SELECT MAX(happen_time),view_object FROM network_error GROUP BY view_object))\n" +
                "and s.station_num = e.view_object and s.flag = 1;";
        return jdbcTemplate.queryForList(sql);
    }
}
