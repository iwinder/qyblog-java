package com.windcoder.qycms.generator.vue;

import com.windcoder.qycms.generator.utils.DbUtil;
import com.windcoder.qycms.generator.utils.Field;
import com.windcoder.qycms.generator.utils.FreeMarkerUtil;
import com.windcoder.qycms.generator.utils.GeneratorPathUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 *  用于后端代码的生成，包含controller、service、dto
 */
public class VueGenerator {
    static final String MODULE = GeneratorPathUtils.VUE_MODULE;

    static String toVuePath =  GeneratorPathUtils.TO_VUE_CONSOLE_PROJECT_W + MODULE + "/";
    static String generatorConfigPath = "qycms-generator/src/main/resources/generator/generatorConfig.xml";

    public static void main(String[] args) throws Exception  {
        // 只生成配置文件中的第一个 table 节点
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        // 读 取xml 文件到 Document 中
        Document doc = reader.read(file);
        // 读取 xml 文件的根节点
        Element rootElement = doc.getRootElement();
        // 读取 context 节点
        Element contextElement = rootElement.element("context");
        // 定义一个Element用于遍历
        Element tableElement;
        // 取第一个 table 的节点
        tableElement = contextElement.elementIterator("table").next();


        String Domain = tableElement.attributeValue("domainObjectName");
        // 表名可能有下划线，所以不是小写的domain
        String tableName =  tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0,1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // 生成vue
        File parentDir = new File(toVuePath);
        if (!parentDir.exists()){
            parentDir.mkdirs();
        }
        FreeMarkerUtil.initConfig("vue.ftl");
//        FreeMarkerUtil.generator(toVuePath + domain + ".vue", map);
        FreeMarkerUtil.generator(toVuePath  + "index.vue", map);


    }


    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
