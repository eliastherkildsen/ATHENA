package org.apollo.template.persistence.PubSub;

import javafx.util.Pair;
import org.apollo.template.Service.Logger.LoggerMessage;
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
    public void subscribe(Subscriber subscriber, MessagesBrokerTopic topic){
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
    public void unSubscribe(Subscriber subscriber, MessagesBrokerTopic topic){
        // creating pair.
        Pair<Subscriber, MessagesBrokerTopic> pair = new Pair<Subscriber, MessagesBrokerTopic>(subscriber, topic);

        // check if the subscriber is subscribed
        if (!subscriberList.contains(pair)){
            LoggerMessage.info(this, "The subscriber: " + subscriber +
                    " Does not subscribe to this topic: " + topic);
            return;
        }

        // removing the pair from subscriberList
        subscriberList.remove(pair);
        LoggerMessage.info(this, "Removed pair: " + pair + " from subscriberList");

    }

    /**
     * Method for publishing an object.
     * @param topic MessagesBrokerTopic
     * @param messages String
     */
    public void publish(MessagesBrokerTopic topic, Object messages){

        // looping through all pairs in subscriberList
        for (Pair<MessagesBrokerTopic, Subscriber> pair : subscriberList){

            if (topic == pair.getKey()){

                pair.getValue().update(messages);

            }

        }

    }



    public static MessagesBroker getInstance(){
        if (instance == null){
            instance = new MessagesBroker();
        }

        return instance;

    }

}
