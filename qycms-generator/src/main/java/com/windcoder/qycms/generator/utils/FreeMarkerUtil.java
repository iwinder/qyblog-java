package com.windcoder.qycms.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FreeMarkerUtil {
    // 将所有模板放在ftl包中
    static String filePath = "qycms-generator/src/main/java/com/windcoder/qycms/generator/ftl/";
    static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(filePath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        cfg.setDefaultEncoding("UTF-8");
        temp = cfg.getTemplate(ftlName,"UTF-8");
    }

    public static  void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
//        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        temp.process(map, bw);
        bw.flush();
        bw.close();
    }
}
