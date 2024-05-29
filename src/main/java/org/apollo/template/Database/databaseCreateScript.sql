
-- Creating the database
CREATE DATABASE db_Athena;
GO

-- Selecting the database
USE db_Athena;

-- Creating the tabels in the database.
CREATE TABLE tbl_inventory(
    fld_inventoryID INTEGER IDENTITY (1,1) PRIMARY KEY,
    fld_inventoryName VARCHAR(50) NOT NULL,
    fld_inventoryDescription VARCHAR(128)
);

CREATE TABLE tbl_roomType(
     fld_roomTypeID INTEGER IDENTITY (1,1) PRIMARY KEY,
     fld_roomTypeName VARCHAR(50) NOT NULL,
     fld_roomTypeDescription VARCHAR(512)
);

CREATE TABLE tbl_room(
     fld_roomID INTEGER IDENTITY (1,1) PRIMARY KEY,
     fld_roomName VARCHAR(50) NOT NULL,
     fld_floor VARCHAR(10),
     fld_roomMaxPersonCount INTEGER,
     fld_roomTypeID INTEGER,

     FOREIGN KEY (fld_roomTypeID) REFERENCES tbl_roomType(fld_roomTypeID)
);

CREATE TABLE tbl_roomInventory(
    fld_roomInventoryID INTEGER IDENTITY (1,1) PRIMARY KEY,
    fld_roomID INTEGER,
    fld_inventoryID INTEGER,

    FOREIGN KEY (fld_roomID) REFERENCES tbl_room(fld_roomID),
    FOREIGN KEY (fld_inventoryID) REFERENCES tbl_inventory(fld_inventoryID)
);


CREATE TABLE tbl_userEmail(
     fld_userID INTEGER IDENTITY(1,1) PRIMARY KEY,
     fld_userEmail VARCHAR(128) NOT NULL
);

CREATE TABLE tbl_errorReport(
    fld_errorReportID INTEGER IDENTITY (1,1) PRIMARY KEY,
    fld_archived BIT NOT NULL,
    fld_reportDate DATE NOT NULL,
    fld_inventoryID INTEGER,
    fld_userID INTEGER,

    FOREIGN KEY (fld_inventoryID) REFERENCES tbl_inventory (fld_inventoryID),
    FOREIGN KEY (fld_userID) REFERENCES tbl_userEmail(fld_userID)
);


CREATE TABLE tbl_adminLogin(
    fld_userID INTEGER IDENTITY(1,1) PRIMARY KEY,
    fld_adminPassword varchar(128) NOT NULL
);

CREATE TABLE tbl_meetingType(
    fld_meetingTypeID INTEGER IDENTITY(1,1) PRIMARY KEY,
    fld_meetingType VARCHAR(50) NOT NULL
);

CREATE TABLE tbl_team(
    fld_teamID INTEGER IDENTITY(1,1) PRIMARY KEY,
    fld_teamName VARCHAR(50) NOT NULL
);

CREATE TABLE tbl_department(
    fld_departmentID INTEGER IDENTITY(1,1) PRIMARY KEY,
    fld_departmentName VARCHAR(50) NOT NULL
);

CREATE TABLE tbl_booking(
    fld_bookingID INTEGER IDENTITY(1,1) PRIMARY KEY,
    fld_startTime TIME(0) NOT NULL,
    fld_endTime TIME(0) NOT NULL,
    fld_date DATE NOT NULL,
    fld_catering BIT NOT NULL,
    fld_numberOfParticipants INTEGER NOT NULL,
    fld_userName VARCHAR(50) NOT NULL,
    fld_userID INTEGER,
    fld_roomID INTEGER,
    fld_meetingTypeID INTEGER,
    fld_departmentID INTEGER,
    fld_teamID INTEGER,

    FOREIGN KEY (fld_userID) REFERENCES tbl_userEmail(fld_userID),
    FOREIGN KEY (fld_roomID) REFERENCES tbl_room(fld_roomid),
    FOREIGN KEY (fld_meetingTypeID) REFERENCES tbl_meetingType(fld_meetingTypeID),
    FOREIGN KEY (fld_departmentID) REFERENCES tbl_department(fld_departmentID),
    FOREIGN KEY (fld_teamID) REFERENCES tbl_team(fld_teamID),
)

