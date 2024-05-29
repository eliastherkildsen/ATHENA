package org.apollo.template.persistence.PubSub;

import org.apollo.template.Model.BookingInformation;

public interface Subscriber {

    void update(BookingInformation bookingInformation);

}
