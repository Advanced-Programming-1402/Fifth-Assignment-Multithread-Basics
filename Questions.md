1: What will be printed after interrupting the thread?
public static class SleepThread extends Thread {
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
            } finally {
                System.out.println("Thread will be finished here!!!");
            }
        }
    }

    public static void main(String[] args) {
        SleepThread thread = new SleepThread();
        thread.start();
        thread.interrupt();
    }

    in this code, when the thread is interrupted, it will be an exception, so the code will go through catch method
    and print Thread was interrupted!. After that, it will print Thread will be finished here!!! as it is in finally 
    bracket.

------------------------------------------------------------------------------------------------------------------------------------------------

public class DirectRunnable implements Runnable {
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        DirectRunnable runnable = new DirectRunnable();
        runnable.run();
    }
}
 it will execute in the same thread that invokes the run() method. It will not be executed concurrently or asynchronously as it would if it were invoked within a Thread object.

------------------------------------------------------------------------------------------------------------------------------------------------

public class JoinThread extends Thread {
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        JoinThread thread = new JoinThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Back to: " + Thread.currentThread().getName());
    }
}

1: Creation of Threads: First, other threads (Thread_1, Thread_2, etc.) are created, each with their own task or job to execute concurrently.

2: Start of Threads: The threads are started using their start() methods, causing them to begin executing their respective run() methods        concurrently with the main thread.

3: Execution of Main Thread: The main() method is executed by the main thread of the program. This thread may perform various tasks, including creating and starting other threads.

4: Invocation of join(): At some point in the main() method, Thread_0.join() is called. This method call causes the main thread to pause its execution and wait for Thread_0 to finish executing.
5: Execution of Thread_0: Thread_0 continues its execution. It may perform various tasks or operations as defined in its run() method.

6: Waiting in Main Thread: While Thread_0 is executing, the main thread remains in a waiting state, suspended until Thread_0 completes its execution.

7: Completion of Thread_0: Once Thread_0 completes its execution, either by reaching the end of its run() method or by throwing an uncaught exception, the main thread resumes execution.

8: Continuation of Main Thread: After Thread_0 has finished, the main thread continues executing from the point where it left off, after the join() method call.

9: Program Termination: The main thread continues executing until it completes its tasks or reaches the end of the main() method. Once the main thread completes, the program terminates.