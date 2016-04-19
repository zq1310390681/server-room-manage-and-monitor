package cn.edu.shou.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by light on 2016/4/18.
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
@Service
public class ZbxTriggerRepository {
    @Autowired
    JdbcTemplate jdbc;

    String triggerSql = "SELECT triggerid,description FROM triggers WHERE description LIKE '%load%'";

    public void updTrigger(){

    }
}
