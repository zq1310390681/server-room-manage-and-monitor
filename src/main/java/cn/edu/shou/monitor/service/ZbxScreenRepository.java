package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.impl.ZbxItemServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by light on 2016/4/5.
 */
@Repository
public class ZbxScreenRepository {
    /**从数据库******************************************************************************************/
    @Autowired
    JdbcTemplate jdbcTemplate ;

    static final String CPU_AVG15 = "select history.value*100 as fullrate,hosts.name\n" +
            " from history,items,hosts\n" +
            " where history.itemid = items.itemid\n" +
            " and items.key_ like '%system.cpu.load[percpu,avg15]%' \n" +
            " and hosts.hostid = items.hostid\n" +
            " group by items.hostid\n" +
            " order by clock desc";
    // disk name and value
    static final String USED_DISK = " select sum(history_uint.value) as sumUsed," + "hosts.name\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.name like 'used disk%'\n" +
            "  and items.flags = 4\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by items.hostid;";
    // disk only value total
    static final String TOTAL_DISK = " select sum(history_uint.value) as sumFree\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.name like 'free disk%'\n" +
            "  and items.flags = 4\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by items.hostid";
    static final String FREE_MEMORY = "select sum(history_uint.value) as sumFree,hosts.name\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.key_ like 'vm.memory.size[free]'\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by items.hostid";
    static final String TOTAL_MEMORY = "select sum(history_uint.value) as sumTotal,hosts.name" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.key_ like '%vm.memory.size[total]%'\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by items.hostid";

    public List<Map<String,Object>> getCpu(){
        String cpu = CPU_AVG15;
        List<Map<String,Object>> cpuList = new ArrayList<Map<String,Object>>();
        cpuList = jdbcTemplate.queryForList(cpu);
        JSONObject cpuJSON = new JSONObject();
        cpuJSON.put("cpu",cpuList);
        cpuJSON.put("dateTime",System.currentTimeMillis());
        return cpuList;
    }

    public List<Map<String,Object>> getCpuDis(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String itemidSqlUsed = "select * from items where items.key_ like '%system.cpu.load[percpu,avg15]%' and templateid <> ''and hostid > 10084;";
        List<Map<String,Object>> usedItemid = jdbcTemplate.queryForList(itemidSqlUsed);
        Map<String,Object> singleItemUsed = new HashMap<>();

        List<Map<String,Object>> usedTemp = new ArrayList<>();
        for(int i=0;i<usedItemid.size();i++){
            String usedItem = usedItemid.get(i).get("itemid").toString();
            String usedDiskSql = "SELECT history.value,hosts.name\n" +
                    "FROM history,hosts,items\n" +
                    "where history.itemid = "+ usedItem + "\n" +
                    "and items.itemid = history.itemid and items.hostid = hosts.hostid\n" +
                    "order by clock desc limit 1 ;";
            if(jdbcTemplate.queryForList(usedDiskSql).size()>0){
                singleItemUsed = jdbcTemplate.queryForMap(usedDiskSql);
                usedTemp.add(singleItemUsed);
            }
        }

        Map<String,Double>result = usedTemp.stream().collect(
                Collectors.groupingBy(
                        used -> used.get("name").toString(),
                        Collectors.summingDouble(t -> Double.parseDouble(t.get("value").toString()))
                ));
        ArrayList<Map<String,Object>> usedRate = new ArrayList<>();
        for (String key:result.keySet()){
            Map<String,Object> tempmap = new HashMap<String,Object>();
            tempmap.put("name",key);
            tempmap.put("fullrate",decimalFormat.format(100 * result.get(key)));
            usedRate.add(tempmap);
        }
        return usedRate;
    }

    public List<Map<String,Object>> getDisk(){
        String usedDisk = USED_DISK;
        List<Map<String,Object>> usedDiskList = new ArrayList<Map<String,Object>>();
        usedDiskList = jdbcTemplate.queryForList(usedDisk);

        String totalDisk = TOTAL_DISK;
        List<Map<String,Object>> totalDiskList = new ArrayList<Map<String,Object>>();
        totalDiskList = jdbcTemplate.queryForList(totalDisk);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        int i,n=usedDiskList.size();
        for (i=0;i<n;i++){
            Object used = usedDiskList.get(i).get("sumUsed");
            Object free = totalDiskList.get(i).get("sumFree");
            double u = Double.parseDouble(String.valueOf(used));
            double f = Double.parseDouble(String.valueOf(free));
            Double rate = u*100/(u+f);
            usedDiskList.get(i).remove("sumUsed");
            usedDiskList.get(i).put("fullrate", decimalFormat.format(rate));
        }
        JSONObject cpuJSON = new JSONObject();
        cpuJSON.put("disk",usedDiskList);
        cpuJSON.put("dateTime",System.currentTimeMillis());
        return usedDiskList;
    }

