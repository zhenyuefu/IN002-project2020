package io;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    public static List<String[]> read(String filename) throws IOException {
        File memberFile = new File(filename);
        if (!memberFile.exists()) {
            if(!memberFile.createNewFile())
                System.out.println("Can't create file"+filename);
        }

        List<String[]> data=null;

        if (memberFile.exists() && memberFile.isFile() && memberFile.canRead()) {
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                data = reader.readAll();
            } catch (CsvException e) {
                e.printStackTrace();
            }

        }
        return new ArrayList<>(data);
    }

    public static void write(String filename, List<String[]> csvData) throws IOException {
        File memberFile = new File(filename);
        if (!memberFile.exists()) {
            if(!memberFile.createNewFile())
                System.out.println("Can't create file"+filename);
        }

        if (memberFile.exists() && memberFile.isFile() && memberFile.canWrite()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
                writer.writeAll(csvData);
            }
        }
    }
}
