package com.demo.springbatch.job;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class WriterJob {

    private int  failCount = 0;
    File file = new File("src/main/resources/outputData.csv");

   public  void failCount(int count) {
      failCount ++;
   }

   public void outPutWriter(Long addCount ,Long  updateCount , int deleteCount){

       try {
           System.out.println("Total fail count : "+failCount);

       // create FileWriter object with file as parameter
       FileWriter outputfile = new FileWriter(file);

       // create CSVWriter object filewriter object as parameter
       CSVWriter writer = new CSVWriter(outputfile);

       // adding header to csv
       String[] header = {"Batch", "Action", "Count"};
       writer.writeNext(header);

       // add data to csv
       String[] data1 = {"-----", "ADD  ", String.valueOf(addCount)};
       writer.writeNext(data1);
       String[] data2 = {"-----", "UPDATE", String.valueOf(updateCount)};
       writer.writeNext(data2);
       String[] data3 = {"-----", "DELETE", String.valueOf(deleteCount)};
       writer.writeNext(data3);
       String[] data4 = {"-----", "FAILED", String.valueOf(failCount)};
       writer.writeNext(data4);

       // closing writer connection
       writer.close();
       failCount = 0;
   } catch (
    IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

   }



}
