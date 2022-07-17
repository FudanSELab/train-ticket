package edu.fudan.common.util;

import java.util.Locale;

public class StringUtils {
    public static String String2Lower(String str){
        if(str.isEmpty()) {
            return str;
        }
        return str.replace(" ", "").toLowerCase(Locale.ROOT);
    }
}
