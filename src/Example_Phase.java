import java.util.concurrent.Phaser;

class PhaserDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        phaser.register();
        System.out.println("Phase: " + phaser.getPhase());
        new PhaserDemo().runMethod(phaser);
        new PhaserDemo().runMethod(phaser);
        new PhaserDemo().runMethod(phaser);
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase: " + phaser.getPhase());
    }

    private void runMethod(Phaser phaser) {
        phaser.register();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " arrived");
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + " after passing barrier");
        }).start();
    }
}

/*
Phase: 0
Thread-1 arrived
Thread-0 arrived
Thread-2 arrived
Thread-2 after passing barrier
Thread-1 after passing barrier
Phase: 1
Thread-0 after passing barrier
 */