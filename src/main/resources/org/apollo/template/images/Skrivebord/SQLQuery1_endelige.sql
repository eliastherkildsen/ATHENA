-- Stored procedure to find available rooms on a given date
CREATE PROCEDURE getAvailableRooms (@BookingDate DATE)
AS
BEGIN

    -- Calculates the total booking time for each room on the specified date ('NULL' if no booking yet)
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

	-- Selects selected fields from 3 tables related to each other using INNER JOIN and LEFT JOIN - these are to be presented when displaying available rooms
    SELECT
        tbl_room.fld_roomName,
		tbl_room.fld_floor,
		tbl_room.fld_roomMaxPersonCount,
		tbl_roomType.fld_roomTypeName

    FROM
        tbl_room
		LEFT JOIN BookedTime ON tbl_room.fld_roomID = BookedTime.fld_roomID
		INNER JOIN tbl_roomType ON tbl_room.fld_roomTypeID = tbl_roomType.fld_roomTypeID

	-- Filters the results to include only rooms that are either not booked on the specified date or have a total booked time less than 8 hours (480 minutes)
    WHERE
        BookedTime.total_booked_time_minutes < 480 OR BookedTime.total_booked_time_minutes IS NULL
	
	-- Groups the results by four columns to ensure that we only see each room once in the list, even if there are multiple bookings on the selected day.
	GROUP BY
    tbl_room.fld_roomName,
    tbl_room.fld_floor,
    tbl_roomType.fld_roomTypeName,
	tbl_room.fld_roomMaxPersonCount
    
	-- The available rooms are sorted by room name (ascending) and start time (ascending)
    ORDER BY
        tbl_room.fld_roomName ASC;

END;
