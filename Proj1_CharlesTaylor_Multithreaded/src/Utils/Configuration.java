package Utils;

import java.io.*;
import java.util.Scanner;

/*
    Name: Charles A Taylor (C3229355)
    Course: CNT 4714 Summer 2015
    Assignment title: Project One – Multi-threaded programming in Java
    Date: May 31, 2015
    Class: CNT 4714
*/
public class Configuration {
    public String mFileName = "";
    public int numberOfStations = 0;
    public int[] stationWorkloads;

    public Configuration(String fileName) {
        mFileName = fileName;
        readFile();
    }

    public void readFile() {
        try {
            Scanner s = new Scanner(new File(mFileName));
            numberOfStations = s.nextInt();
            stationWorkloads = new int[numberOfStations];
            for(int i=0;s.hasNextInt();i++){
                stationWorkloads[i] = s.nextInt();
            }
        }catch(FileNotFoundException fe) {
            System.err.println("File not found!");

        }
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    public int[] getStationWorkloads() {
        return stationWorkloads;
    }

    public void setStationWorkloads(int[] stationWorkloads) {
        this.stationWorkloads = stationWorkloads;
    }
}