    public List<Map<String,Object>> getDiskDis(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String itemidSqlUsed = "select itemid from items where items.flags = 4\n" +
                "and items.name like 'used disk%'and templateid <> ''and hostid > 10084";
        List<Map<String,Object>> usedItemid = jdbcTemplate.queryForList(itemidSqlUsed);
        Map<String,Object> singleItemUsed = new HashMap<>();

        String itemidSqlTotal = "select itemid from items where items.flags = 4\n" +
                "and items.name like 'total disk%'and templateid <> ''and hostid > 10084";
        List<Map<String,Object>> totalItemid = jdbcTemplate.queryForList(itemidSqlTotal);
        Map<String,Object> singleItemTotal = new HashMap<>();

        List<Map<String,Object>> usedTemp = new ArrayList<>();
        List<Map<String,Object>> totalTemp = new ArrayList<>();
        for(int i=0;i<totalItemid.size();i++){
            String usedItem = usedItemid.get(i).get("itemid").toString();
            String freeItem = totalItemid.get(i).get("itemid").toString();
            String usedDiskSql = "SELECT history_uint.value,hosts.name\n" +
                    "FROM history_uint,hosts,items\n" +
                    "where history_uint.itemid = "+ usedItem + "\n" +
                    "and items.itemid = history_uint.itemid and items.hostid = hosts.hostid\n" +
                    "order by clock desc limit 1 ;";
            if(jdbcTemplate.queryForList(usedDiskSql).size()>0){
                singleItemUsed = jdbcTemplate.queryForMap(usedDiskSql);
                usedTemp.add(singleItemUsed);
            }

            String totalDiskSql = "SELECT history_uint.value,hosts.name\n" +
                    "FROM history_uint,hosts,items\n" +
                    "where history_uint.itemid = "+ freeItem + "\n" +
                    "and items.itemid = history_uint.itemid and items.hostid = hosts.hostid\n" +
                    "order by clock desc limit 1 ;";
            if(jdbcTemplate.queryForList(totalDiskSql).size()>0) {
                singleItemTotal = jdbcTemplate.queryForMap(totalDiskSql);
                totalTemp.add(singleItemTotal);
            }
        }

        Map<String,Double>result = usedTemp.stream().collect(
                Collectors.groupingBy(
                        used -> used.get("name").toString(),
                        Collectors.summingDouble(t -> Double.parseDouble(t.get("value").toString()))
                ));
        Map<String,Double>result2 = totalTemp.stream().collect(
                Collectors.groupingBy(
                        used->used.get("name").toString(),
                        Collectors.summingDouble(t->Double.parseDouble(t.get("value").toString()))
                ));
        ArrayList<Map<String,Object>> usedRate = new ArrayList<>();
        for (String key:result.keySet()){
            Map<String,Object> tempmap = new HashMap<String,Object>();
            tempmap.put("name",key);
            tempmap.put("fullrate",decimalFormat.format(100 * result.get(key)/result2.get(key)));
            usedRate.add(tempmap);
        }
        return usedRate;
    }

    public List<Map<String,Object>> getRam(){
        String freeMemory = FREE_MEMORY;
        List<Map<String,Object>> freeMemoryList = new ArrayList<Map<String,Object>>();
        freeMemoryList = jdbcTemplate.queryForList(freeMemory);

        String totalMemory = TOTAL_MEMORY;
        List<Map<String,Object>> totalMemoryList = new ArrayList<Map<String,Object>>();
        totalMemoryList = jdbcTemplate.queryForList(totalMemory);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        int i,n = freeMemoryList.size();
        for (i=0;i<n;i++){
            Object free = freeMemoryList.get(i).get("sumFree");
            Object total = totalMemoryList.get(i).get("sumTotal");
            double f= Double.parseDouble(String.valueOf(free));
            double t =Double.parseDouble(String.valueOf(total));
            Double rate = t*100/f;
            freeMemoryList.get(i).remove("sumFree");
            freeMemoryList.get(i).put("fullrate", decimalFormat.format(rate));
        }
        JSONObject ramJSON = new JSONObject();
        ramJSON.put("disk",freeMemoryList);
        ramJSON.put("dateTime",System.currentTimeMillis());
        return freeMemoryList;
    }

