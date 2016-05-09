package com.savdev.jms.ptp.synchconsumer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 */
public class PtpBlockingTimeoutConsumer1 {

    @Resource(mappedName = "java:/testJmsQueueEntryName")
    private Queue queue;

    @Inject
    JMSContext context;


    public String receiveMessage() {
        //never use: context.createConsumer(queue).receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));
        //consumer must be closed
        try(JMSConsumer consumer = context.createConsumer(queue);){
            return consumer.receiveBody(String.class, TimeUnit.SECONDS.toMillis(5));

        }
    }
}
