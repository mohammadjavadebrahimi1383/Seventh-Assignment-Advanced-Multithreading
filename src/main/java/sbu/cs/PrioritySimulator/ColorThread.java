package sbu.cs.PrioritySimulator;

public abstract class ColorThread extends Thread {

    synchronized void printMessage(Message message) {
        System.out.printf("[x] %s. thread_name: %s%n", message.toString(), currentThread().getName());
        Runner.addToList(message);
        Runner.counter.countDown();
    }

    abstract String getMessage();
}