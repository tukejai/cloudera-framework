package com.cloudera.framework.main.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.MRConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LocalClusterDFSMRTest extends BaseTest {

  private static Logger LOG = LoggerFactory
      .getLogger(LocalClusterDFSMRTest.class);

  private static JobConf conf;
  private static FileSystem fileSystem;

  @Override
  public Configuration getConf()  {
    return conf;
  }

  @Override
  public FileSystem getFileSystem() throws IOException {
    return fileSystem;
  }

  @Override
  public String getPathLocal(String pathRelativeToModuleRoot) {
    String pathRelativeToModuleRootSansLeadingSlashes = stripLeadingSlashes(pathRelativeToModuleRoot);
    return pathRelativeToModuleRootSansLeadingSlashes.equals("") ? PATH_LOCAL_WORKING_DIR
        .length() < 2 ? "/" : PATH_LOCAL_WORKING_DIR.substring(0,
        PATH_LOCAL_WORKING_DIR.length() - 2) : new Path(PATH_LOCAL_WORKING_DIR,
        pathRelativeToModuleRootSansLeadingSlashes).toUri().toString();
  }

  @Override
  public String getPathHDFS(String pathRelativeToHDFSRoot) {
    String pathRelativeToHDFSRootSansLeadingSlashes = stripLeadingSlashes(pathRelativeToHDFSRoot);
    return pathRelativeToHDFSRootSansLeadingSlashes.equals("") ? PATH_HDFS_LOCAL
        : new Path(PATH_HDFS_LOCAL, pathRelativeToHDFSRootSansLeadingSlashes)
            .toUri().toString();
  }

  @BeforeClass
  public static void setUpRuntime() throws Exception {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Test harness [setUpRuntime] starting");
    }
    fileSystem = FileSystem.getLocal(conf = new JobConf());
    conf.set(MRConfig.FRAMEWORK_NAME, MRConfig.LOCAL_FRAMEWORK_NAME);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Test harness [setUpRuntime] finished");
    }
  }

  @AfterClass
  public static void tearDownRuntime() throws Exception {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Test harness [tearDownRuntime] starting");
    }
    if (fileSystem != null) {
      fileSystem.close();
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Test harness [tearDownRuntime] finished");
    }
  }

}
