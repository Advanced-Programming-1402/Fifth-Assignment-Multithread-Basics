# Fifth-Assignment-Multithread Theoretical Questions 📝 answers

## Question 1:

1. interrupt() method: If any thread is in sleeping or waiting for a state then using the interrupt() method, we can interrupt the execution of that thread by showing InterruptedException. A thread that is in the sleeping or waiting state can be interrupted with the help of the interrupt() method of Thread class.

2. in this code sleep get ignored and thread work without sleep.
### outPut: 
Thread was interrupted!

Thread will be finished here!!!


## Question 2:
1. the run() method is called directly, so the message will be printed from the main thread
2. run() in this code exactly worked like function
3. code running step by step

### outPut: 
Running in main

## Question 3:
1. The join method allows one thread to wait for the completion of another. If t is a Thread object whose thread is currently executing, t. join(); causes the current thread to pause execution until t 's thread terminates.

### outPut: 
Running in: Thread-0

Back to: main

