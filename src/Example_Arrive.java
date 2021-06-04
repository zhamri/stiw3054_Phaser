import java.util.concurrent.Phaser;

class Phaser_Arrive {
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(10);
        phaser.register();
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
        System.out.println("Thread is sleeping");
        Thread.sleep(1000);
        for (int i = 0; i <= 5; i++) {
            new Phaser_Arrive().runTask(phaser);
            phaser.arrive();
        }
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + phaser.getPhase() +
                " -> Registered_Parties:" + phaser.getRegisteredParties());
    }

    void runTask(Phaser phaser) {
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +
                        " -> " + "Phase-" + phaser.getPhase() +
                        " -> Registered_Parties:" + phaser.getRegisteredParties());
            }
        }.start();
    }
}


/*
main -> Phase-0 -> Registered_Parties:11
Thread is sleeping
Thread-2 -> Phase-0 -> Registered_Parties:11
Thread-4 -> Phase-0 -> Registered_Parties:11
Thread-3 -> Phase-0 -> Registered_Parties:11
Thread-1 -> Phase-0 -> Registered_Parties:11
Thread-5 -> Phase-0 -> Registered_Parties:11
Thread-0 -> Phase-0 -> Registered_Parties:11
main -> Phase-0 -> Registered_Parties:11
 */