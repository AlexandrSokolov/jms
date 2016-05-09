package com.savdev.jms.pubsub;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.savdev.jms.pubsub.asynchconsumer.PubSubAsyncConsumer;
import com.savdev.jms.pubsub.producer.PubSubProducer;

/**
 */

@RunWith(Arquillian.class)
public class PubSubAsyncJmsTest {

    public static final String MESSAGE_1 = "Test message1";

    @Inject
    PubSubProducer pubSubProducer;

    @Inject
    PubSubAsyncConsumer pubSubAsyncConsumer;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(PubSubProducer.class)
                .addClass(PubSubAsyncConsumer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    /*
        Simple usecase, send one message. The only consumer gets it.
     */
    @Test
    public void testSendMessageAndConsumeItSuccessfully() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(
            new Runnable() {
                @Override
                public void run() {
                    pubSubAsyncConsumer.startListen(new Function<String, Void>() {
                        @Override
                        public Void apply(final String message) {
                            Assert.assertEquals(MESSAGE_1, message);
                            return null;
                        }
                    });
                }
            }
        );
        pubSubProducer.sendMessage(MESSAGE_1);
    }
}
