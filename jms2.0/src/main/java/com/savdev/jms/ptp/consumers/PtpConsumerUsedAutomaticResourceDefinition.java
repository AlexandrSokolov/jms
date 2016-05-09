package com.savdev.jms.ptp.consumers;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 */
public class PtpConsumerUsedAutomaticResourceDefinition {
    @Resource(mappedName = "java:global/jms/DemoQueue")
    private Queue defaultQueue;

    @Inject
    @JMSConnectionFactory("java:global/jms/MyConnectionFactory")
    JMSContext context;


    public String receiveMessage() {
        //never use: context.createConsumer(defaultQueue).receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));
        //consumer must be closed
        try(JMSConsumer consumer = context.createConsumer(defaultQueue);){
            return consumer.receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));

        }
    }
}
