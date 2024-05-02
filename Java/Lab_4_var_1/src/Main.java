import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        Semaphore waiter = new Semaphore(4);
        for (int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5], waiter);
            new Thread(philosophers[i]).start();
        }
    }
}

class Philosopher implements Runnable{
    private final int id;
    private final Semaphore leftFork;
    private final Semaphore rightFork;
    private final Semaphore waiter;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork, Semaphore waiter){
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 10; i++) {
                System.out.println("Philosopher " + id + " thinking time " + i);
                waiter.acquire();
                leftFork.acquire();
                System.out.println("Philosopher " + id + " took left fork ");
                rightFork.acquire();
                System.out.println("Philosopher " + id + " took right fork ");
                System.out.println("Philosopher " + id + " eating time " + i);
                rightFork.release();
                System.out.println("Philosopher " + id + " put right fork ");
                leftFork.release();
                System.out.println("Philosopher " + id + " put left fork ");
                waiter.release();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}