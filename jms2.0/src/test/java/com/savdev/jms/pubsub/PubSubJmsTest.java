package com.savdev.jms.pubsub;


import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.savdev.jms.pubsub.consumers.PubSubBlockingTimeoutConsumer1;
import com.savdev.jms.pubsub.producers.PubSubProducer;

/**
 */

@RunWith(Arquillian.class)
public class PubSubJmsTest {

    public static final String MESSAGE_1 = "Test message1";

    @Inject
    PubSubProducer pubSubProducer;

    @Inject
    PubSubBlockingTimeoutConsumer1 pubSubBlockingTimeoutConsumer1;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(PubSubProducer.class)
                .addClass(PubSubBlockingTimeoutConsumer1.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    /*
        Simple usecase, send one message. The only consumer gets it.
     */
    @Test
    public void testSendMessageAndConsumeItSuccessfully() {
        pubSubProducer.sendMessage(MESSAGE_1);
        Assert.assertEquals(MESSAGE_1, pubSubBlockingTimeoutConsumer1.receiveMessage());
    }
}
