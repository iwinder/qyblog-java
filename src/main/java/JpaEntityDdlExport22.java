import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 可以使用JPA Entity类生成DDL查询的类
 *
 * 生成成功，但DIALECT_CLASS获取不友好。
 * https://gist.github.com/sbcoba/e4264f4b4217746767e682c61f9dc3a6
 */
public class JpaEntityDdlExport22 {
    /**
     * 要创建的文件名
     */
    private static final String SCHEMA_SQL = "schema2_%s.sql";
    private static final String SCHEMA_UPDATE_SQL = "schema-update2_%s.sql";
    /**
     * 域类路径位置（如果范围很宽，则只能找到带有@Entity的类）
     */
    private final static String PATTERN = "classpath*:**/**/entity/*.class";
    private final static String PATH = "com.windcoder";


    /**
     * DB类型生成DDL
     * org.hibernate.dialect.* 包参考*
     *
     * - Oracle  Oracle10gDialect.class
     * - H2 H2Dialect.class
     * ...
     *
     */
    private final static Class<? extends Dialect> DIALECT_CLASS = MySQL5InnoDBDialect.class;

    public static void main(String[] args) {
        createData(args);

//        File directory = new File("");// 参数为空
//        String courseFile = null;
//        try {
//            courseFile = directory.getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(courseFile);
//        String[] argss = args;
//        if (args==null|| args.length==0){
//            args.SCHEMA_UPDATE_SQL;
//        }
//        updatData(args);
    }


    /**
     * 生成全量SQL脚本
     * @param args
     */
    public static void createData(String[] args){
        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.dialect", DIALECT_CLASS);
//        settings.put("hibernate.format_sql", true);
        settings.put("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        settings.put("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        settings.put("hibernate.id.new_generator_mappings", false);

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        MetadataSources metadata = new MetadataSources(standardServiceRegistry);
        String pattern = getPattern(args);
        List<Class<?>> classes = getClassesByAnnotation(Entity.class, pattern);
        classes.forEach(metadata::addAnnotatedClass);
        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
        SchemaExport schemaExport = new SchemaExport(metadataImplementor);
        String outputFile = getOutputFilename(args);
        schemaExport.setOutputFile(outputFile);
        schemaExport.setDelimiter(";");
        schemaExport.create(true, false);
    }

    public static void updatData(String[] args){
        File propsFile = new File("./qycms-core/common/src/main/resources/application.properties");
//        File propsFile = new File("classpath:application.properties");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propsFile));
            Map<String, Object> settings = new HashMap<>();
            settings.put("hibernate.dialect", DIALECT_CLASS);
            settings.put("hibernate.connection.driver", properties.getProperty("spring.datasource.driver-class-name"));
            settings.put("hibernate.connection.url", properties.getProperty("spring.datasource.url"));
            settings.put("hibernate.connection.username", properties.getProperty("spring.datasource.username"));
            settings.put("hibernate.connection.password", properties.getProperty("spring.datasource.password"));
            settings.put("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
            settings.put("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
            settings.put("hibernate.id.new_generator_mappings", false);

            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            MetadataSources metadata = new MetadataSources(standardServiceRegistry);
            String pattern = getPattern(args);
            List<Class<?>> classes = getClassesByAnnotation(Entity.class, pattern);
            classes.forEach(metadata::addAnnotatedClass);
            MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
            SchemaUpdate schemaUpdate = new SchemaUpdate(metadataImplementor);
            String[] argss ={SCHEMA_UPDATE_SQL};
            schemaUpdate.setOutputFile(getOutputFilename(argss));

            schemaUpdate.setDelimiter(";");
            schemaUpdate.setFormat(false);
            schemaUpdate.execute(true, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPattern(String[] args) {
        String pattern = PATTERN;
        if(args != null && args.length >= 3
                && StringUtils.hasText(args[2])) {
            pattern = args[2];
        }
        return pattern;
    }

    private static void appendMetaData(String outputFile, Map<String, Object> settings) {
        String charsetName = "UTF-8";
        File ddlFile = new File(outputFile);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("/* Generate Environment\n");
            for (Map.Entry<String, Object> entry : settings.entrySet()) {
                sb.append(entry.getKey().toString() + ": " + entry.getValue() + "\n");
            }
            sb.append("*/\n");
            String ddlFileContents = StreamUtils.copyToString(new FileInputStream(ddlFile), Charset.forName(charsetName));
            sb.append(ddlFileContents);
            FileCopyUtils.copy(sb.toString().getBytes(charsetName), ddlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在查询末尾添加分号（;）
     * @param outputFile
     */
    private static void appendSemicolon(String outputFile) {
        String charsetName = "UTF-8";
        File ddlFile = new File(outputFile);
        try {
            String ddlFileContents = StreamUtils.copyToString(new FileInputStream(ddlFile), Charset.forName(charsetName));
            ddlFileContents = ddlFileContents.replaceAll("\n\n", ";\n\n");
            ddlFileContents = ddlFileContents.replaceAll("\n.*(?![\f\n\r])$", ";\n");
            FileCopyUtils.copy(ddlFileContents.getBytes(charsetName), ddlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation, String pattern) {
        return getResources(pattern).stream()
                .map(r -> metadataReader(r))
                .filter(Objects::nonNull)
                .filter(mr -> mr.getAnnotationMetadata().hasAnnotation(annotation.getName()))
                .map(mr -> entityClass(mr))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取与模式对应的资源信息。
     * @param pattern
     * @return
     */
    private static List<Resource> getResources(String pattern) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(pattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(resources);
    }


    private static Class<?> entityClass(MetadataReader mr) {
        String className = mr.getClassMetadata().getClassName();
//        if (className.indexOf(PATH)<0){
//            return null;
//        }
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.err.printf("%s Class not found", className);
            return null;
        }
        return clazz;
    }

    private static MetadataReader metadataReader(Resource r) {
        MetadataReader mr;
        try {
            mr = new SimpleMetadataReaderFactory().getMetadataReader(r);
        } catch (IOException e) {
            System.err.printf(e.getMessage());
            return null;
        }
        return mr;
    }

    private static String getOutputFilename(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        if(args != null && args.length > 0
                && StringUtils.hasText(args[0])) {
            String customSchemaName = args[0];
            if(customSchemaName.contains("%s")) {
                return String.format(customSchemaName, currentDate);
            }
            return customSchemaName;
        }
        return String.format(SCHEMA_SQL, currentDate);
    }
}
