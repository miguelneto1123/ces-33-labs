import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmitterGeneratorTest {
    @Test
    public void test1a(){
        System.out.println("===============\nTest 1a");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 6, 6);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test1b(){
        System.out.println("\n===============\nTest 1b");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 2, 6);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test2a(){
        System.out.println("\n===============\nTest 2a");
        int numProc = 8;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 6, 6);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test2b(){
        System.out.println("\n===============\nTest 2b");
        int numProc = 8;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 2, 6);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test3a(){
        System.out.println("\n===============\nTest 3a");
        int numProc = 2;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 3, 10);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test3b(){
        System.out.println("\n===============\nTest 3b");
        int numProc = 2;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 1, 10);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test4a(){
        System.out.println("\n===============\nTest 4a");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 6, 8);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test4b(){
        System.out.println("\n===============\nTest 4b");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 2, 8);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test5a(){
        System.out.println("\n===============\nTest 5a");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 9, 8);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }

    @Test
    public void test5b(){
        System.out.println("\n===============\nTest 5b");
        int numProc = 4;
        EmitterManager m = new EmitterManager(numProc);
        Generator g = new Generator(m, 3, 8);

        boolean cpuOverload = g.run();

        System.out.println("Amount of messages sent by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.sentMessages[i]);
        }

        System.out.println("\nAmount of messages received by processor:");
        for (int i = 0; i < numProc; i++){
            System.out.println(i + ": " + m.receivedMessages[i]);
        }

        System.out.println("\nTotal CPU changes: " + m.cpuChanges());
		assertTrue(cpuOverload);
    }
}
