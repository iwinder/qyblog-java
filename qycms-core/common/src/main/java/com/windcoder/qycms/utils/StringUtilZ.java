package com.windcoder.qycms.utils;

import com.windcoder.qycms.exception.BusinessException;
import org.apache.commons.codec.digest.Md5Crypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilZ {

//    private final String[] BOTS = ["google"];

    private static String removeHtml(String html) {
        String content = "";
        content = html.replaceAll( ".*?<body.*?>(.*?)<\\/body>", "$1");
        content=content.replaceAll("</?[a-zA-Z]+[^><]*>","");
        content.replaceAll("\n","");
        return content;
    }

    public static String removeHtmlAndSubstring(String html) {
        String content = removeHtml(html);
        return content.substring(0,Math.min(100, content.length()));

    }

    public static String substringHtml(String html) {
        String content = HtmlToText.getContent(html);
        return content.substring(0,Math.min(100, content.length()));

    }

    public static Integer randomRange(Integer minNum,Integer maxNum) {
        int tmp = 0;
        if (minNum == null) {
            if ( maxNum == null) {
                return tmp;
            } else {
                return (int) (Math.random()*maxNum+1);
            }

        } else   {
           if (maxNum == null) {
               return  minNum + (int) (Math.random()*10+1);
           } else {
               return  minNum + (int) (Math.random()*(maxNum-minNum+1));
           }
        }

    }
    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            return MD5Util.stringToMD5(str,32);
        } catch (Exception e) {
            throw new BusinessException("数据加密出现错误");
        }
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            System.out.println(randomRange(0, null));
        }

    }

    public static String getFirstMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static String getReplaceAll(String regex, String source,String  replace ){
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.replaceAll(replace);
    }

}
