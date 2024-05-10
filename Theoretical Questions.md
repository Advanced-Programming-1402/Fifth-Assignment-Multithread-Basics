# Theoretical Questions 📝


## Questions 1 :

- What will be printed after interrupting the thread?

## Code in java 
## [
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
## ]

## answer : 
When the interrupt() method is called on a thread, it sets the interrupt flag of the thread to true. If the thread is currently in a sleeping or waiting state (which it is in this case due to the Thread.sleep(10000);), An InterruptedException will be thrown and the interrupt flag of the thread is cleared (set to false).
 In this code, when the thread.interrupt(); is called in the main method, it interrupts the sleeping thread, causing it to An InterruptedException is thrown. This exception is caught in the catch block and “Thread was interrupted!” is printed. After that, the finally block is executed and “Thread will be finished here!!!” is printed.

# So, the output of this code will be:

## Thread was interrupted!
## Thread will be finished here!!!
# ___________________________________________________________________________________________________________

## Questions 2 :

- In Java, what would be the outcome if the run() method of a Runnable object is invoked directly, without initiating it inside a Thread object?

## Code in java:
## [
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
## ]

## answer:
In Java, if you invoke the run() method directly without initiating it inside a Thread object, it will not start a new thread. Instead, the run() method will be executed in the current thread, just like any other method call.
In this code, the run() method of the DirectRunnable object is called directly in the main method. Therefore, it will be executed in the main thread, not in a new thread. the Thread.currentThread().getName() method returns the name of the currently executing thread, which is the main thread in this case.

# So, the output of this code will be:

## Running in: main
# ___________________________________________________________________________________________________________

## Questions 3 :

- Elaborate on the sequence of events that occur when the join() method of a thread (let's call it Thread_0) is invoked within the Main() method of a Java program.

## Code in java:
## [
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
## ]

## answer:
In Java, the join() method is used to pause the current thread (in this case, the main thread) until the thread on which join() is called (in this case, Thread_0) finishes its execution. Here’s the sequence of events that occur when join() is invoked in your code:

1. JoinThread thread = new JoinThread(); - A new JoinThread object named thread is created.

2. thread.start(); - The start() method is called on thread, which causes a new thread of execution to start. The run() method of thread is called in this new thread. The System.out.println("Running in: " + Thread.currentThread().getName()); statement is executed in this new thread, so it prints “Running in: Thread-0”.

3. thread.join(); - The main thread calls join() on thread. This causes the main thread to pause and wait for thread to finish its execution. If thread is interrupted while main is waiting, an InterruptedException will be thrown.

4. Once thread has finished its execution, the main thread resumes. The System.out.println("Back to: " + Thread.currentThread().getName()); statement is executed in the main thread, so it prints “Back to: main”.

5. If thread is interrupted during its execution, the InterruptedException is caught and the stack trace of the exception is printed.

# So, the output of your code will be something like this:

## Running in: Thread-0
## Back to: main



