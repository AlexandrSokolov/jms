package com.savdev.jms.pubsub.asynchconsumer;

import java.util.function.Function;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 */
public class PubSubAsyncConsumer {

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
            TestMessageListener testMessageListener = new TestMessageListener(function);
            consumer.setMessageListener(testMessageListener);
        }
    }
}
