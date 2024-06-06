USE db_Athena;

-- tbl_inventory
INSERT INTO tbl_inventory (fld_inventoryName, fld_inventoryDescription)
VALUES
    ('Andet', 'Et ikke difineret objekt'),
    ('Tavle', 'Stor tavle til at skrive på med kridt eller whiteboard markers'),
    ('Projektor', 'Enhed til at projicere computerens skærm på væggen eller et lærred'),
    ('Stol', 'Standard stol'),
    ('Bord', 'Standard bord'),
    ('Mikrofon', 'Mikrofon til brug ved præsentationer eller forelæsninger'),
    ('Højttaler', 'Højttaler til lydudstyr i ilokalet');

-- tbl_roomType
INSERT INTO tbl_roomType (fld_roomTypeName, fld_roomTypeDescription)
VALUES
    ('Andet', 'Et ikke difineret lokale'),
    ('Møde lokale', 'Et lokale til at holde møder'),
    ('Klasseværelse', 'Et lokale til at undervise studerende'),
    ('Hybrid lokale', 'Et lokale til at undervise studerende, med mulighed for online undervisning'),
    ('Værksted', 'Et lokale med maskiner til at arbejde med rå matrialer');

-- tbl_room
INSERT INTO tbl_room (fld_roomName, fld_roomMaxPersonCount, fld_roomTypeID, fld_floor)
VALUES

    -- 3 sal på alsion.
    ('305', 30, 3, 3),
    ('306', 18, 2, 3),
    ('307', 18, 2, 3),
    ('315', 20, 2, 3),
    ('316', 35, 2, 3),
    -- 4 sal på alsion
    ('400', 12, 4, 4),
    ('401', 30, 2, 4),
    ('402', 30, 2, 4),
    ('403', 12, 2, 4),
    ('404', 30, 2, 4),
    ('405', 16, 2, 4),
    ('406', 18, 2, 4),
    ('407', 18, 2, 4),
    ('409', 26, 2, 4),
    ('410', 18, 2, 4);

-- tbl_roomInventory
INSERT INTO tbl_roomInventory (fld_roomID, fld_inventoryID)
VALUES
    -- Room 305
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),

    -- room 306
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),

    -- room 307
    (3, 1),
    (3, 2),
    (3, 3),
    (3, 4);


-- tbl_userEmail
INSERT INTO tbl_userEmail (fld_userEmail)
VALUES
    ('test123@easv365.dk'),
    ('peterRasmusen@easv365.dk'),
    ('MadsPetersen@easv365.dk'),
    ('KimSøndergård@easv365.dk'),
    ('LarsHansen@easv365.dk');

-- tbl_errorReport

-- tbl_adminLogin

-- tbl_meetingType
INSERT INTO tbl_meetingType (fld_meetingType)
VALUES
    ('Anonymt'),
    ('Andet'),
    ('Møde'),
    ('Eksamen'),
    ('Undervisning');


-- tbl_team
INSERT INTO tbl_team (fld_teamName)
VALUES
    ('Andet'),
    ('Anonymt'),
    ('d22'),
    ('d23'),
    ('e22'),
    ('e23'),
    ('pt22'),
    ('pt23'),
    ('md22'),
    ('md23');
-- tbl_department
INSERT INTO tbl_department (fld_departmentName)
VALUES
    ('Andet'),
    ('Anonymt'),
    ('Rengøring'),
    ('Ledelse'),
    ('Underviser');

-- tbl_booking
INSERT INTO tbl_booking (fld_startTime, fld_endTime, fld_date, fld_catering, fld_numberOfParticipants, fld_userName, fld_userID, fld_roomID, fld_meetingTypeID, fld_departmentID, fld_teamID)

VALUES
    ('08:00:00', '09:00:00', '2024-05-28', 1, 15, 'Mads', 3, 1, 3, 5, 4),
    ('11:00:00', '12:30:00', '2024-05-28', 0, 17, 'Mads', 3, 1, 3, 5, 4),
    ('09:15:00', '16:00:00', '2024-05-28', 0, 7, 'Lars', 5, 2, 3, 5, 4),
    ('08:00:00', '09:15:00', '2024-05-28', 1, 11, 'Peter', 2, 2, 3, 5, 4),
    ('13:00:00', '13:45:00', '2024-05-28', 0, 4, 'Kim', 4, 7, 2, 1, 1);

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

-- Function for InfoScreen
use db_Athena;
go

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
go



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

-- Stored procedure to finde email id by email adress
    USE db_Athena
go

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

CREATE PROCEDURE FindbookingByEmail (@EmailAddress NVARCHAR(255))
AS
BEGIN

    SELECT tbl_booking.fld_bookingID, tbl_userEmail.fld_userEmail, tbl_room.fld_roomName, fld_startTime, fld_endTime, fld_userName,
           tbl_meetingType.fld_meetingType

    FROM tbl_booking
             INNER JOIN tbl_userEmail ON tbl_booking.fld_userID = tbl_userEmail.fld_userID
             INNER JOIN tbl_room ON tbl_booking.fld_roomID = tbl_room.fld_roomID
             INNER JOIN tbl_meetingType on tbl_booking.fld_bookingID = tbl_meetingType.fld_meetingTypeID

    WHERE
        tbl_userEmail.fld_userEmail = @EmailAddress;
END;





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


    CREATE PROCEDURE getAllRoomTypeNames
    AS
    BEGIN
        SELECT
            fld_roomTypeName
        FROM
            tbl_roomType
    END

        use db_Athena
go

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
END

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
    END

CREATE PROCEDURE getNumberOfBookingsFromRoomID (@roomID INTEGER)
AS
BEGIN
    SELECT COUNT(fld_bookingID)
    FROM tbl_booking
    WHERE fld_roomID = @roomID
END


CREATE PROCEDURE GetRoomIDFromName (@roomName varChar(50))
AS
BEGIN
    SELECT fld_roomID
    FROM tbl_room
    WHERE fld_roomName = @roomName
END

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


