/**
 * Project Name:solr-dataimportschedule
 * File Name:org.apache.solr.handler.dataimport.scheduler.SolrDataImportProperties
 * Package Name:PACKAGE_NAME
 * Date:2017/12/11 16:21
 * Copyright (c) 2017, ljk@bostech.com.cn All Rights Reserved.
 */
package org.apache.solr.handler.dataimport.scheduler;


import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * TODO
 * @author 刘姜科
 * @since JDK1.7
 * @history 2017/12/11
 */
public class SolrDataImportProperties {
    private Properties properties;
    public static final String SYNC_ENABLED = "syncEnabled";
    public static final String SYNC_CORES = "syncCores";
    public static final String SERVER = "server";
    public static final String PORT = "port";
    public static final String WEBAPP = "webapp";
    public static final String PARAMS = "params";
    public static final String INTERVAL = "interval";
    public static final String REBUILDINDEXPARAMS = "reBuildIndexParams";
    public static final String REBUILDINDEXBEGINTIME = "reBuildIndexBeginTime";
    public static final String REBUILDINDEXINTERVAL = "reBuildIndexInterval";
    private static final Logger logger = LoggerFactory.getLogger(SolrDataImportProperties.class);

    public void loadProperties(boolean force) {
        try {
            SolrResourceLoader loader = new SolrResourceLoader();
            logger.info("Instance dir = " + loader.getInstancePath());
            String configDir = loader.getConfigDir();
            configDir = SolrResourceLoader.normalizeDir(configDir);
            if ((force) || (this.properties == null)) {
                this.properties = new Properties();
                String dataImportPropertiesPath = configDir + "dataimport.properties";
                FileInputStream fis = new FileInputStream(dataImportPropertiesPath);
                this.properties.load(fis);
            }
        } catch (FileNotFoundException fnfe) {
            logger.error(
                    "Error locating DataImportScheduler dataimport.properties file",
                    fnfe);
        } catch (IOException ioe) {
            logger.error(
                    "Error reading DataImportScheduler dataimport.properties file",
                    ioe);
        } catch (Exception e) {
            logger.error("Error loading DataImportScheduler properties", e);
        }
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}
