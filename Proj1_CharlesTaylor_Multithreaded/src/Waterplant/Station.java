package Waterplant;

import java.util.ArrayList;

/**
 * Created by charles on 5/28/15.
 */
public class Station implements Runnable {
    private int mStationNumber;
    private int mWorkLoad;
    //assign the pipes to this station then consistently check if they are available
    private Pipe mPipeConnecterRight; // N
    private Pipe mPipeConnecterLeft; // (N-1)%(#OfPipes)

    private int mRightPipeIdx;
    private int mLeftPipeIdx;
    //meant for configuration file
    private boolean mConfigured = false;

    public Station(int stationNumber, int workload,int numOfStations) {
        mStationNumber=stationNumber;
        mWorkLoad = workload;

        //TODO: find alternative readable solution for actual modulus not remainder
        mLeftPipeIdx = (((stationNumber - 1) % numOfStations) + numOfStations) % numOfStations;
        mRightPipeIdx = stationNumber;

    }

    public int getmWorkLoad() {
        return mWorkLoad;
    }

    public void setmWorkLoad(int mWorkLoad) {
        this.mWorkLoad = mWorkLoad;
    }

    public int getmStationNumber() {
        return mStationNumber;
    }

    public void setmStationNumber(int mStationNumber) {
        this.mStationNumber = mStationNumber;
    }

    @Override
    public void run() {
        while(mWorkLoad > 0) {
            if(mConfigured) {
                doWork();
            }else {
                System.out.printf("Station %d: Workload set to %d\n",getmStationNumber(),mWorkLoad);
                System.out.printf("Station %d: In-Connection set to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
                System.out.printf("Station %d: Out-Connection set to pipe %d\n",mStationNumber,mPipeConnecterLeft.getmPipeNumber());
                mConfigured = true;
            }
        }
        System.out.printf("* * Station %d: Workload successfully completed. * *\n",getmStationNumber());
    }

    public void doWork() {
        if(checkLocks()) {
            try {
                System.out.printf("Station %d: Successfully flows on pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
                System.out.printf("Station %d: Successfully flows on pipe %d\n",mStationNumber,mPipeConnecterLeft.getmPipeNumber());

                mWorkLoad = mWorkLoad - 1;
                System.out.printf("Station %d: Workload set to %d\n",mStationNumber,mWorkLoad);
            }finally {
                mPipeConnecterRight.accessLock.unlock();
                mPipeConnecterLeft.accessLock.unlock();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Station %d: released access to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
                System.out.printf("Station %d: released access to pipe %d\n",mStationNumber,mPipeConnecterLeft.getmPipeNumber());
            }
        }else {
//            System.out.println("Couldn't get All Locks");
        }
    }
    //
    public void checkWorkload() {
        System.out.printf("Station %d: Workload set to %d\n",mStationNumber,mWorkLoad);
    }

    //ensure we can use the the stations by checking the pipe locks
    public boolean checkLocks() {
        boolean lRightPipeAvail = false;
        boolean lLeftPipeAvail = false;
        try {
            //try lock not only returns if avail but also locks the sharedObject
            lRightPipeAvail = mPipeConnecterRight.accessLock.tryLock();
            if(lRightPipeAvail) {
//                mPipeConnecterRight.accessLock.lock(); // not necessary due to tryLock
                System.out.printf("Station %d: granted access to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
            }
            lLeftPipeAvail = mPipeConnecterLeft.accessLock.tryLock();
            if(lLeftPipeAvail) {
//                mPipeConnecterLeft.accessLock.lock();
                System.out.printf("Station %d: granted access to pipe %d\n",mStationNumber,mPipeConnecterLeft.getmPipeNumber());
            }
        }finally {
            if(!(lLeftPipeAvail && lRightPipeAvail)) {
                if(lRightPipeAvail) {
                    mPipeConnecterRight.accessLock.unlock();
                    System.out.printf("Station %d: released access to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
                }
                if(lLeftPipeAvail) {
                    mPipeConnecterLeft.accessLock.unlock();
                    System.out.printf("Station %d: released access to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
                }
            }
        }
        return lLeftPipeAvail && lRightPipeAvail;
    }
    //init all the pipes for this station
    public void setPipes(ArrayList<Pipe> pipes) {
        mPipeConnecterRight = pipes.get(mRightPipeIdx);
//        System.out.printf("Station %d: In-Connection set to pipe %d\n",mStationNumber,mPipeConnecterRight.getmPipeNumber());
        mPipeConnecterLeft = pipes.get(mLeftPipeIdx);
//        System.out.printf("Station %d: Out-Connection set to pipe %d\n",mStationNumber,mPipeConnecterLeft.getmPipeNumber());

    }
}
