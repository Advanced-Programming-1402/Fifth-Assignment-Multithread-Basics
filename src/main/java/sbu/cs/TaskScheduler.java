package sbu.cs;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

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
        public int getProcessingTime(){
            return processingTime;
        }
        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();

        //sorting tasks base on processing time
        Collections.sort(tasks,Comparator.comparing(Task::getProcessingTime).reversed());

        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */

        //make a thread for each task and add the name of it to finishedTasks when it completed
        for (Task task : tasks) {
            try {
                Thread thread = new Thread(task);
                thread.start();
                thread.join();  //join() method is for waiting for a task to completed and then go to another one
                finishedTasks.add(task.taskName);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}