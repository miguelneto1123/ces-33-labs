import org.junit.Test;

import java.util.Random;

public class ManagerTest {
    private int nproc = 3;
    HybridManager m = new HybridManager(nproc);

    @Test
    public void test(){
        int NUMITER = 100;
        Random rand = new Random();

        for (int i = 0; i < NUMITER; i++) {
            System.out.println("Iteration " + i);
            //m.printStatus();
            m.printSimpleStatus();

            if (i%2 == 0)
                m.addProcess(new Process(rand.nextInt(m.quantProcessors()),
                        i,
                        rand.nextInt(10) + 1,
                        (rand.nextInt(100)/100.0f)/nproc));

            m.update();
        }

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < 3; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < 3; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }
    }
}
