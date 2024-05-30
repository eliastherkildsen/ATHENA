package org.apollo.template.Service;

import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingID
 * DATE
 * ROOM
 * EMAIL
 * NumberOfParticipants
 */
public class GenerateCSV {
    String csvFilePath = "data.csv";
    List<BookingInformation> bookingList = new ArrayList<>();

    /**
     * Generates a CSV file with predetermined values from List with BookingInformation objects
     * @param bookingInformationList object
     */
    public GenerateCSV(List<BookingInformation> bookingInformationList){
        bookingList.addAll(bookingInformationList);
        generateCSVFile();
    }

    public void generateCSVFile() {
        BufferedWriter bufferedWriter = null;
        try {
            LoggerMessage.debug(this, "Attempting to Generate CSV File");

            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            this.csvFilePath = "booking_information_" + dateTime.format(formatter) + ".csv";


            FileWriter writer = new FileWriter(csvFilePath);
            bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.append("Date,RoomName,NumberOfParticipants,UserName,startTime,endTime");
            bufferedWriter.newLine();

            for (BookingInformation bi : bookingList) {
                LoggerMessage.debug(this, "Writing INFO :" + bi.getBookingId());
                bufferedWriter.append(bi.getDate().toString()); // Assuming getDate returns a date in the correct format
                bufferedWriter.append(",");
                bufferedWriter.append(bi.getRoom().getRoomName());
                bufferedWriter.append(",");
                bufferedWriter.append(String.valueOf(bi.getNumberOfParticipants()));
                bufferedWriter.append(",");
                bufferedWriter.append(bi.getUserName());
                bufferedWriter.append(",");
                bufferedWriter.append(bi.getStartTime().toString()); // Assuming getStartTime returns a time in the correct format
                bufferedWriter.append(",");
                bufferedWriter.append(bi.getEndTime().toString()); // Assuming getEndTime returns a time in the correct format
                bufferedWriter.newLine();
            }
            LoggerMessage.debug(this, "Successfully Written CSV File");
        } catch (IOException e) {
            LoggerMessage.error(this, "File Writer Failed: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close(); //Remembering to close the writer
                } catch (IOException e) {
                    LoggerMessage.error(this, "Failed to close BufferedWriter: " + e.getMessage());
                    e.printStackTrace(); // Log the stack trace for better debugging
                }
            }
        }

    }
}
