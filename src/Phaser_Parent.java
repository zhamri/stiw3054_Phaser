import java.util.concurrent.Phaser;

public class Phaser_Parent {

    public static void main(String[] args) {

        Phaser parentPhaser = new Phaser(1);
        Phaser childPhaser = new Phaser(parentPhaser, 0);
        childPhaser.register();
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + parentPhaser.getPhase() +
                " -> Parent_Registered_Parties:" + parentPhaser.getRegisteredParties());
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + childPhaser.getPhase() +
                " -> Child_Registered_Parties:" + childPhaser.getRegisteredParties());

        System.out.println("parentPhaser isTerminated : " + parentPhaser.isTerminated());
        System.out.println("childPhaser isTerminated : " + childPhaser.isTerminated());
        parentPhaser.arriveAndDeregister();
        childPhaser.arriveAndDeregister();
        System.out.println("parentPhaser isTerminated : " + parentPhaser.isTerminated());
        System.out.println("childPhaser isTerminated : " + childPhaser.isTerminated());

        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + parentPhaser.getPhase() +
                " -> Parent_Registered_Parties:" + parentPhaser.getRegisteredParties());
        System.out.println(Thread.currentThread().getName() +
                " -> " + "Phase-" + childPhaser.getPhase() +
                " -> Child_Registered_Parties:" + childPhaser.getRegisteredParties());
    }
}


/*
main -> Phase-0 -> Parent_Registered_Parties:2
main -> Phase-0 -> Child_Registered_Parties:1
parentPhaser isTerminated : false
childPhaser isTerminated : false
parentPhaser isTerminated : true
childPhaser isTerminated : true
main -> Phase--2147483647 -> Parent_Registered_Parties:0
main -> Phase--2147483647 -> Child_Registered_Parties:0
 */

