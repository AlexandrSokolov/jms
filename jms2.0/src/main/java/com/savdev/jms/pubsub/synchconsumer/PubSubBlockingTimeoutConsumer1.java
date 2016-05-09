package com.savdev.jms.pubsub.synchconsumer;

import java.util.function.Function;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 */
public class PubSubBlockingTimeoutConsumer1 {

    @Resource(lookup = "jmsTestTopicEntryName")
    private Topic topic;

    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    JMSContext context;

    public void startListen(final Function<String, Void> function) {
        //never use: context.createConsumer(defaultQueue).receiveBody(String.class, TimeUnit.SECONDS.toMillis(10));
        //consumer must be closed
        //creates a consumer on an unshared nondurable subscription if a topic is specified as the destination.
        try(JMSConsumer consumer = context.createConsumer(topic)){
            function.apply(consumer.receiveBody(String.class));
        }
    }
}
