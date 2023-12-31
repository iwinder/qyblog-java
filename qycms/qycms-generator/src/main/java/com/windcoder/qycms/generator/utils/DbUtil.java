package com.windcoder.qycms.generator.utils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbUtil {

    /**
     *  连接数据库
     * @return
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:MySQL://localhost:3306/qycmsdev?characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai";
            String user = "winder";
            String pass = "123456";
            conn = DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     *  获得表注释
     * @param tableName
     * @return
     */
    public static String getTableComment(String tableName) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select table_comment from information_schema.TABLES where table_name = '"+ tableName +"'");
        String tableNameCH = "";
        if (null != rs) {
            while (rs.next()) {
                tableNameCH = rs.getString("table_comment");
                break;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("表名：" + tableNameCH );
        return tableNameCH;
    }

    public static List<Field> getColumnByTableName(String tableName) throws SQLException {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from `" + tableName + "`");
        if (null != rs) {
            while (rs.next()) {
                String columnName = rs.getString("Field");
                String type = rs.getString("Type");
                String comment = rs.getString("Comment");
                String nullAble = rs.getString("Null"); //YES NO
                Field field = new Field();
                field.setName(columnName);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setType(type);
                field.setJavaType(sqlTypeToJavaType(rs.getString("Type")));
                field.setComment(comment);
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                field.setNullAble("YES".equals(nullAble));
                // char类型一般用于固定长度的字段，常见的有ID字段和枚举字段，id字段不需要校验，枚举一般有下拉选择，不是手动输入也不需要校验。
                // 这里仅校验了varchar类型的长度
                if (type.toUpperCase().contains("varchar".toUpperCase())) {
                    String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                } else {
                    field.setLength(0);
                }
                // 校验是否是枚举
//                if(comment.contains("枚举")) {
//                    field.setEnums(true);
//                    // 以课程等级为例：从注释中的“枚举[CourseLevelEnum]”，得到COURSE_LEVEL
//                    int start = comment.indexOf("[");
//                    int end = comment.indexOf("]");
//                    String enumsName = comment.substring(start+1, end);
//                    String enumsConst = EnumGenerator.toUnderline(enumsName);
//                    field.setEnumsConst(enumsConst);
//                } else {
//                    field.setEnums(false);
//                }
                field.setEnums(false);
                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("列信息：" + fieldList);
        return fieldList;
    }


    /**
     * 下划线转小驼峰
     */
    public static String lineToHump(String str){
        Pattern linePattern = Pattern.compile("_(\\w)");
        // 全部小写
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            // 执行渐进式的替换，把替换完的内容添加到sb中
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        // 当遍历执行所有的替换操作完成后,将最后剩余的部分添加到sb（把最后一次匹配到内容之后的字符串追加到StringBuffer中）
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 下划线转大驼峰
     */
    public static String lineToBigHump(String str){
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 数据库类型转为Java类型
     */
    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";
        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";
        } else if (sqlType.toUpperCase().contains("long".toUpperCase()) || sqlType.toUpperCase().contains("bigint".toUpperCase())) {
            return "Long";
        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";
        }  else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }

}
