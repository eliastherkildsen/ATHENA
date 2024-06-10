package org.apollo.template.persistence.PubSub;

import javafx.util.Pair;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.ConfigLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessagesBrokerTest implements Subscriber{
    /*
    This test aims to validate that the messages broker in the system works as intended.
     */

    private Room room;
    private MeetingType meetingType;
    private Booking booking;
    private Email email;
    private Object recivedObject;
    private Pair<MessagesBrokerTopic, Subscriber> pair;

    // Setting up an environment for the tests.
    @BeforeEach
    void setUp() {

        // setting up debugger environment.
        ConfigLoader.get();

        // creating a mock object to run the test on.
        booking = new Booking();
        room = new Room();
        meetingType = new MeetingType();
        email = new Email(16, "testEmail@easv365.dk");

        // constructing meetingType
        meetingType.setMeetingTypeID(2);
        meetingType.setMeetingTypeName("Møde");

        // constructing room
        room.setRoomID(10);
        room.setRoomName("301");
        room.setFloor(2);
        room.setRoomMaxPersonCount(35);

        // constructing the booking.
        booking.setMeetingType(meetingType);
        booking.setUsername("bob");
        booking.setStartTime(Time.valueOf("08:00:00"));
        booking.setEndTime(Time.valueOf("10:00:00"));
        booking.setEmail(email);
        booking.setBookingID(12);
        booking.setNumberOfParticipants(16);
        booking.setAdHoc(true);
        booking.setUsername("peter");

        pair = new Pair<>(MessagesBrokerTopic.BOOKING_INFORMATION, this);

    }

    // EQ testing.

    /**
     * Method for testing that when a class subscribes to a topic it is also registered
     */
    @Test
    void subscriberTestValid(){

        // subscribing to the messages broker.
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        // fetching the subscribers.
        List<Pair<MessagesBrokerTopic, Subscriber>> messagesBrokerSubList = MessagesBroker.getInstance().getSubscriberList();

        // validating that this has subscribed to the messages broker.
        boolean actual = messagesBrokerSubList.contains(pair);
        boolean expected = true;

        // testing that the expected value was received.
        assertEquals(expected, actual);

    }

    /*
    in this case nobody has subscribed to the messages broker, therefor this list should return 0
     */
    @Test
    void subscriberTestInvalid() {

        int expected = 0;
        int actual = MessagesBroker.getInstance().getSubscriberList().size();

        assertEquals(expected, actual);

    }

    /*
    This test aims to validate that a class can subscribe, and unsubscribe
     */
    @Test
    void unSubscribeTestValid() {

        // subscribing the messagesBroker.
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        // unsubscribing the messageBroker.
        try {
            MessagesBroker.getInstance().unSubscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // validating that this does not exist in the messages broker sub list.
        boolean actual = MessagesBroker.getInstance().getSubscriberList().contains(pair);
        boolean exspected = false;
        assertEquals(exspected, actual);

    }

    /*
    This test aims to ensure that you can not call unsubscribe if your not already subscribed.
    should throw an error.
     */
    @Test
    void unSubscribeTestInvalid() {

        boolean actual;
        boolean exspected = false;

        // unSubscribing to messages broker. catching error as boolean false.

        try {
            MessagesBroker.getInstance().unSubscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);
            actual = true;
        } catch (Exception e) {
            actual = false;
        }

        assertEquals(exspected, actual);

    }

    /*
    This test aims to ensure that its possible to publish an object, and that the right object is afterwords received.
     */
    @Test
    void publishObjectValid() {

        // subscribing to the messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        // publishing object.
        try {
            MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, booking);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Object o = recivedObject;
        // casting object to booking.
        Booking actualBooking = (Booking) o;

        assertEquals(actualBooking, booking);

    }

    /*
    This test aims to ensure that it is not possible to publish an object witch is null
    */
    @Test
    void publishObjectInvalid() {

        boolean exspected = false;
        boolean actual;

        // subscribing to the messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        // publishing object.
        try {
            MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, null);
            actual = true;
        } catch (Exception e) {
            actual = false;
        }


        assertEquals(exspected, actual);

    }


    @AfterEach
    void tearDown() {
        // setting all obj to null
        booking = null;
        email = null;
        room = null;
        meetingType = null;
    }

    @Override
    public void update(Object o) {
        recivedObject = o;
    }
}