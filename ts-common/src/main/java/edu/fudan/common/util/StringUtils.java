package edu.fudan.common.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class StringUtils {
    public static String String2Lower(String str){
        if(str == null || str.isEmpty()) {
            return str;
        }
        return str.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public static Date String2Date(String str){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date d = formatter.parse(str);
            return d;
        }catch(Exception e){
               return new Date();
        }
    }

    public static String Date2String(Date date){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
