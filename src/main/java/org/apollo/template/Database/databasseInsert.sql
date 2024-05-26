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

