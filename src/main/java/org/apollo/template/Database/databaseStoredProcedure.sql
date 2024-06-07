CREATE PROCEDURE AddEmailIfNotExists
@EmailAddress NVARCHAR(255)
AS
BEGIN
    -- Check if the email already exists
    IF NOT EXISTS (SELECT 1 FROM tbl_userEmail WHERE fld_userEmail = @EmailAddress)
        BEGIN
            -- Insert the email into the table
            INSERT INTO tbl_userEmail(fld_userEmail)
            VALUES (@EmailAddress);

            -- Indicate success
            SELECT 'Email added successfully' AS Result;
        END
    ELSE
        BEGIN
            -- Indicate that the email already exists
            SELECT 'Email already exists' AS Result;
        END
END;
GO

-- Function for InfoScreen
CREATE PROCEDURE GetBookingsByDate
@BookingDate DATE -- We are doing this by date.
AS
BEGIN
    -- SELECT the relevant data fields i want it to return.
    SELECT
        tbl_booking.fld_startTime,
        tbl_booking.fld_endTime,
        tbl_booking.fld_userName,
        tbl_room.fld_roomName,
        tbl_meetingType.fld_meetingType
    FROM
        tbl_booking
            INNER JOIN tbl_room ON tbl_booking.fld_roomID = tbl_room.fld_roomID
            INNER JOIN tbl_meetingType ON tbl_booking.fld_meetingTypeID = tbl_meetingType.fld_meetingTypeID
    WHERE
        tbl_booking.fld_date = @BookingDate
    ORDER BY
        tbl_booking.fld_startTime ASC,
        tbl_booking.fld_endTime ASC;
END;
GO

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
        tbl_room.fld_roomID,
        tbl_room.fld_roomName,
        tbl_room.fld_floor,
        tbl_room.fld_roomMaxPersonCount,
        tbl_room.fld_roomTypeID,
        tbl_roomType.fld_roomTypeName,
        tbl_roomType.fld_roomTypeDescription

    FROM
        tbl_room
            LEFT JOIN BookedTime ON tbl_room.fld_roomID = BookedTime.fld_roomID
            INNER JOIN tbl_roomType ON tbl_room.fld_roomTypeID = tbl_roomType.fld_roomTypeID

    -- Filters the results to include only rooms that are either not booked on the specified date or have a total booked time less than 8 hours (480 minutes)
    WHERE
        BookedTime.total_booked_time_minutes < 480 OR BookedTime.total_booked_time_minutes IS NULL

    -- Groups the results by five columns to ensure that we only see each room once in the list, even if there are multiple bookings on the selected day.
    GROUP BY
        tbl_room.fld_roomID,
        tbl_room.fld_roomName,
        tbl_room.fld_floor,
        tbl_room.fld_roomTypeID,
        tbl_roomType.fld_roomTypeName,
        tbl_roomType.fld_roomTypeDescription,
        tbl_room.fld_roomMaxPersonCount

    -- The available rooms are sorted by room name (ascending) and start time (ascending)
    ORDER BY
        tbl_room.fld_roomName ASC;

END;
GO

-- Gets Room Name from a given Room ID
CREATE PROCEDURE getRoomNameFromID (@RoomID int)
AS
BEGIN
    SELECT
        tbl_room.fld_roomName
    FROM
        tbl_room
    WHERE
        fld_roomID = @RoomID
END
GO

-- Stored procedure to find all bookings on a specific and in a specific room
CREATE PROCEDURE getBookingsFromDate (@BookingDate Date, @RoomID INTEGER)
AS
BEGIN

    SELECT
        tbl_booking.fld_startTime,
        tbl_booking.fld_endTime
    FROM
        tbl_booking
    WHERE
        fld_date = @BookingDate AND fld_roomID = @RoomID

END;
GO

-- Stored procedure to finde email id by email adress

CREATE PROCEDURE getEmailIDByEmailAdress(@emailAdress varchar(256))
AS
BEGIN
    SELECT
        fld_userID
    FROM
        tbl_userEmail
    WHERE
        fld_userEmail = @emailAdress

END;
GO

-- Stored procedure for getting the meetingTypeid by passing the meetingType name
CREATE PROCEDURE getMeetingTypeIDByMeetingType(@meetingType varchar(50))
AS
BEGIN
    SELECT
        fld_meetingTypeID
    FROM
        tbl_meetingType
    WHERE
        fld_meetingType = @meetingType
END;
GO

CREATE PROCEDURE FindbookingByEmail (@EmailAddress NVARCHAR(255))
AS
BEGIN

    SELECT tbl_booking.fld_bookingID, tbl_userEmail.fld_userEmail, tbl_room.fld_roomName, fld_startTime, fld_endTime, fld_userName,
           tbl_meetingType.fld_meetingType

    FROM tbl_booking
             INNER JOIN tbl_userEmail ON tbl_booking.fld_userID = tbl_userEmail.fld_userID
             INNER JOIN tbl_room ON tbl_booking.fld_roomID = tbl_room.fld_roomID
             INNER JOIN tbl_meetingType on tbl_booking.fld_meetingTypeID = tbl_meetingType.fld_meetingTypeID

    WHERE
        tbl_userEmail.fld_userEmail = @EmailAddress;
END;
GO

CREATE PROCEDURE getAllRoomTypeNames
AS
BEGIN
    SELECT
        fld_roomTypeName
    FROM
        tbl_roomType
END;
GO

