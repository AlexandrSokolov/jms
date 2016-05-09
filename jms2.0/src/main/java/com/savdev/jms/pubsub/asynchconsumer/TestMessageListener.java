package com.savdev.jms.pubsub.asynchconsumer;

import java.util.function.Function;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 */
public class TestMessageListener implements MessageListener {

    public TestMessageListener(Function<String, Void> function) {
        this.function = function;
    }

    final Function<String, Void> function;

    @Override
    public void onMessage(Message message) {
        try {
            function.apply(message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
