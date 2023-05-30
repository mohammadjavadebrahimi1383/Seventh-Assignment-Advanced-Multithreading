package sbu.cs.Semaphore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Operator extends Thread {
    public static Semaphore semaphore = new Semaphore(2);

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            try {
                semaphore.acquire();
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
                System.out.println(this.getName() + " " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}