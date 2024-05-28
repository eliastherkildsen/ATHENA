package org.apollo.template.persistence;

import org.apollo.template.Service.Logger.LoggerMessage;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    // This is the topic class related to PUB/SUB

    // topic name
    String topicName;

    // list for contaning all the subscribers.
    private List<Subscriber> subscriberList = new ArrayList<>();

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Method for unsubscribing to a topic
     * @param subscriber Subscriber.
     */
    public void subscribe(Subscriber subscriber){

        // check if subscriber is null
        if (subscriber == null){
            LoggerMessage.error(this, "The subscriber: " + subscriber + " Is NULL!");
            return;
        }

        // checks if the subscriber already exists in subscriberList
        if (subscriberList.contains(subscriber)){
            LoggerMessage.error(this, "The subscriber: " + subscriber + " does alrady exist in: "
                    + this + " For topic: " + topicName);
            return;
        }

        // add the subscriber to the subscriber list
        subscriberList.add(subscriber);
        LoggerMessage.info(this, "The subscriber: " + subscriber + " Has been added to topic: " + topicName);

    }

    /**
     * Method for unsubscribing to a topic
     * @param subscriber Subscriber.
     */
    public void unSubscribe(Subscriber subscriber){

        // checks if the subscriber obj is NULL.
        if (subscriber == null){
            LoggerMessage.info(this, "The subscriber: " + subscriber + " Is NULL!");
            return;
        }

        // checks if the subscriber does not exists in the list.
        if (!subscriberList.contains(subscriber)){
            LoggerMessage.error(this, "The subscriber: " + subscriber + " does not exist in: " +
                    this + " for topic: " + topicName);
            return;
        }

        // removes the subscriber from the subscriber list.
        subscriberList.remove(subscriber);

    }

    /**
     * Method for publishing messages to the subscribers.
     * @param message obj.
     */
    public void publish(Message message){
        for (Subscriber subscriber : subscriberList){
            subscriber.update(message);
        }
    }

    public String getTopicName() {
        return topicName;
    }
}
