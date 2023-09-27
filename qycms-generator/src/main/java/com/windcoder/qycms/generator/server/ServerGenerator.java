package com.windcoder.qycms.generator.server;


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
public class ServerGenerator {
    static final String MODULE = GeneratorPathUtils.MODULE;
    static final String projectUrl = GeneratorPathUtils.PROJECT_URL;
    static final String projectControllerUrl = GeneratorPathUtils.PROJECT_CONSOLE_URL;
//    static String MODULE = "file";
//    static String MODULE = "system";
//    static String MODULE = "resource";
    static String toServicePath = projectUrl + "/src/main/java/com/windcoder/qycms/" + MODULE + "/service/";
    static String toDtoPath = projectUrl + "/src/main/java/com/windcoder/qycms/" + MODULE + "/dto/";
    static String toControllerPath = projectControllerUrl + "/src/main/java/com/windcoder/qycms/" + MODULE + "/admin/controller/";
//    static String generatorConfigPath = "qycms-generator\\src\\main\\resources\\generator\\generatorConfig.xml";
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

        // 生成dto
        FreeMarkerUtil.initConfig("dto.ftl");
        FreeMarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // 生成Service
        FreeMarkerUtil.initConfig("service.ftl");
        FreeMarkerUtil.generator(toServicePath+ Domain +"Service.java", map);
//
        // 生成Controller
        FreeMarkerUtil.initConfig("controller.ftl");
        FreeMarkerUtil.generator(toControllerPath+ Domain +"Controller.java", map);
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
