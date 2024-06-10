package org.apollo.template.persistence.PubSub;

import javafx.util.Pair;
import org.apollo.template.Service.Logger.LoggerMessage;

import javax.lang.model.element.UnknownElementException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public class MessagesBroker {

    private static MessagesBroker instance;
    // list of subs and their topic
    private List<Pair<MessagesBrokerTopic, Subscriber>> subscriberList = new ArrayList<>();

    // making messagesBroker private, to follow singleton principal.
    private MessagesBroker() {}

    /**
     * Method for subscribing to a topic.
     * @param subscriber Subscriber
     * @param topic MessagesBrokerTopic
     */
    public synchronized void subscribe(Subscriber subscriber, MessagesBrokerTopic topic){
        // creating pair.
        Pair<MessagesBrokerTopic, Subscriber> pair = new Pair<MessagesBrokerTopic, Subscriber>(topic, subscriber);

        // checking if pair already exists.
        if (subscriberList.contains(pair)){
            LoggerMessage.info(this, "The pair is already subscribed!");
            return;
        }

        // adding pair to subscriber list.
        subscriberList.add(pair);
        LoggerMessage.info(this, "added " + pair + " to subscribers");

    }
    /**
     * Method for unsubscribing from a topic.
     * @param subscriber Subscriber
     * @param topic MessagesBrokerTopic
     */
    public synchronized void unSubscribe(Subscriber subscriber, MessagesBrokerTopic topic) throws Exception {
        // creating pair.
        Pair<MessagesBrokerTopic, Subscriber> pair = new Pair<MessagesBrokerTopic, Subscriber>(topic,subscriber);

        // check if the subscriber is subscribed
        if (!subscriberList.contains(pair)){
            LoggerMessage.warning(this, "The subscriber: " + subscriber +
                    " Does not subscribe to this topic: " + topic);
            throw new Exception("The subscriber: " + subscriber +
                    " Does not subscribe to this topic: " + topic);
        }

        // removing the pair from subscriberList
        subscriberList.remove(pair);
        LoggerMessage.warning(this, "Removed pair: " + pair + " from subscriberList");

    }
    /**
     * Method for publishing an object.
     * @param topic MessagesBrokerTopic
     * @param messages String
     */
    public synchronized void publish(MessagesBrokerTopic topic, Object messages) throws Exception {

        // validating that the messages object is not null
        if (messages == null){
            throw new Exception();
        }

        // looping through all pairs in subscriberList
        for (Pair<MessagesBrokerTopic, Subscriber> pair : subscriberList){
        LoggerMessage.debug(this,pair.getKey() + " - " + pair.getValue());
            if (topic.equals(pair.getKey())){
                LoggerMessage.trace(this, "Object : " + messages.toString());
                pair.getValue().update(messages);

            }
        }
    }


    public synchronized static MessagesBroker getInstance(){
        if (instance == null){
            instance = new MessagesBroker();
        }

        return instance;
    }

    public List<Pair<MessagesBrokerTopic, Subscriber>> getSubscriberList() {
        return subscriberList;
    }
}
