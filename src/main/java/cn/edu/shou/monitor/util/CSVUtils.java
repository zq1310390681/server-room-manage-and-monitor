package cn.edu.shou.monitor.util;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by light on 2016/4/25.
 */
/**
 * CSV操作(导出和导入)
 *
 * @author 林计钦
 * @version 1.0 Jan 27, 2014 4:30:58 PM
 */
public class CSVUtils {

    //导出
    public boolean exportCsv(File file, String fieldNames,List<Object> dataList){
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
            bw.write(fieldNames+"\r\n");
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
