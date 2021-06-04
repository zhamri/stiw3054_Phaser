import java.util.concurrent.Phaser;

class PhaserTracer {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        new Task1(phaser);
        new Task1(phaser);
        new Task1(phaser);
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
//        phaser.arriveAndDeregister();  // phase problem if deregister main here
        System.out.println("New phase-" + phaser.getPhase() + " started");
        new Task2(phaser);
        new Task2(phaser);
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase-" + phaser.getPhase() + " completed");
        phaser.arriveAndDeregister();
        System.out.println(Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }
}

class Task1 implements Runnable {
    private Phaser phaser;

    Task1(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Task1-begin -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        try {
            Thread.sleep(200);
            phaser.arriveAndAwaitAdvance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished");
        phaser.arriveAndDeregister();
        System.out.println("Task1-end -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }
}

class Task2 implements Runnable {
    private Phaser phaser;

    Task2(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Task2-begin -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " finished");
        phaser.arriveAndDeregister();
        System.out.println("Task2-end -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }
}

/*
main -> Phase-0 -> Registered_Parties:1
main -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-1 -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-2 -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-0 -> Phase-0 -> Registered_Parties:3
Thread-2 finished
Thread-1 finished
Thread-0 finished
Task1-end -> Thread-2 -> Phase-1 -> Registered_Parties:3
Task1-end -> Thread-1 -> Phase-1 -> Registered_Parties:2
Task1-end -> Thread-0 -> Phase-1 -> Registered_Parties:1
New phase-1 started
Task2-begin -> Thread-3 -> Phase-1 -> Registered_Parties:3
Task2-begin -> Thread-4 -> Phase-1 -> Registered_Parties:3
Thread-4 finished
Thread-3 finished
Phase-2 completed
Task2-end -> Thread-4 -> Phase-2 -> Registered_Parties:1
main -> Phase--2147483645 -> Registered_Parties:0
Task2-end -> Thread-3 -> Phase-2 -> Registered_Parties:2
 */