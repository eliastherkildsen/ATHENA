package org.apollo.template.persistence;

import org.apollo.template.Model.BookingInformation;

public interface Subscriber {

    void update(BookingInformation bookingInformation);

}
