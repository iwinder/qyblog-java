package com.windcoder.qycms.utils;

public class StringUtilZ {

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

    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            System.out.println(randomRange(0, null));
        }

    }

}
