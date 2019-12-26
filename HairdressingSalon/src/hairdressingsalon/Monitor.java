package hairdressingsalon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Oguzhan Tohumcu
 */
public class Monitor {
    
    private Condition emptySeat;
    private Condition newClient;
    private Condition invite;
    private Condition readyForCut;
    private Condition ended;
    
    private Client inChair;

    private static final int NUMBEROFSEATS = 3;
    private int numberOfClients = 0;
    
    private boolean endedHaircut = false;
    private boolean invited = false;
    
    Lock lock;

    public Monitor(){
        lock = new ReentrantLock();
        emptySeat = lock.newCondition();
        newClient = lock.newCondition();
        invite = lock.newCondition();
        readyForCut = lock.newCondition();
        ended = lock.newCondition();
    }

    public void haveAHaircut() throws InterruptedException {
        lock.lock();

        try {
            while (noEmptySeats()) {
                emptySeat.await();
            }
            System.out.println(Thread.currentThread().getName() + " gets a seat in waiting room");

            numberOfClients++;
            newClient.signal();

            while(!invited) {
                invite.await();
            }
            invited = false;
            System.out.println(Thread.currentThread().getName() + " was invited by hairdresser.");

            numberOfClients--;
            emptySeat.signal();
            System.out.println(Thread.currentThread().getName() + " leaves waiting room!");

            /* go sit in Barbers chair */
            inChair = (Client) Thread.currentThread();
            readyForCut.signal();
            
            System.out.println(Thread.currentThread().getName() + " is seated in hairdresser chair.");

            while (!endedHaircut) {
                ended.await();
            }
            endedHaircut = false;

            System.out.println(Thread.currentThread().getName() + " leaves the hairdressing salon!");
        } finally {
            lock.unlock();
        }

    }

    public Client nextClient() throws InterruptedException{
        lock.lock();
        try {
            while (noClients()) {
                System.out.println("Barber goes to sleep");
                newClient.await();
            }
            
            invited = true;
            invite.signal();

            while (inChair == null) {
                readyForCut.await();
            }
            return inChair;
        } finally {
            lock.unlock();
        }
    }

    public void showOut(Client client){
        lock.lock();
        try {
            inChair = null;
            endedHaircut = true;
            ended.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean noClients(){
        return (numberOfClients == 0);
    }

    private boolean noEmptySeats(){
        return (numberOfClients == NUMBEROFSEATS);
    }
}
