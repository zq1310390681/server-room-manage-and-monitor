package cn.edu.shou.monitor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by light on 2016/5/5.
 * @parm args
 * 输入字符串 得到其大写字母，若无，返回越来的值
 */
public class UpperCase {
    /**
     * @param args
     * @return String
     * @throws IOException
     */
    public String getUpperCase(String args) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(args.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int t = 0;
        while((t = bin.read()) != -1){
            char c = (char)t;
            if((c >='A')&&(c <= 'Z')){
                out.write(t);
            }
        }
        if(out.toString().length()>0){
            return out.toString();
        }else{
            return args;
        }
    }
}
