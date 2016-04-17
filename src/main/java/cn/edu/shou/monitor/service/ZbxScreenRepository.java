package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.spring.TargetDataSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/5.
 */
@Repository
public class ZbxScreenRepository {
    @Autowired
    JdbcTemplate jdbcTemplate ;

    static final String CPU_AVG15 = "select history.value*100 as fullrate,hosts.name\n" +
            " from history,items,hosts\n" +
            " where history.itemid = items.itemid\n" +
            " and items.key_ like '%system.cpu.load[percpu,avg15]%' \n" +
            " and hosts.hostid = items.hostid\n" +
            " group by hosts.hostid\n" +
            " order by clock desc";
    // disk name and value
    static final String USED_DISK = " select sum(history_uint.value) as sumUsed," + "hosts.name\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.name like 'used disk%'\n" +
            "  and items.flags = 4\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by hosts.hostid;";
    // disk only value total
    static final String TOTAL_DISK = " select sum(history_uint.value) as sumFree\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.name like 'free disk%'\n" +
            "  and items.flags = 4\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by hosts.hostid";
    static final String FREE_MEMORY = "  select sum(history_uint.value) as sumFree,hosts.name\n" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.key_ like 'vm.memory.size[free]'\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by hosts.hostid";
    static final String TOTAL_MEMORY = " select sum(history_uint.value) as sumTotal,hosts.name" +
            "  from history_uint,items,hosts\n" +
            "  where history_uint.itemid = items.itemid\n" +
            "  and items.key_ like '%vm.memory.size[total]%'\n" +
            "  and hosts.hostid = items.hostid\n" +
            "  group by hosts.hostid";

    @TargetDataSource(name = "zabbix")
    public List<Map<String,Object>> getCpu(){
        String cpu = CPU_AVG15;
        List<Map<String,Object>> cpuList = new ArrayList<Map<String,Object>>();
        cpuList = jdbcTemplate.queryForList(cpu);
        JSONObject cpuJSON = new JSONObject();
        cpuJSON.put("cpu",cpuList);
        cpuJSON.put("dateTime",System.currentTimeMillis());
        return cpuList;
    }

    @TargetDataSource(name = "zabbix")
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
    @TargetDataSource(name = "zabbix")
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
        JSONObject cpuJSON = new JSONObject();
        cpuJSON.put("disk",freeMemoryList);
        cpuJSON.put("dateTime",System.currentTimeMillis());
        return freeMemoryList;
    }



}

/**
 *     static final String CPU_AVG15 = "select history.itemid,history.value*100 as value,history.clock,hosts.hostid,hosts.name\n" +
             " from history,items,hosts\n" +
             " where history.itemid = items.itemid\n" +
             " and items.key_ like '%system.cpu.load[percpu,avg15]%' \n" +
             " and hosts.hostid = items.hostid\n" +
             " group by hosts.hostid\n" +
             " order by clock desc";
     static final String USED_DISK = " select history_uint.itemid,sum(history_uint.value) as sum," +
             "history_uint.clock,hosts.hostid,hosts.name,items.key_\n" +
             "  from history_uint,items,hosts\n" +
             "  where history_uint.itemid = items.itemid\n" +
             "  and items.key_ like '%,used%'\n" +
             "  and items.flags = 4\n" +
             "  and hosts.hostid = items.hostid\n" +
             "  group by hosts.hostid;";
     static final String TOTAL_DISK = " select history_uint.itemid,sum(history_uint.value) as sum," +
             "history_uint.clock,hosts.hostid,hosts.name,items.key_\n" +
             "  from history_uint,items,hosts\n" +
             "  where history_uint.itemid = items.itemid\n" +
             "  and items.key_ like '%,total%'\n" +
             "  and items.flags = 4\n" +
             "  and hosts.hostid = items.hostid\n" +
             "  group by hosts.hostid";
     static final String FREE_MEMORY = "  select history_uint.itemid,sum(history_uint.value) as sum," +
             "history_uint.clock,hosts.hostid,hosts.name,items.key_\n" +
             "  from history_uint,items,hosts\n" +
             "  where history_uint.itemid = items.itemid\n" +
             "  and items.key_ like 'vm.memory.size[free]'\n" +
             "  and hosts.hostid = items.hostid\n" +
             "  group by hosts.hostid";
     static final String TOTAL_MEMORY = " select history_uint.itemid,sum(history_uint.value) as sum," +
             "history_uint.clock,hosts.hostid,hosts.name,items.key_\n" +
             "  from history_uint,items,hosts\n" +
             "  where history_uint.itemid = items.itemid\n" +
             "  and items.key_ like '%vm.memory.size[total]%'\n" +
             "  and hosts.hostid = items.hostid\n" +
             "  group by hosts.hostid";
 */
