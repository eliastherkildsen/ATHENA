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
    -- Booking for today (current date)
    ('09:00:00', '10:00:00', CAST(GETDATE() AS DATE), 1, 10, 'Mads', 3, 1, 3, 5, 4),
    ('11:30:00', '13:00:00', CAST(GETDATE() AS DATE), 0, 8, 'Lars', 5, 2, 3, 5, 4),
    ('14:00:00', '16:00:00', CAST(GETDATE() AS DATE), 0, 6, 'Peter', 2, 2, 3, 5, 4),
    -- Booking for tomorrow
    ('08:30:00', '09:45:00', CAST(DATEADD(DAY, 1, GETDATE()) AS DATE), 1, 9, 'Kim', 4, 7, 2, 1, 1),
    ('10:30:00', '11:45:00', CAST(DATEADD(DAY, 1, GETDATE()) AS DATE), 1, 8, 'Mads', 3, 1, 3, 5, 4),
    ('13:00:00', '13:45:00', CAST(DATEADD(DAY, 1, GETDATE()) AS DATE), 0, 5, 'Lars', 5, 2, 3, 5, 4),
    -- Booking for the day after tomorrow
    ('09:15:00', '10:15:00', CAST(DATEADD(DAY, 2, GETDATE()) AS DATE), 1, 10, 'Peter', 2, 2, 3, 5, 4),
    ('11:30:00', '13:00:00', CAST(DATEADD(DAY, 2, GETDATE()) AS DATE), 0, 8, 'Kim', 4, 7, 2, 1, 1),
    ('14:00:00', '16:00:00', CAST(DATEADD(DAY, 2, GETDATE()) AS DATE), 0, 6, 'Mads', 3, 1, 3, 5, 4),
    -- Booking for 3 days from now
    ('08:45:00', '09:45:00', CAST(DATEADD(DAY, 3, GETDATE()) AS DATE), 1, 9, 'Lars', 5, 2, 3, 5, 4),
    ('10:30:00', '11:45:00', CAST(DATEADD(DAY, 3, GETDATE()) AS DATE), 1, 8, 'Peter', 2, 2, 3, 5, 4),
    ('13:00:00', '13:45:00', CAST(DATEADD(DAY, 3, GETDATE()) AS DATE), 0, 5, 'Kim', 4, 7, 2, 1, 1),
    -- Booking for 4 days from now
    ('09:00:00', '10:00:00', CAST(DATEADD(DAY, 4, GETDATE()) AS DATE), 1, 10, 'Mads', 3, 1, 3, 5, 4),
    ('11:30:00', '13:00:00', CAST(DATEADD(DAY, 4, GETDATE()) AS DATE), 0, 8, 'Lars', 5, 2, 3, 5, 4),
    ('14:00:00', '16:00:00', CAST(DATEADD(DAY, 4, GETDATE()) AS DATE), 0, 6, 'Peter', 2, 2, 3, 5, 4);