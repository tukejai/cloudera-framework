package com.cloudera.framework.main.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.ql.CommandNeedRetryException;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.exec.CopyTask;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapreduce.MRConfig;
import org.apache.hive.jdbc.miniHS2.MiniHS2;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniClusterDfsMrHiveBaseTest extends BaseTest {

  private static final String COMMAND_DELIMETER = ";";

  private static Logger LOG = LoggerFactory
      .getLogger(MiniClusterDfsMrHiveBaseTest.class);

  private static HiveConf conf;
  private static MiniHS2 miniHs2;
  private static FileSystem fileSystem;

  @Override
  public Configuration getConf() {
    return conf;
  }

  @Override
  public FileSystem getFileSystem() {
    return fileSystem;
  }

  @BeforeClass
  public static void setUpRuntime() throws Exception {
    long time = debugMessageHeader(LOG, "setUpRuntime");
    HiveConf hiveConf = new HiveConf(new Configuration(), CopyTask.class);
    miniHs2 = new MiniHS2(hiveConf, true);
    Map<String, String> hiveConfOverlay = new HashMap<String, String>();
    hiveConfOverlay.put(ConfVars.HIVE_SUPPORT_CONCURRENCY.varname, "false");
    hiveConfOverlay.put(MRConfig.FRAMEWORK_NAME, MRConfig.LOCAL_FRAMEWORK_NAME);
    miniHs2.start(hiveConfOverlay);
    fileSystem = miniHs2.getDfs().getFileSystem();
    SessionState.start(new SessionState(hiveConf));
    conf = miniHs2.getHiveConf();
    for (String table : processStatement("SHOW TABLES")) {
      processStatement("DROP TABLE " + table);
    }
    debugMessageFooter(LOG, "setUpRuntime", time);
  }

  @Before
  public void setUpSchema() throws Exception {
    long time = debugMessageHeader(LOG, "setUpSchema");
    for (String table : processStatement("SHOW TABLES")) {
      processStatement("DROP TABLE " + table);
    }
    debugMessageFooter(LOG, "setUpSchema", time);
  }

  @AfterClass
  public static void tearDownRuntime() throws Exception {
    long time = debugMessageHeader(LOG, "tearDownRuntime");
    if (miniHs2 != null) {
      miniHs2.stop();
    }
    debugMessageFooter(LOG, "tearDownRuntime", time);
  }

  public static List<String> processStatement(String statement)
      throws SQLException, CommandNeedRetryException, IOException {
    long time = debugMessageHeader(LOG, "processStatement");
    List<String> results = new ArrayList<String>();
    CommandProcessor commandProcessor = CommandProcessorFactory
        .getForHiveCommand(statement.trim().split("\\s+"), conf);
    (commandProcessor = commandProcessor == null ? new Driver(conf)
        : commandProcessor).run(statement);
    if (commandProcessor instanceof Driver) {
      ((Driver) commandProcessor).getResults(results);
    }
    debugMessageFooter(LOG, "processStatement", time);
    return results;
  }

  public static List<String> processStatement(String directory, String file)
      throws SQLException, CommandNeedRetryException, IOException {
    List<String> results = new ArrayList<String>();
    for (String statement : readFileToLines(directory, file, COMMAND_DELIMETER)) {
      results.addAll(processStatement(statement));
    }
    return results;
  }

  private static List<String> readFileToLines(String directory, String file,
      String delimeter) throws IOException {
    List<String> lines = new ArrayList<String>();
    InputStream inputStream = MiniClusterDfsMrHiveBaseTest.class
        .getResourceAsStream(directory + "/" + file);
    if (inputStream != null) {
      try {
        for (String line : IOUtils.toString(inputStream).split(delimeter)) {
          if (!line.trim().equals("")) {
            lines.add(line.trim());
          }
        }
        return lines;
      } finally {
        inputStream.close();
      }
    }
    throw new IOException("Could not load file [" + directory + "/" + file
        + "] from classpath");
  }

}