    public List<Map<String,Object>> getRamDis(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String itemidSqlFree = "select * from items where items.key_ like '%vm.memory.size[free]%'and templateid <> ''and hostid > 10084;";
        List<Map<String,Object>> usedItemid = jdbcTemplate.queryForList(itemidSqlFree);
        Map<String,Object> singleItemFree = new HashMap<>();

        String itemidSqlTotal =  "select * from items where items.key_ like '%vm.memory.size[total]%' and templateid <> ''and hostid > 10084;";
        List<Map<String,Object>> totalItemid = jdbcTemplate.queryForList(itemidSqlTotal);
        Map<String,Object> singleItemTotal = new HashMap<>();

        List<Map<String,Object>> usedTemp = new ArrayList<>();
        List<Map<String,Object>> totalTemp = new ArrayList<>();
        for(int i=0;i<totalItemid.size();i++){
            String usedItem = usedItemid.get(i).get("itemid").toString();
            String totalItem = totalItemid.get(i).get("itemid").toString();
            String usedDiskSql = "SELECT history_uint.value,hosts.name\n" +
                    "FROM history_uint,hosts,items\n" +
                    "where history_uint.itemid = "+ usedItem + "\n" +
                    "and items.itemid = history_uint.itemid and items.hostid = hosts.hostid\n" +
                    "order by clock desc limit 1 ;";
            if(jdbcTemplate.queryForList(usedDiskSql).size()>0){
                singleItemFree = jdbcTemplate.queryForMap(usedDiskSql);
                usedTemp.add(singleItemFree);
            }

            String totalDiskSql = "SELECT history_uint.value,hosts.name\n" +
                    "FROM history_uint,hosts,items\n" +
                    "where history_uint.itemid = "+ totalItem + "\n" +
                    "and items.itemid = history_uint.itemid and items.hostid = hosts.hostid\n" +
                    "order by clock desc limit 1 ;";
            if(jdbcTemplate.queryForList(totalDiskSql).size()>0) {
                singleItemTotal = jdbcTemplate.queryForMap(totalDiskSql);
                totalTemp.add(singleItemTotal);
            }
        }

        Map<String,Double>result = usedTemp.stream().collect(
                Collectors.groupingBy(
                        used -> used.get("name").toString(),
                        Collectors.summingDouble(t -> Double.parseDouble(t.get("value").toString()))
                ));
        Map<String,Double>result2 = totalTemp.stream().collect(
                Collectors.groupingBy(
                        used->used.get("name").toString(),
                        Collectors.summingDouble(t->Double.parseDouble(t.get("value").toString()))
                ));
        ArrayList<Map<String,Object>> usedRate = new ArrayList<>();
        for (String key:result.keySet()){
            Map<String,Object> tempmap = new HashMap<String,Object>();
            tempmap.put("name",key);
            tempmap.put("fullrate",decimalFormat.format(100-(100 * result.get(key)/result2.get(key))));
            usedRate.add(tempmap);
        }
        return usedRate;
    }


