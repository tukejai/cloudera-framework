package com.cloudera.example;

/**
 * Constants
 */
public interface Constants {

  public static final String DIR_DS_MYDATASET = "/my-dataset";
  public static final String DIR_DS_MYDATASET_RAW = DIR_DS_MYDATASET + "/raw";
  public static final String DIR_DS_MYDATASET_RAW_SOURCE_TEXT = DIR_DS_MYDATASET_RAW + "/source/text";
  public static final String DIR_DS_MYDATASET_RAW_SOURCE_TEXT_TAB = DIR_DS_MYDATASET_RAW_SOURCE_TEXT
      + "/tab-delim/none";
  public static final String DIR_DS_MYDATASET_RAW_SOURCE_TEXT_TAB_SUFFIX = ".tsv";
  public static final String DIR_DS_MYDATASET_RAW_SOURCE_TEXT_COMMA = DIR_DS_MYDATASET_RAW_SOURCE_TEXT
      + "/comma-delim/none";
  public static final String DIR_DS_MYDATASET_RAW_SOURCE_TEXT_COMMA_SUFFIX = ".csv";
  public static final String DIR_DS_MYDATASET_PROCESSED = DIR_DS_MYDATASET + "/processed";
  public static final String DIR_DS_MYDATASET_CLEANSED = "cleansed";
  public static final String DIR_DS_MYDATASET_PROCESSED_CLEANSED = DIR_DS_MYDATASET_PROCESSED + "/"
      + DIR_DS_MYDATASET_CLEANSED;
  public static final String DIR_DS_MYDATASET_PROCESSED_CLEANSED_RELATIVE = DIR_DS_MYDATASET_CLEANSED
      + "/avro/binary/none";
  public static final String DIR_DS_MYDATASET_DUPLICATE = "duplicate";
  public static final String DIR_DS_MYDATASET_PROCESSED_DUPLICATE = DIR_DS_MYDATASET_PROCESSED + "/"
      + DIR_DS_MYDATASET_DUPLICATE;
  public static final String DIR_DS_MYDATASET_PROCESSED_DUPLICATE_RELATIVE = DIR_DS_MYDATASET_DUPLICATE
      + "/avro/binary/none";
  public static final String DIR_DS_MYDATASET_MALFORMED = "malformed";
  public static final String DIR_DS_MYDATASET_PROCESSED_MALFORMED = DIR_DS_MYDATASET_PROCESSED + "/"
      + DIR_DS_MYDATASET_MALFORMED;
  public static final String DIR_DS_MYDATASET_PROCESSED_MALFORMED_RELATIVE = DIR_DS_MYDATASET_MALFORMED
      + "/avro/binary/none";

  public enum Counter {
    RECORDS, RECORDS_CLEANSED, RECORDS_DUPLICATE, RECORDS_MALFORMED;
  }

}
