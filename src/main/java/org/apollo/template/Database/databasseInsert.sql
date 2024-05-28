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
INSERT INTO tbl_room (fld_roomName, fld_roomMaxPersonCount, fld_roomTypeID)
VALUES

    -- 3 sal på alsion.
    ('305', 30, 3),
    ('306', 18, 2),
    ('307', 18, 2),
    ('315', 20, 2),
    ('316', 35, 2),
    -- 4 sal på alsion
    ('400', 12, 4),
    ('401', 30, 2),
    ('402', 30, 2),
    ('403', 12, 2),
    ('404', 30, 2),
    ('405', 16, 2),
    ('406', 18, 2),
    ('407', 18, 2),
    ('409', 26, 2),
    ('410', 18, 2);

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
        tbl_inventory.fld_inventoryName,
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



