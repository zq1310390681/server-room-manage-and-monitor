package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.spring.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/18.
 *
报警规则设置，从数据库直接该；两种情况，1)已经创建过的要批量该，2）为创建过的只需改template对应的trigger。
两种情况合并，改一次两个都改

 正向
 items 表的itemid 关联functions表的itemid ，得到了对应的triggerid，
 在triggers表的expression里面修改阈值。

 items.hostid = templateid
 items.itemid = functions.itemid
 functions.triggerid = triggers.triggerid
 triggers.expression

 逆向
 从triggers 表的description 找到监控的像 比如load
 triggers表的triggerid 和functions表的functonid 对应
 functions.itemid 对应于 items.itemid
 items.itemid对应hostid

 hosts_templates -->
 hostid,itemid,key_,name_
 functions,triggers
 triggers,expression
 */

@Repository
public class ZbxTriggerRepository {
    @Autowired
    JdbcTemplate jdbc;

    @TargetDataSource(name = "zabbix")
    public void updTrigger(String itemKeyToSearch,String thresholdNew){
        // 得到字段itemid，hostid，item_name,item_key,triggerid,expression
        String triggerSql;
        if(itemKeyToSearch.equals("memory usage is not enough")){
            triggerSql = "SELECT i.itemid,i.hostid,i.name,i.key_,fun.triggerid,trg.expression\n" +
                "FROM items as i,hosts_templates as ht,functions as fun,triggers as trg\n" +
                "where i.hostid = ht.hostid \n" +
                "and trg.description = '"+itemKeyToSearch+"'\n" +
                "and i.itemid = fun.itemid\n" +
                "and trg.triggerid = fun.triggerid\n" +
                "and ht.hostid>10084;";
        }else{
            triggerSql = "SELECT i.itemid,i.hostid,i.name,i.key_,fun.triggerid,trg.expression\n" +
                "FROM items as i,hosts_templates as ht,functions as fun,triggers as trg\n" +
                "where i.hostid = ht.hostid \n" +
                "and i.key_ = '"+itemKeyToSearch+"'\n" +
                "and i.itemid = fun.itemid\n" +
                "and trg.triggerid = fun.triggerid\n" +
                "and ht.hostid>10084;";
        }
        List<Map<String,Object>> list = jdbc.queryForList(triggerSql);
        String expression = list.get(1).get("expression").toString(); // 因为是一个模版出来的，所有操作符都一样
        int a = expression.lastIndexOf("}");
        String operator = expression.substring(a+1,a+2); //得到运算符

        List<String> str = new ArrayList<>();
        for(Map<String,Object> map : list){
            String expressionOld = map.get("expression").toString();
            String thresholdOld = expressionOld.substring(a+2,expressionOld.length());
            String expressionNew = expressionOld.replace(operator+thresholdOld,operator + thresholdNew);
            map.put("expression",expressionNew);
            String triggerid = map.get("triggerid").toString();
            String updTriggers = "update triggers set expression = '"+ expressionNew + "' where triggerid =" + triggerid + ";\n";
            str.add(updTriggers);
//            jdbc.update(updTriggers);
        }
        String[] sql = str.toArray(new String[str.size()]);
        jdbc.batchUpdate(sql);
    }
}
