package cn.edu.shou.monitor.web;

import java.util.List;
import java.util.Map;

/**
 * Created by sqhe18 on 2016/4/23.
 * 常用函数
 */
public class commonFunction {

    //导出表格头和列
    public List<Map<String,String>>getTableHeadAndCol(String head,String col){
        if(head!="" && col!=""){
            String heads[]=head.split(",");
            String cols[]=col.split(",");
        }
        return null;
    }
}
