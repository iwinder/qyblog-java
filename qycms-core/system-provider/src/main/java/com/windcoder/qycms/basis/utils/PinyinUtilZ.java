package com.windcoder.qycms.basis.utils;

import com.windcoder.qycms.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class PinyinUtilZ {
   private static final HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

   private static String toHanYuPinyinStringByChinese(String str) throws BadHanyuPinyinOutputFormatCombination {
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        return PinyinHelper.toHanYuPinyinString(str,defaultFormat,"", true).toLowerCase();
    }

    public static String toHanYuPinyinString(String str) {
        try {
            String noFormat =  toHanYuPinyinStringByChinese(str);
            return noFormat.replaceAll("[^a-z0-9]", "-").replaceAll("(\\-+)", "-");
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            log.error(badHanyuPinyinOutputFormatCombination.getLocalizedMessage());
            new BusinessException("转换拼音失败");
        }
        return null;
    }


    public static String regexReplace(String str){
        Pattern p = null;
        Matcher m = null;
        String value = null;

        // 去掉<>标签及其之间的内容
        p = Pattern.compile("[^\\x00-\\xff]");
        m = p.matcher(str);
        String temp = str;
        //下面的while循环式进行循环匹配替换，把找到的所有
        //符合匹配规则的字串都替换为你想替换的内容
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value, "-");
        }
        return temp;
    }

    public static void main(String[] args) {
        String str = "Java学习---Pinyin4j使用————手册掠 course dd：东东s:dd";
//        String[] pinyin = PinyinHelper.toHanyuPinyinStringArray( );
        try {
            String ss = toHanYuPinyinStringByChinese(str);
            System.out.println("dd:" + ss);
            String s2 = ss.replaceAll("[^a-z0-9]", "-").replaceAll("(\\-+)", "-");
//            String s3 = s2
            System.out.println("dd s2:" + s2);
//            System.out.println("dd s3:" + s3);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
    }


}
