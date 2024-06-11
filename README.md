# Athena booking system.
The purpose of the system is to develop an information and booking system.
The system should enable employees and students to quickly get an overview 
of the institution's rooms and their availability. 
This should be accessible on the institution's information screen 
or on the admin computer. It should be possible for employees to 
book rooms for teaching, meetings, etc., and to order catering if necessary.
As a student, it should be possible to book available 
rooms on an ad-hoc basis. Furthermore, the system should be able
to handle data extraction and display booking statistics.

![applicationGaiaAdHoc.gif](src%2Fmain%2Fresources%2Forg%2Fapollo%2Ftemplate%2Fimages%2FapplicationGaiaAdHoc.gif)

## Installation guide
This is a guide for installing the system. Due to the system being developed in java, the JVM should allow 
you to run the program on any supported operating system.

### Database
To install the system you will need to follow these steps:
1. Install MSSQL database. 
2. Create a user in MSSQL database, and replace the username, password, port and ipadress in 
`src/main/java/org/apollo/template/Database/db.properties`. Run the test, `src/test/java/org/apollo/template/Database/JDBCTest.java`
to insure that a connection has been established. 
3. Run the databaseCreateScript from `src/main/java/org/apollo/template/Database/databaseCreateScript.sql`
4. Run the databaseInsertScript from `src/main/java/org/apollo/template/Database/databaseInsertScript.sql`

To ensure that the system is installed correctly run all tests from 
`src/test/java/org/apollo/template`

You have now successfully set up the database. 

### Setting up the system
1. To run the system simply pull it from github, navigate to the branch `main` and run the program.

## Dependency's 
1. MSSQL (https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
  MSSQL is used as the database for the system. 
2. JAVA SDK v. 21  or newer (https://jdk.java.net/21/)
3. JDBC (https://mvnrepository.com/artifact/mysql/mysql-connector-java) 
is used for interacting with the database from within the application.

### User Guide

The Athena System consists of two parts: ad-hoc booking and the admin panel. The purpose of the 
admin panel is to create an interface for managing bookings, rooms, and more.
The ad-hoc part of the system is an information screen where, today's meetings 
are presented and meetings can be booked for the same day.

#### Ad-hoc
To create a meeting, press the "BOOK" button on the information screen, 
then select a room and a time frame. Next, the user will be prompted to 
provide some information relevant to the system. After this, the booking can be confirmed.

If you wish to delete your booking, select the "DELETE BOOKING" button, 
then the user must enter the email under which the booking was made and select 
the meeting to be deleted.

#### Admin
The admin panel has various functionalities. These can be divided into two sections.

##### System Administration
The system administration section ensures that relevant classes, 
rooms, and information are always accurate. Therefore, the system includes
several different tabs that an admin can navigate to in order to ensure this.

##### Admin Booking
Admin booking is an extension of ad-hoc booking, which allows for booking a room 
for multiple days at a time, booking rooms in the future, and deleting bookings.

##### UML
![mainUMLAthena.png](src/main/resources/org/apollo/template/images/mainUMLAthena.png)
