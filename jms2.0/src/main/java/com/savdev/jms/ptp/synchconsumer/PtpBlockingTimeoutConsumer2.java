package com.savdev.jms.ptp.synchconsumer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 */
public class PtpBlockingTimeoutConsumer2 {

    @Resource(mappedName = "java:/testJmsQueueEntryName")
    private Queue queue;

    @Inject
    JMSContext context;

    public String receiveMessage() {
        try(JMSConsumer consumer = context.createConsumer(queue);){
            return consumer.receiveBody(String.class, TimeUnit.SECONDS.toMillis(5));
        }
    }
}
