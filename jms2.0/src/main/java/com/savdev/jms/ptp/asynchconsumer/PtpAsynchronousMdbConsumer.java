package com.savdev.jms.ptp.asynchconsumer;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 */
@MessageDriven(mappedName="ExpiryQueue4PtpAsyncMDbConsumer", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup",
//                // not "java:/jms/queue/ExpiryQueue4PtpAsyncMDbConsumer":
//                propertyValue = "jms/queue/ExpiryQueue4PtpAsyncMDbConsumer"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})
public class PtpAsynchronousMdbConsumer implements MessageListener {

    public PtpAsynchronousMdbConsumer() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
