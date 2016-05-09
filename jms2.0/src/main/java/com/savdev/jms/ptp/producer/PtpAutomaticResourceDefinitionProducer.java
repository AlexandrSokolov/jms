package com.savdev.jms.ptp.producer;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Queue;

/**
 *  Usage Automatic Resource Definitions
 *  Connection factories and destinations defined in this way must be in the:
 *  java:comp, java:module, java:app, or java:global namespaces,
 *  and they typically exist for as long as the application that defines them is deployed.
 */
@JMSConnectionFactoryDefinition(
        name = "java:global/jms/MyConnectionFactory",
        user = "guest",
        maxPoolSize = 30,
        minPoolSize = 20,
        properties = {
                "addressList=mq://localhost:7676",
                "reconnectEnabled=true",
                ""
        }
)
@JMSDestinationDefinition(
        name = "java:global/jms/DemoQueue",
        interfaceName = "javax.jms.Queue",
        destinationName = "demoQueue"
)
public class PtpAutomaticResourceDefinitionProducer {
    @Resource(mappedName = "java:global/jms/DemoQueue")
    private Queue defaultQueue;

    @Inject
    @JMSConnectionFactory("java:global/jms/MyConnectionFactory")
    JMSContext context;

    public void sendMessage(final String message) {
        context.createProducer().send(defaultQueue, message);
        System.out.printf("test");
    }
}
