/*
 * Copyright 2015 Asitha Nanayakkara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jms.config;

public abstract class PubSubConfig extends GlobalConfig{

    private long messageCount;
    private int parallelPublishers;
    private String queueName;
    private String id;

    public PubSubConfig(GlobalConfig globalConfig) {
        super(globalConfig.getUsername(), globalConfig.getPassword(),
                globalConfig.getHostname(), globalConfig.getPort(),
                globalConfig.getInitialContextFactory(), globalConfig.getConnectionFactoryPrefix(),
                globalConfig.getConnectionFactoryName(), globalConfig.getClientID(),
                globalConfig.getVirtualHostName(), globalConfig.getPrintPerMessages(),
                globalConfig.isEnableConsoleReport(), globalConfig.getConsoleReportUpdateInterval(),
                globalConfig.isJmxReportEnable(), globalConfig.isCsvReportEnable(),
                globalConfig.getCsvUpdateInterval(), globalConfig.getCsvGaugeUpdateInterval());
    }

    void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }

    void setParallelPublishers(int parallelPublishers) {
        this.parallelPublishers = parallelPublishers;
    }

    void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public int getParallelPublishers() {
        return parallelPublishers;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
