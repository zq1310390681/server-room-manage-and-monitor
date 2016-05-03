package cn.edu.shou.monitor.transmission;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by light on 2016/3/29.
 */
@Repository
public class SuComputerScreen {
    /**
     * 利用JSch包实现远程主机SHELL命令执行
     * @param ip 主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     * @param privateKey 密钥文件路径
     * @param passphrase 密钥的密码
     */

    @Autowired
    JdbcTemplate jdbc;

    public  String sshShell(String ip, String user, String psw
            ,int port ,String privateKey ,String passphrase,String command) throws Exception{
        String shellString = null;
        Session session = null;
        Channel channel = null;

        JSch jsch = new JSch();

        //设置密钥和密码
        if (privateKey != null && !"".equals(privateKey)) {
            if (passphrase != null && "".equals(passphrase)) {
                //设置带口令的密钥
                jsch.addIdentity(privateKey, passphrase);
            } else {
                //设置不带口令的密钥
                jsch.addIdentity(privateKey);
            }
        }

        if(port <=0){
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        }else{
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip ,port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);

        String shellOutput = "";
        try {
            //创建sftp通信通道
            channel = session.openChannel("shell");
            channel.connect(1000);

            //获取输入流和输出流
            InputStream instream = channel.getInputStream();
            OutputStream outstream = channel.getOutputStream();

            //发送需要执行的SHELL命令，需要用\n结尾，表示回车
            outstream.write(command.getBytes());  //"qstat \n";  // "pbsnodes \n"
            outstream.flush();


            Thread.sleep(1000);
            //获取命令执行的结果
            List<String> result = new ArrayList<String>();

            while (instream.available() > 0){
                byte[] data = new byte[instream.available()];
                int nLen = instream.read(data);

                if (nLen < 0){
                    throw new Exception("network error.");
                }

                //转换输出结果并打印出来
                shellOutput = new String(data, 0, nLen,"iso8859-1");
//                System.out.println("OUTPUT IS "+shellOutput);
            }

            outstream.close();
            instream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
        return shellOutput;
    }


    public  String sendSuComputer(String command) throws Exception{
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SuComputerScreen superComputer = new SuComputerScreen();
        List<String> result = new ArrayList<String>();
        String[] title = {"JobID","Name","User","Time","Use","S","Queue"};

        if(command.equals("qstat \n")){
            JSONArray array = new JSONArray();
            JSONObject superCom = new JSONObject();
            String shellResult = superComputer.sshShell("192.168.9.27", "test", "test123", -1, "", "",command); //"qstat \n"; "pbsnodes \n"
            String[] tempArray = shellResult.split("\n");
            result.addAll(Arrays.asList(tempArray));
            if (result.size()>5){
                for(String rs : result){
                    if(rs.substring(0, 1).matches("[0-9]")){
                        rs=rs.replaceAll("\\s+", ",");
                        String[] temp = rs.substring(0,rs.length()-1).split(",");
                        for(int i=0;i<temp.length;i++){
                            if(title[i].equals("Name")){
                                String temps = "ECSWFSs";
                                String sql = "SELECT name_zh FROM super_computer WHERE abbr like '%"+ temps + "%';";
                                Map map = jdbc.queryForMap(sql);
                                if(map.size()>0){
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
            return array.toString();
        }

        if(command.equals("pbsnodes \n")){
            String shellResult = superComputer.sshShell("192.168.9.27", "test", "test123", -1, "", "",command); //"qstat \n"; "pbsnodes \n"
            String[] tempArray = shellResult.split("\r\n\r\n");
            result.addAll(Arrays.asList(tempArray));
            for(int i= 0;i<result.size();i++){
                String str = result.get(i);
                String sub = str.substring(0,5);
                if(!sub.equals("comput")){
                    result.remove(i);
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
            System.out.printf(superCom.toString());
            return superCom.toString();
        }else{
            return null;
        }
    }
}
