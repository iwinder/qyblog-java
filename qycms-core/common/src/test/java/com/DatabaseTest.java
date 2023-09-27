package com;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.extract.internal.DatabaseInformationImpl;
import org.hibernate.tool.schema.extract.spi.DatabaseInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.text.MessageFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = { "classpath:application.properties" })
//@SpringApplicationConfiguration(classes = { ConfigTest.class })
@SpringBootTest(classes = { DatabaseTest.class })
public class DatabaseTest {
    private static final String version = "2";

    private static final String previousVersion = "1";
    @Autowired
    private Metadata metadata;

//    public DatabaseTest(Metadata metadata) {
//        this.metadata = metadata;
//    }

    @Autowired
    private DataSource dataSource;

    @Test
    public void currentSchemaVersionDoesMatchMappings() throws SQLException {

        File dropAndCreateDdlFile = new File("db/base/create-ddl.sql");
        deleteFileIfExists(dropAndCreateDdlFile);


        SchemaExport schemaExport = new SchemaExport((MetadataImplementor) metadata);
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.setOutputFile(dropAndCreateDdlFile.getAbsolutePath());

        schemaExport.execute(true, false, false, false);
//        SchemaExport schemaExport = new SchemaExport((MetadataImplementor) metadata);
//        schemaExport.setDelimiter(";");
//        schemaExport.setFormat(false);
//        schemaExport.setOutputFile(dropAndCreateDdlFile.getAbsolutePath());
//
//        schemaExport.execute(true, false, false, false);
//        export.setDelimiter(";");
//        export.setOutputFile(directory + "ddl_" + dialect.name().toLowerCase() + ".sql");
//        export.setFormat(true);
//        export.execute(true, false, false, false);
    }
    private void deleteFileIfExists(File dropAndCreateDdlFile) {
        if (dropAndCreateDdlFile.exists()) {
            if (!dropAndCreateDdlFile.isFile()) {
                String msg = MessageFormat.format("File is not a normal file {0}", dropAndCreateDdlFile);
                throw new IllegalStateException(msg);
            }

            if (!dropAndCreateDdlFile.delete()) {
                String msg = MessageFormat.format("Unable to delete file {0}", dropAndCreateDdlFile);
                throw new IllegalStateException(msg);
            }
        }
    }
//    @Test
    public void schemaMigrationIsValid() throws SQLException {
//        if (previousVersion == null)
//            return;
//
//        String previousSchema = String.format("sql/schema_v%s.sql", previousVersion);
//        String migrationScript = String.format("sql/schema_v%s_to_v%s.sql", previousVersion, version);
//        Resource previousSchemaResource = new ClassPathResource(previousSchema);
//        Resource migrationScriptResource = new ClassPathResource(migrationScript);
//        ScriptUtils.executeSqlScript(dataSource.getConnection(), previousSchemaResource);
//        ScriptUtils.executeSqlScript(dataSource.getConnection(), migrationScriptResource);
//
//        LocalSessionFactoryBuilder sessionFactory = new LocalSessionFactoryBuilder(dataSource);
//        sessionFactory.scanPackages(this.getClass().getPackage().getName());
//        Dialect dialect = new MySQL5Dialect();
//        DatabaseMetadata metadata = new DatabaseMetadata(dataSource.getConnection(), dialect, sessionFactory);
//        sessionFactory.validateSchema(dialect, metadata);
    }
}
