/**
 * Project Name:solr-dataimportschedule
 * File Name:DeltaImportHTTPPostScheduler
 * Package Name:org.apache.solr.handler.dataimport.scheduler
 * Date:2017/12/11 16:27
 * Copyright (c) 2017, ljk@bostech.com.cn All Rights Reserved.
 */
package org.apache.solr.handler.dataimport.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

/**
 * TODO
 * @author 刘姜科
 * @since JDK1.7
 * @history 2017/12/11
 */
public class DeltaImportHTTPPostScheduler extends BaseTimerTask{
    private static final Logger logger = LoggerFactory
            .getLogger(DeltaImportHTTPPostScheduler.class);

    public DeltaImportHTTPPostScheduler(String webAppName, Timer t)
            throws Exception {
        super(webAppName, t);
        logger.info("<index update process> DeltaImportHTTPPostScheduler init");
    }

    public void run() {
        try {
            // check mandatory params
            if (server.isEmpty() || webapp.isEmpty() || params == null
                    || params.isEmpty()) {
                logger.warn("<index update process> Insuficient info provided for data import");
                logger.info("<index update process> Reloading global dataimport.properties");
                reloadParams();
                // single-core
            } else if (singleCore) {
                prepUrlSendHttpPost(params);

                // multi-core
            } else if (syncCores.length == 0
                    || (syncCores.length == 1 && syncCores[0].isEmpty())) {
                logger.warn("<index update process> No cores scheduled for data import");
                logger.info("<index update process> Reloading global dataimport.properties");
                reloadParams();

            } else {
                for (String core : syncCores) {
                    prepUrlSendHttpPost(core, params);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to prepare for sendHttpPost", e);
            reloadParams();
        }
    }
}
