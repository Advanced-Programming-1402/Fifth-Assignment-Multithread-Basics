package sbu.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        public String getName(){
            return taskName;
        }

        public int getTime(){
            return processingTime;
        }
        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();
        for(Task task: tasks){
            finishedTasks.add(task.getName());
        }
        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */
        return finishedTasks;
    }

    public static void main(String[] args) {
        List<Thread> threadsList = new ArrayList<>();
        ArrayList<Task> tasks = new ArrayList<>();

        Task task1 = new Task("A",300);
        Task task2 = new Task("B",600);
        Task task3 = new Task("C",250);
        Task task4 = new Task("G",50);
        Task task5 = new Task("E",110);
        Task task6 = new Task("F",500);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);
        Thread thread4 = new Thread(task4);
        Thread thread5 = new Thread(task5);
        Thread thread6 = new Thread(task6);

        threadsList.add(thread1);
        threadsList.add(thread2);
        threadsList.add(thread3);
        threadsList.add(thread4);
        threadsList.add(thread5);
        threadsList.add(thread6);

        for(Thread threads:threadsList){
            threads.start();
        }

        for (Thread thread:threadsList) { // wait for all threads to join
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        Comparator <Task> theTime = Comparator.comparing(Task::getTime);
        // Sort the list using the custom comparator
        Collections.sort(tasks, Collections.reverseOrder(theTime));


        ArrayList<String> finishedTasks = doTasks(tasks);

        for (String taskName : finishedTasks) {
            System.out.print("\"" + taskName + "\"" + " ");
        }
    }
}
