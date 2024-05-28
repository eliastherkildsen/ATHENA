package org.apollo.template.persistence;

public class Message {

    // this is the object that a subscriber can receive.

    private Object object;

    public Message(Object o) {
        this.object = o;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
