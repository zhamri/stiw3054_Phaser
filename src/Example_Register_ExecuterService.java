import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

class Task implements Runnable {
    private String threadName;
    private Phaser phaser;

    Task(String threadName, Phaser phaser) {
        this.threadName = threadName;
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        System.out.println(threadName +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        /*
        If threads sleep for few seconds, the teh number of parties will be different
        because the phaser has not been Deregistered yet.
         */
//        try {
//            Thread.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        phaser.arriveAndDeregister();
    }
}

class PhaserRegister {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser phaser = new Phaser(1);
        executorService.submit(new Task("T1", phaser));
        executorService.submit(new Task("T2", phaser));
        executorService.submit(new Task("T3", phaser));

        phaser.arriveAndAwaitAdvance();

        executorService.submit(new Task("T4", phaser));
        executorService.submit(new Task("T5", phaser));

        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
        executorService.shutdown();
    }
}


/*
T1 -> Phase-0 -> Registered_Parties:4
T2 -> Phase-0 -> Registered_Parties:4
T3 -> Phase-0 -> Registered_Parties:4
T4 -> Phase-1 -> Registered_Parties:3
T5 -> Phase-1 -> Registered_Parties:3
 */