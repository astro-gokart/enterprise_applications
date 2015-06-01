package Waterplant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
    Name: Charles A Taylor (C3229355)
    Course: CNT 4714 Summer 2015
    Assignment title: Project One – Multi-threaded programming in Java
    Date: May 31, 2015
    Class: CNT 4714
*/
public class Pipe {
    private String mName;
    private int mPipeNumber;
    Lock accessLock = new ReentrantLock();

    public Pipe(int pipeNumber) {
        mPipeNumber = pipeNumber;
    }

    public Pipe(int pipeNumber, String name) {
        mName = name;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmPipeNumber() {
        return mPipeNumber;
    }

    public void setmPipeNumber(int mPipeNumber) {
        this.mPipeNumber = mPipeNumber;
    }
}
