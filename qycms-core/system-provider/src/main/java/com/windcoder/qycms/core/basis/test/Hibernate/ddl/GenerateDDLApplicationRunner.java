package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//@Component
public class GenerateDDLApplicationRunner  implements ApplicationRunner {
    private Metadata metadata;
    private static final String SCHEMA_SQL2 = "db/base/create-ddl_%s.sql";
    public GenerateDDLApplicationRunner(Metadata metadata) {
        this.metadata = metadata;
    }

    public void run(ApplicationArguments args) throws Exception {
        File dropAndCreateDdlFile = new File(getOutputFilename());
        deleteFileIfExists(dropAndCreateDdlFile);


        SchemaExport schemaExport = new SchemaExport((MetadataImplementor) metadata);
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.setOutputFile(dropAndCreateDdlFile.getAbsolutePath());

        schemaExport.execute(true, false, false, false);
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


    private static String getOutputFilename() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        return String.format(SCHEMA_SQL2, currentDate);
    }
}
