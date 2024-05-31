use db_Athena;

GO

CREATE PROCEDURE getAvailableRooms (@BookingDate DATE)
AS
BEGIN

    -- Calculates the total booking time for each room on the specified date
    WITH BookedTime AS (
        SELECT
            fld_roomID,
            SUM(DATEDIFF(MINUTE, fld_startTime, fld_endTime)) AS total_booked_time_minutes
        FROM
            tbl_booking
        WHERE
            fld_date = @BookingDate
        GROUP BY
            fld_roomID
    )

    -- Selects selected fields from 4 tables related to each other using INNER JOIN - these are to be presented when displaying available rooms
    SELECT
        tbl_room.fld_roomID,
        tbl_room.fld_roomName,
        tbl_room.fld_floor,
        tbl_room.fld_roomMaxPersonCount,
        tbl_booking.fld_bookingID,
        tbl_roomType.fld_roomTypeName,
        BookedTime.total_booked_time_minutes
    FROM
        tbl_room
        INNER JOIN tbl_booking ON tbl_room.fld_roomID = tbl_booking.fld_roomID
        INNER JOIN tbl_roomInventory ON tbl_room.fld_roomID = tbl_roomInventory.fld_roomID
        INNER JOIN tbl_inventory ON tbl_roomInventory.fld_roomInventoryID = tbl_inventory.fld_inventoryID
        INNER JOIN tbl_roomType ON tbl_room.fld_roomTypeID = tbl_roomType.fld_roomTypeID
        LEFT JOIN BookedTime ON tbl_room.fld_roomID = BookedTime.fld_roomID

    -- Filters the results to include only rooms that are either not booked on the specified date or have a total booked time less than 8 hours (480 minutes)
    WHERE
        fld_date = @BookingDate
    AND COALESCE(BookedTime.total_booked_time_minutes, 0) < 480

    -- The available rooms are sorted by room name (ascending) and start time (ascending)
    ORDER BY
        tbl_room.fld_roomName, tbl_booking.fld_startTime ASC;
END;
