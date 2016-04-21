package com.savdev.producers;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Created by alexandr on 19.04.16.
 */
public class PtpProducer {

    @Resource(mappedName = "java:/jms/queue/ExpiryQueue")
    private Queue defaultQueue;


    /*
        Default value for @JMSConnectionFactory is:
        @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
        Can be configured in wildfly via:
        <default-bindings ... jms-connection-factory="java:jboss/DefaultJMSConnectionFactory" .../>
        So when inject we can avoid using of it.
     */
    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")  //not needed when default connection factory is used
    JMSContext context;

    public void sendMessage(final String message)
    {
        context.createProducer().send(defaultQueue, message);
        System.out.printf("test");;
    }
}