    /**ZBX API******************************************************************************************/
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    //memory 获取内存使用率
    public JSONObject getMemoryUsage(String hostIds) {
        // memory ram
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItemMemFree = zbxItem.getSimpleItem(hostIds, "vm.memory.size","").getJSONArray("result");
        JSONObject memory= new JSONObject();
        if(zbxItemMemFree.length()!=0) { // 跳过没有Memory的host
            memory = zbxItemMemFree.getJSONObject(0);
            Object memoryOne = memory.get("lastvalue");//
            Object memoryTwo = zbxItemMemFree.getJSONObject(1).get("lastvalue");
            float mem1 = Float.parseFloat(String.valueOf(memoryOne));
            float mem2 = Float.parseFloat(String.valueOf(memoryTwo));
            float memoryUsage;
            if (mem1 <= mem2) {
                if (mem2 == 0) {
                    memoryUsage = 0;
                } else {
                    memoryUsage = mem1 / mem2 * 100;
                }
            } else {
                if (mem1 == 0) {
                    memoryUsage = 0;
                } else {
                    memoryUsage = mem2 / mem1 * 100;
                }
            }
            memory.put("fullrate", decimalFormat.format(memoryUsage));
            memory.remove("lastvalue");
            memory.remove("itemid");
        }
            return memory;

    }
    public JSONArray sendMemory(){
        JSONArray ram = new JSONArray();
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();  //array of hostid
        ArrayList<String> hostNamesArray = ZbxHostServiceImpl.HostArray.getHostName();//array of hostname
        int n = hostIdsArray.size();
        for(int i=0;i<n;i++){
            JSONObject nameAndMem = this.getMemoryUsage(hostIdsArray.get(i));// 根据hostid得到value
            if(!nameAndMem.toString().equals("{}")) { //跳过空数据
                String names = hostNamesArray.get(i);
                nameAndMem.put("name", names); // 添加hostname进入JSON
                ram.put(i, nameAndMem);
            }
        }
        JSONObject ramResult = new JSONObject();
        ramResult.put("ram",ram);
//        ramResult.put("dateTime",System.currentTimeMillis());
//        return ramResult.toString().replace("null,","");
        return ram;
    }

    // cpu 获取CPU的使用情况
    public JSONObject getCpuUsage(String hostIds) {
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItemCpu = zbxItem.getSimpleItem(hostIds,"[percpu,avg15]","").getJSONArray("result");
        JSONObject cpu=new JSONObject();
        String LastVal="";
        if(zbxItemCpu.length()!=0) {
            LastVal = zbxItemCpu.getJSONObject(0).get("lastvalue").toString();
            double cpuUsage = Double.parseDouble(String.valueOf(LastVal)) * 100;
            cpu.put("fullrate", decimalFormat.format(cpuUsage));
        }
        return cpu;
    }
    public JSONArray sendCpu(){
        JSONArray cpu = new JSONArray();
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        ArrayList<String> hostNamesArray = ZbxHostServiceImpl.HostArray.getHostName();
        for(int i=0;i<hostIdsArray.size();i++){
            JSONObject nameAndCpu = getCpuUsage(hostIdsArray.get(i));
            if(!nameAndCpu.toString().equals("{}")) {
                String names = hostNamesArray.get(i);
                nameAndCpu.put("name", names);
                cpu.put(i, nameAndCpu);
            }
        }
        JSONObject cpuResult = new JSONObject();
        cpuResult.put("cpu",cpu);
        return cpu;
    }

    //disk 获取disk的使用情况
    public JSONObject getDiskUsage(String hostIds){
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItemDisk= zbxItem.getSimpleItem(hostIds,",pfree","").getJSONArray("result");
        JSONObject zbxItemDisks= new JSONObject();
        if(zbxItemDisk.length()!=0) {
            double sum = 0;
            for (int i = 0; i < zbxItemDisk.length(); i++) { //一个host中间可能包括若干个磁盘，计算总的使用率
                zbxItemDisks = zbxItemDisk.getJSONObject(i);
                Object disk = zbxItemDisks.get("lastvalue");
                double diskUsageFlt;
                diskUsageFlt = Double.parseDouble(String.valueOf(disk));
                sum = diskUsageFlt + sum;
            }

            double diskUsage = sum / zbxItemDisk.length();
            zbxItemDisks.put("fullrate", decimalFormat.format(diskUsage));
            zbxItemDisks.remove("itemid");
            zbxItemDisks.remove("lastvalue");
        }
        return zbxItemDisks;
    }
    public JSONArray sendDisk(){
        JSONArray diskJsa = new JSONArray();
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        ArrayList<String> hostNamesArray = ZbxHostServiceImpl.HostArray.getHostName();
        int n = hostIdsArray.size();
        for(int i=0;i<n;i++){
            JSONObject nameAndDisk = this.getDiskUsage(hostIdsArray.get(i));
            if(!nameAndDisk.toString().equals("{}")) {
                String names = hostNamesArray.get(i);
                nameAndDisk.put("name", names);
                diskJsa.put(i, nameAndDisk);
            }
        }
        JSONObject ramResult = new JSONObject();
        ramResult.put("disk",diskJsa);
//        ramResult.put("dateTime",System.currentTimeMillis());
//        return ramResult.toString().replace("null,","");
        return diskJsa;
    }

