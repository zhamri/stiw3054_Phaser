import java.time.LocalTime;
import java.util.concurrent.Phaser;

class Phaser_Register {
    private static final Phaser phaser = new Phaser();

    public static void main(String[] args) throws InterruptedException {
        startTask(0);
        startTask(500);
        startTask(1000);
    }

    private static void startTask(long initialDelay) throws InterruptedException {
        Thread.sleep(initialDelay);
        new Thread(Phaser_Register::runTask).start();
    }

    private static void runTask() {
        phaser.register();
        print("after registering");
        for (int i = 1; i <= 2; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print("before arrive " + i);
            phaser.arriveAndAwaitAdvance();
            print("after arrive " + i);
        }
    }

    private static void print(String msg) {
        System.out.printf("%-20s: %10s, t=%s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
                msg,
                Thread.currentThread().getName(),
                LocalTime.now(),
                phaser.getRegisteredParties(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties(),
                phaser.getPhase()
        );
    }
}


/*
after registering   :   Thread-0, t=20:56:17.895494, registered=1, arrived=0, unarrived=1 phase=0
after registering   :   Thread-1, t=20:56:18.367181, registered=2, arrived=0, unarrived=2 phase=0
after registering   :   Thread-2, t=20:56:19.371949, registered=3, arrived=0, unarrived=3 phase=0
before arrive 1     :   Thread-0, t=20:56:20.935313, registered=3, arrived=0, unarrived=3 phase=0
before arrive 1     :   Thread-1, t=20:56:21.371522, registered=3, arrived=1, unarrived=2 phase=0
before arrive 1     :   Thread-2, t=20:56:22.373169, registered=3, arrived=2, unarrived=1 phase=0
after arrive 1      :   Thread-2, t=20:56:22.375107, registered=3, arrived=0, unarrived=3 phase=1
after arrive 1      :   Thread-0, t=20:56:22.375253, registered=3, arrived=0, unarrived=3 phase=1
after arrive 1      :   Thread-1, t=20:56:22.375196, registered=3, arrived=0, unarrived=3 phase=1
before arrive 2     :   Thread-1, t=20:56:25.379410, registered=3, arrived=0, unarrived=3 phase=1
before arrive 2     :   Thread-2, t=20:56:25.379411, registered=3, arrived=0, unarrived=3 phase=1
before arrive 2     :   Thread-0, t=20:56:25.379411, registered=3, arrived=0, unarrived=3 phase=1
after arrive 2      :   Thread-0, t=20:56:25.381727, registered=3, arrived=0, unarrived=3 phase=2
after arrive 2      :   Thread-2, t=20:56:25.381744, registered=3, arrived=0, unarrived=3 phase=2
after arrive 2      :   Thread-1, t=20:56:25.381752, registered=3, arrived=0, unarrived=3 phase=2
 */