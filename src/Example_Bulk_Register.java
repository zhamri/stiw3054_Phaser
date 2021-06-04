import java.util.concurrent.Phaser;

class PhaserBulkRegister {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        // registering 3 parties in bulk
        phaser.bulkRegister(3);
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        // starting 3 threads
        for (int i = 1; i <= 3; i++) {
            new Thread(new Task_1(phaser)).start();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        // starting 2 threads
        for (int i = 1; i <= 2; i++) {
            new Thread(new Task_2(phaser)).start();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        // deregistering the main thread
        phaser.arriveAndDeregister();
    }
}

class Task_1 implements Runnable {
    private Phaser phaser;

    Task_1(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.println("Task1-begin -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
        System.out.println("Task1-completed -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }
}

class Task_2 implements Runnable {
    private Phaser phaser;

    Task_2(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        System.out.println("Task2-begin -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
        System.out.println("Task2-completed -> " + Thread.currentThread().getName() +
                " -> Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }
}


/*
main -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-0 -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-2 -> Phase-0 -> Registered_Parties:4
Task1-begin -> Thread-1 -> Phase-0 -> Registered_Parties:4
Task1-completed -> Thread-1 -> Phase-1 -> Registered_Parties:3
Task1-completed -> Thread-2 -> Phase-1 -> Registered_Parties:1
Task1-completed -> Thread-0 -> Phase-1 -> Registered_Parties:2
main -> Phase-1 -> Registered_Parties:3
Task2-begin -> Thread-3 -> Phase-1 -> Registered_Parties:3
Task2-begin -> Thread-4 -> Phase-1 -> Registered_Parties:3
Task2-completed -> Thread-3 -> Phase-2 -> Registered_Parties:2
Task2-completed -> Thread-4 -> Phase-2 -> Registered_Parties:1
main -> Phase-2 -> Registered_Parties:1
 */