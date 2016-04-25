package cn.edu.shou.monitor.web.api;

/**
 * Created by seky on 16/4/22.
 */

import cn.edu.shou.monitor.service.ApplicationManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出
 *
 * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
 * @param dataList 数据
 * @return
 */
@RestController
@RequestMapping(value ="/predictCenter/api/")
public class CSVUtils {
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;
    @RequestMapping(value = "/exportCsv")
    public boolean exCSV(){
        //获取数据
        List<Object> dataList=new ArrayList<Object>();

        boolean isSuccess=exportCsv(new File("C:/Users/sqhe18/Desktop/qianqian.csv"), dataList);
        System.out.println(isSuccess);
        return true;
    }


    //导出
    public  boolean exportCsv(File file, List<Object> dataList){
        boolean isSucess=false;

        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        String bs = new String(b);
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"UTF8");
            //bw =new BufferedWriter(osw);
            bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            bw.write("\uFEFF");
            bw.write("编号,姓名,性别\r\n");
            if(dataList!=null && !dataList.isEmpty()){
                for(Object data : dataList){
                    bw.append(data.toString()).append("\r\n");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

    /**
     * 导入
     *
     * @param file csv文件(路径+文件)
     * @return
     */
    @RequestMapping(value = "/api/importCsv")
    public  List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();

        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }
}
