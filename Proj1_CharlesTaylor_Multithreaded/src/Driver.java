import Utils.Configuration;
import Waterplant.Pipe;
import Waterplant.Station;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Name: Charles A Taylor (C3229355)
    Course: CNT 4714 Summer 2015
    Assignment title: Project One – Multi-threaded programming in Java
    Date: May 31, 2015
    Class: CNT 4714
*/
public class Driver {
    //TODO: Find way so that the path is not absolute i'd prefer something like /raw/config.txt

    static final String CONFIG_FILE = "/home/charles/IdeaProjects/Proj1_CharlesTaylor_Multithreaded/src/raw/config1.txt";
    static Configuration config =  new Configuration(CONFIG_FILE);

    static ArrayList<Station> stations = new ArrayList<Station>();
    static ArrayList<Pipe> pipes = new ArrayList<Pipe>();

    static public void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(15);
        //init my config file
        int numberOfStations = config.getNumberOfStations();
        //load all the stations and pipes into an array list
        for (int i = 0; i < numberOfStations; i++) {
            Station station = new Station(i, config.getStationWorkloads()[i], numberOfStations);
            Pipe pipe = new Pipe(i);
            stations.add(station);
            pipes.add(pipe);
        }
        //load my pipes
        for (Station sta : stations) {
            sta.setPipes(pipes);
        }

        try {
            for (int i = 0; i < numberOfStations; i++) {
                service.execute(stations.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

}