    //ping 获取ping的使用情况
    public JSONObject getPingLive(String hostIds){
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItemPing= zbxItem.getSimpleItem(hostIds,"","ICMP ping").getJSONArray("result");
        JSONObject ping= new JSONObject();
        if(zbxItemPing.length()!=0) {
            ping = zbxItemPing.getJSONObject(0);
            Object pingAlive = ping.get("lastvalue");
            ping.put("state",pingAlive.toString());
    }
        ping.remove("lastvalue");
        ping.remove("itemid");
        return ping;
    }
    public String sendPing(){
        JSONArray pingJsa = new JSONArray();
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        ArrayList<String> hostNamesArray = ZbxHostServiceImpl.HostArray.getHostName();
        int n = hostIdsArray.size();
        for(int i=0;i<n;i++){
            JSONObject nameAndPing = this.getPingLive(hostIdsArray.get(i));
            if(!nameAndPing.toString().equals("{}")) {
                String names = hostNamesArray.get(i);
                nameAndPing.put("target", names);
                nameAndPing.put("source","预报中心");
                pingJsa.put(i, nameAndPing);
            }
        }
        JSONObject pingResult = new JSONObject();
        pingResult.put("Ping",pingJsa);
        pingResult.put("dateTime",System.currentTimeMillis());
        return pingResult.toString().replace("null,","");
    }

    // 交换机流量 input or output
    public JSONObject getPutNet(String hostIds,String inputOrOutput){
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItemPut= zbxItem.getSimpleItem(hostIds,inputOrOutput,"").getJSONArray("result");
        JSONObject input= new JSONObject();
        if(zbxItemPut.length()!=0) {
            input = zbxItemPut.getJSONObject(0);
            Object pingAlive = input.get("lastvalue");
            input.put(inputOrOutput,pingAlive.toString());
        }
        input.remove("lastvalue");
        input.remove("itemid");
        return input;
    }
    public String sendPutNet(String inputOrOutput){
        JSONArray putJsa = new JSONArray();
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        ArrayList<String> hostNamesArray = ZbxHostServiceImpl.HostArray.getHostName();
        int n = hostIdsArray.size();
        for(int i=0;i<n;i++){
            JSONObject nameAndPut = this.getPutNet(hostIdsArray.get(i), inputOrOutput);
            if(!nameAndPut.toString().equals("{}")) {
                String names = hostNamesArray.get(i);
                nameAndPut.put("name", names);
                putJsa.put(i, nameAndPut);
            }
        }
        JSONObject putResult = new JSONObject();
        putResult.put(inputOrOutput,putJsa);
        putResult.put("dateTime",System.currentTimeMillis());
        return putResult.toString().replace("null,","");
    }

    //获取cpu，内存，存储联合查询函数
    public JSONObject getUnionUsage(String type,String hostIds){
        String searchStr="";
        if (type.equals("memory")){//内存
            searchStr="vm.memory.size";
        }else if(type.equals("cpu")){//cpu
            searchStr="[percpu,avg15]";
        }else {//disk
            searchStr=",pfree";
        }
        ZbxItemServiceImpl zbxItem = new ZbxItemServiceImpl();
        JSONArray zbxItems=zbxItem.getSimpleItem(hostIds,searchStr,"").getJSONArray("result");

        JSONObject results = new JSONObject();
        Object trueVal=null;
        if(zbxItems.length()!=0) {
            double sum = 0;
            for (int i = 0; i < zbxItems.length(); i++) { //一个host中间可能包括若干个磁盘，计算总的使用率
                results = zbxItems.getJSONObject(i);
                trueVal = results.get("lastvalue");
                double diskUsageFlt;
                diskUsageFlt = Double.parseDouble(String.valueOf(trueVal));
                sum = diskUsageFlt + sum;
            }

            double diskUsage = sum / results.length();
            double cpuUsage = Double.parseDouble(String.valueOf(trueVal)) * 100;
            results.put("fullrate", decimalFormat.format(cpuUsage));
            results.remove("lastvalue");
            results.remove("itemid");
        }
        return results;
    }
}