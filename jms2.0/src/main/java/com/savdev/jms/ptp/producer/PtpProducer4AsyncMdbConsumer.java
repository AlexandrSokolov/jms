package com.savdev.jms.ptp.producer;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * In order to use another queue for mdb consumer, create one more producer
 */
public class PtpProducer4AsyncMdbConsumer {

    @Resource(mappedName = "java:/jms/queue/ExpiryQueue4PtpAsyncMDbConsumer")
    private Queue defaultQueue;


    @Inject
    JMSContext context;

    public void sendMessage(final String message)
    {
        context.createProducer().send(defaultQueue, message);
    }
}
