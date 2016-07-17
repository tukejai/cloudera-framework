package com.cloudera;

import org.junit.ClassRule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.cloudera.framework.testing.server.DfsServer;
import com.cloudera.framework.testing.server.HiveServer;
import com.cloudera.framework.testing.server.MrServer;
import com.cloudera.framework.testing.server.PythonServer;
import com.cloudera.test.Hive;
import com.cloudera.test.Python;

@RunWith(Suite.class)
@SuiteClasses({ //
    Hive.class, //
    Python.class, //
})

public class TestSuite {

  @ClassRule
  public static TestRule cdhServers = RuleChain //
      .outerRule(DfsServer.getInstance(DfsServer.Runtime.CLUSTER_DFS)) //
      .around(MrServer.getInstance(MrServer.Runtime.CLUSTER_JOB)) //
      .around(HiveServer.getInstance(HiveServer.Runtime.CLUSTER_MR2)) //
      .around(PythonServer.getInstance(PythonServer.Runtime.LOCAL_CPYTHON));

}
