package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.util.SshShell;
import cn.edu.shou.monitor.util.UpperCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by light on 2016/5/4.
 */
@Repository
public class ZSuperComputerRepository {
    @Autowired
    JdbcTemplate jdbc;

    public  String sendSuComputer(String command) throws Exception{
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SshShell superComputer = new SshShell();
        List<String> result = new ArrayList<String>();
        String[] title = {"JobID","Name","User","Time","Use","S","Queue"};

        if(command.equals("qstat \n")){
            JSONArray array = new JSONArray();

            String shellResult = superComputer.sshShell("192.168.9.27", "test", "test123", -1, "", "",command); //"qstat \n"; "pbsnodes \n"
            String[] tempArray = shellResult.split("\n");
            result.addAll(Arrays.asList(tempArray));
            if (result.size()>5){
                for(String rs : result){
                    if(rs.substring(0, 1).matches("[0-9]")){
                        JSONObject superCom = new JSONObject();
                        rs=rs.replaceAll("\\s+", ",");
                        String[] temp = rs.substring(0,rs.length()-1).split(",");
                        for(int i=0;i<temp.length;i++){
                            if(title[i].equals("Name")){
                                UpperCase upperCase = new UpperCase();
                                String sql = "select name_zh from super_computer where instr(abbr,'"+ upperCase.getUpperCase(temp[i]) +"')>0;";
                                if(jdbc.queryForList(sql).size()>0){
                                    Map map = jdbc.queryForMap(sql);
                                    superCom.put(title[i], map.get("name_zh"));
                                }else{
                                    superCom.put(title[i], temp[i]);
                                }
                            }else {
                                superCom.put(title[i], temp[i]);
                            }
                        }
                        array.put(superCom);
                    }
                }
            }
            return array.toString().replace("\r","");
        }

        if(command.equals("pbsnodes \n")){
            String shellResult = superComputer.sshShell("192.168.9.27", "test", "test123", -1, "", "",command); //"qstat \n"; "pbsnodes \n"
            String[] tempArray = shellResult.split("\r\n\r\n");
            result.addAll(Arrays.asList(tempArray));
            for(int i= 0;i<result.size();i++){
                String str = result.get(i);
                String sub = str.substring(0,6);
                if(!sub.equals("comput")){
                    result.remove(i);
                    --i;
                }
            }

            JSONArray superCom = new JSONArray();
            for(String str:result){
                JSONObject su = new JSONObject();
                String computTemp = str.substring(str.indexOf("comput"),str.indexOf("state")).replace("\n","").replace(" ","");
                su.put("computNodes",computTemp);
                String state = str.substring(str.indexOf("state")+7,str.indexOf("np")).replace("\n","").replace(" ","");
                su.put("state", state);
                String loadave = str.substring(str.indexOf("loadave") + 8, str.indexOf(",ncpus"));
                su.put("loadave", loadave);
                String availmem = str.substring(str.indexOf("availmem=")+9, str.indexOf("kb,totmem"));
                String totmem = str.substring(str.indexOf("totmem=")+7, str.indexOf("kb,idletime"));
                double memTemp = 100*Double.valueOf(availmem)/Double.valueOf(totmem);
                String memTemp1 = decimalFormat.format(memTemp);
                su.put("freeMem",memTemp1);
                superCom.put(su);
            }
            return superCom.toString().replace("\\r","");
        }else{
            return null;
        }
    }
}
