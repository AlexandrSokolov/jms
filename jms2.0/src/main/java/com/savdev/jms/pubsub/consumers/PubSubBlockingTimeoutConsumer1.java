package com.savdev.jms.pubsub.consumers;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 */
public class PubSubBlockingTimeoutConsumer1 {
    @Resource(mappedName = "jmsTestTopicEntryName")
    private Topic topic;

    @Inject
    JMSContext context;

    public String receiveMessage() {
        //never use: context.createConsumer(defaultQueue).receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));
        //consumer must be closed
        try(JMSConsumer consumer = context.createConsumer(topic);){
            return consumer.receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));
        }
    }
}
