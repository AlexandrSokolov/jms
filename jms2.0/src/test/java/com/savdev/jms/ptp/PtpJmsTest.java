package com.savdev.jms.ptp;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.savdev.jms.ptp.consumers.PtpBlockingTimeoutConsumer1;
import com.savdev.jms.ptp.consumers.PtpBlockingTimeoutConsumer2;
import com.savdev.jms.ptp.producers.PtpProducer;

/**
 *
 */
@RunWith(Arquillian.class)
public class PtpJmsTest {

    public static final String MESSAGE_1 = "Test message1";
    public static final String MESSAGE_2 = "Test message2";
    @Inject
    PtpProducer ptpProducer;

    @Inject
    PtpBlockingTimeoutConsumer1 consumer;

    @Inject
    PtpBlockingTimeoutConsumer2 consumer2;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(PtpProducer.class)
                .addClass(PtpBlockingTimeoutConsumer2.class)
                .addClass(PtpBlockingTimeoutConsumer1.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    /*
        Simple usecase, send one message. The only consumer gets it.
     */
    @Test
    public void testSendMessageAndConsumeItSuccessfully() {
        ptpProducer.sendMessage(MESSAGE_1);
        Assert.assertEquals(MESSAGE_1, consumer.receiveMessage());
    }

    /*
        Test shows that with PTP (Queue interface is used) only one client can consume a message.
        If two clients try to consume message,
        and only one message has been sent, the other one, if synchronious consumer is used, will be either blocked
        or wait for timeout
     */
    @Test
    public void testSendMessageWith2PtpConsumers() {
        ptpProducer.sendMessage(MESSAGE_1);
        Assert.assertEquals(MESSAGE_1, consumer.receiveMessage());
        Assert.assertNull(consumer2.receiveMessage());
    }

    /*
        With PTP only one client gets a message.
        If message queue is empty, other consumers either just blocked or wait for a time out.
     */
    @Test
    public void testSendMessageTwiceWith2PtpConsumers() {
        ptpProducer.sendMessage(MESSAGE_1);
        Assert.assertEquals(MESSAGE_1, consumer.receiveMessage());
        Assert.assertNull(consumer2.receiveMessage());
        ptpProducer.sendMessage(MESSAGE_2);
        Assert.assertEquals(MESSAGE_2, consumer2.receiveMessage());
        Assert.assertNull(consumer.receiveMessage());
    }
//
//    /*
//        With PTP only one client gets a message.
//        If message queue is empty, other consumers either just blocked or wait for a time out.
//     */
//    @Test
//    public void testAutomaticResourceDefinitionUsage() {
//        automaticResourceDefinitionProducer.sendMessage(MESSAGE_1);
//        Assert.assertEquals(MESSAGE_1, automaticResourceDefinitionConsumer.receiveMessage());
//    }
//
//    /*
//        With PTP only one client gets a message.
//        If message queue is empty, other consumers either just blocked or wait for a time out.
//     */
//    @Test
//    public void testSendMessageWithAsyncronousConsumer() {
//        ptpProducer4AsyncMdbConsumer.sendMessage(MESSAGE_1);
//    }
}
