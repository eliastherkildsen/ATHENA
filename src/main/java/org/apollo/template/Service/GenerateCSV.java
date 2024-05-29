package org.apollo.template.Service;

import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    GenerateCSV(List<BookingInformation> bookingInformationList){
        bookingList.addAll(bookingInformationList);
    }

    public void

    public void generateCSV(){
        try {
            FileWriter writer = new FileWriter(csvFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.append("");


        } catch (IOException e){
            LoggerMessage.error(this, "File Writer Failed : " + e.getMessage());
        }

    }
}