CREATE PROCEDURE getErrorReports
AS
BEGIN
    SELECT
        -- Selecting data from error report entity
        tbl_errorReport.fld_archived, tbl_errorReport.fld_errorReportID,
        tbl_errorReport.fld_reportDate, tbl_errorReport.fld_reportDescription,

        -- selecting data from email entity
        tbl_userEmail.fld_userEmail, tbl_userEmail.fld_userID,

        -- selecting data from room entity
        tbl_room.fld_roomName, tbl_room.fld_roomID,

        -- select data from
        tbl_inventory.fld_inventoryName, tbl_inventory.fld_inventoryID


    FROM tbl_errorReport
             INNER JOIN tbl_userEmail on tbl_errorReport.fld_userID = tbl_userEmail.fld_userID
             INNER JOIN tbl_room on tbl_errorReport.fld_roomID = tbl_room.fld_roomID
             INNER JOIN tbl_inventory on tbl_errorReport.fld_inventoryID = tbl_inventory.fld_inventoryID
END;
GO

CREATE PROCEDURE GetAllBookingsFromTodayAndOnwards (@date Date)
AS
BEGIN
    SELECT tbl_booking.fld_bookingID, tbl_userEmail.fld_userEmail, tbl_room.fld_roomName, fld_startTime, fld_endTime, fld_userName,
           tbl_meetingType.fld_meetingType

    FROM tbl_booking
             INNER JOIN tbl_userEmail ON tbl_booking.fld_userID = tbl_userEmail.fld_userID
             INNER JOIN tbl_room ON tbl_booking.fld_roomID = tbl_room.fld_roomID
             INNER JOIN tbl_meetingType on tbl_booking.fld_meetingTypeID = tbl_meetingType.fld_meetingTypeID

    WHERE
        tbl_booking.fld_date >= @date;
END;
GO

CREATE PROCEDURE getNumberOfBookingsFromRoomID (@roomID INTEGER)
AS
BEGIN
    SELECT COUNT(fld_bookingID)
    FROM tbl_booking
    WHERE fld_roomID = @roomID
END;
GO

CREATE PROCEDURE GetRoomIDFromName (@roomName varChar(50))
AS
BEGIN
    SELECT fld_roomID
    FROM tbl_room
    WHERE fld_roomName = @roomName
END;
GO

CREATE PROCEDURE GetAvailableRoomsForDateTimeRange
    @startDate DATE,
    @startTime TIME,
    @endDate DATE,
    @endTime TIME,
    @maxPersonCount INT
AS
BEGIN
    SELECT
        tbl_room.fld_roomID,
        tbl_room.fld_roomName,
        tbl_room.fld_roomMaxPersonCount,
        tbl_room.fld_roomTypeID,
        tbl_room.fld_floor,
        tbl_roomType.fld_roomTypeID AS roomType_ID,
        tbl_roomType.fld_roomTypeDescription,
        tbl_roomType.fld_roomTypeName
    FROM
        tbl_room
            INNER JOIN
        tbl_roomType ON tbl_room.fld_roomTypeID = tbl_roomType.fld_roomTypeID
    WHERE
        tbl_room.fld_roomMaxPersonCount >= @maxPersonCount AND
        NOT EXISTS (
            SELECT 1
            FROM tbl_booking
            WHERE tbl_room.fld_roomID = tbl_booking.fld_roomID
              AND tbl_booking.fld_date >= @startDate
              AND tbl_booking.fld_date <= @endDate
              AND ((tbl_booking.fld_startTime < @endTime AND tbl_booking.fld_endTime > @startTime)
                OR (tbl_booking.fld_startTime < @endTime AND tbl_booking.fld_endTime > @endTime)
                OR (tbl_booking.fld_startTime < @startTime AND tbl_booking.fld_endTime > @startTime))
        );
END;
GO

CREATE PROCEDURE getRoomTypeIDFromName (@RoomTypeName varChar(50))
AS
BEGIN
    SELECT
        *
    FROM
        tbl_roomType
    WHERE
        fld_roomTypeName = @RoomTypeName
END;
GO


-- Stored procedure to find total booking time in minutes per booking in a given room
CREATE PROCEDURE getTotalBookingTimePerBooking(@roomID int, @date Date)
AS
BEGIN

    SELECT

        fld_bookingID,

        -- Calculates total booking time per booking
        SUM(DATEDIFF(MINUTE, fld_startTime, fld_endTime)) AS total_booking_time


    FROM
        tbl_booking

    WHERE
        fld_roomID = @roomID
      AND fld_date = @date

    -- Groups the results by bookingID so each bookingID only appears once (with the the total booking time)
    GROUP BY
        fld_bookingID
    -- Orders the results by bookingID
    ORDER BY
        fld_bookingID

END;
GO


-- Stored procedure to find total booking time in minutes per day in a given room
CREATE PROCEDURE getTotalBookingTimePerDay(@roomID INT, @startDate DATE, @endDate DATE)

AS
BEGIN
    -- Generates all dates in the DateRange (from startDate - endDate)
    WITH DateRange AS (

        SELECT @startDate AS fld_date

        -- Combines all SELECT results
        UNION ALL

        -- Adds one day to fld_date
        SELECT DATEADD(DAY, 1, fld_date)

        FROM DateRange

        -- Ensures that we only continue adding dates as long as the new date (after adding one day) is less than or equal to @endDate
        WHERE DATEADD(DAY, 1, fld_date) <= @endDate
    )

    SELECT
        -- Takes a date, sums the total booking time (minutes) for that day in the selected room - 0 if no booking
        DateRange.fld_date,
        ISNULL(SUM(DATEDIFF(MINUTE, tbl_booking.fld_startTime, tbl_booking.fld_endTime)), 0) AS total_booking_time

    FROM
        -- Joins DateRange with tbl_booking, filtering by room ID
        DateRange
            LEFT JOIN tbl_booking ON DateRange.fld_date = tbl_booking.fld_date AND tbl_booking.fld_roomID = @roomID

    GROUP BY
        DateRange.fld_date
    ORDER BY
        DateRange.fld_date;
END;
GO
