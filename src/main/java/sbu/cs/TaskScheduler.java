package sbu.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskScheduler {
    public static class Task implements Runnable {
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }

        @Override
        public void run() {
            try {
                // Simulate CPU utilization by sleeping for the specified processing time
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task completed: " + taskName);
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks) {
        ArrayList<String> finishedTasks = new ArrayList<>();

        // Sort tasks based on processing time (descending order)
        Collections.sort(tasks, Comparator.comparingInt(task -> -task.processingTime));

        // Create and start threads for each task
        for (Task task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                // Wait for the thread to finish
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Add task name to finishedTasks
            finishedTasks.add(task.taskName);
        }

        return finishedTasks;
    }

    public static void main(String[] args) {
        // Example usage
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task A", 3000));
        tasks.add(new Task("Task B", 2000));
        tasks.add(new Task("Task C", 5000));

        ArrayList<String> result = doTasks(tasks);
        System.out.println("Order of task execution:");
        for (String taskName : result) {
            System.out.println(taskName);
        }
    }
}
