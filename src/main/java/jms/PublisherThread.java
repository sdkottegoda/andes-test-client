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

package jms;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;

import java.util.concurrent.atomic.AtomicInteger;

import static com.codahale.metrics.MetricRegistry.name;

public class PublisherThread implements Runnable {

    private static Log log = LogFactory.getLog(PublisherThread.class);
    private final Meter publishRate;

    private SimpleJMSPublisher jmsPublisher;
    private AtomicInteger sentCount;

    public PublisherThread(SimpleJMSPublisher publisher) {
        jmsPublisher = publisher;
        sentCount = new AtomicInteger(0);
        publishRate = Main.metrics.meter(name(
                        PublisherThread.class, "publisher", publisher.getConfigs().getId(), "meter")
        );

        // Per given period how many messages were sent is taken through this gauge
        Main.gauges.register(
                name(PublisherThread.class, jmsPublisher.getConfigs().getId(), "sent-stats"),
                new Gauge<Integer>() {

                    /**
                     * number of messages sent since last call to this method is returned
                     * @return Integer
                     */
                    @Override
                    public Integer getValue() {
                        int val = sentCount.get();
                        sentCount.addAndGet(-val);
                        return val;
                    }
                });
    }

    @Override
    public void run() {
        long messageCount = jmsPublisher.getConfigs().getMessageCount();
        String publisherID = jmsPublisher.getConfigs().getId();

        log.info("Starting publisher to send " + messageCount + " messages. Publisher ID: " + publisherID);
        Message message = null;

        try {
            for (int i = 1; i <= messageCount; i++) {

                message = jmsPublisher.createTextMessage(i + " Publisher: " + publisherID);
                jmsPublisher.send(message);

                if (log.isTraceEnabled()) {
                    log.trace("message published: " + message);
                }
                sentCount.incrementAndGet();
                publishRate.mark();
            }

            log.info("Stopping publisher. [ Publisher ID: " + jmsPublisher.getConfigs().getId() + "  ]");
            jmsPublisher.close();
        } catch (JMSException e) {
            log.error("Exception occurred while publishing. " +
                    "\n\tPublisher ID: " + publisherID +
                    "\n\tMessage: " + message, e);
        }

        log.info("Stopped publisher. [ Publisher ID: " + jmsPublisher.getConfigs().getId() + "  ]");
    }
}
