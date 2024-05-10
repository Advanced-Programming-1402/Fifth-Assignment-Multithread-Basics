package sbu.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

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

        public int getProcessingTime() {
            return processingTime;
        }

        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                System.out.println("mnm mnm shirmoz");;
            }

        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();

        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */
        Collections.sort(tasks , Comparator.comparing(Task :: getProcessingTime));
        for (int i =tasks.size()-1  ; i>=0 ; i--){
            finishedTasks.add(tasks.get(i).taskName);
            Thread thread = new Thread(tasks.get(i));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
        ArrayList<Task> tasks = new ArrayList<>();
        System.out.println(doTasks(tasks));

    }
}
