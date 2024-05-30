USE db_Athena;

GO

CREATE PROCEDURE getAvailableRoomsTEST (@BookingDate DATE)
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
        tbl_roomType.fld_roomTypeName,
        COALESCE(BookedTime.total_booked_time_minutes, 0) AS total_booked_time_minutes
    FROM
        tbl_room
        LEFT JOIN tbl_roomType ON tbl_room.fld_roomTypeID = tbl_roomType.fld_roomTypeID
        LEFT JOIN BookedTime ON tbl_room.fld_roomID = BookedTime.fld_roomID
    WHERE
        COALESCE(BookedTime.total_booked_time_minutes, 0) < 480
    ORDER BY
        tbl_room.fld_roomName;

END;
GO
