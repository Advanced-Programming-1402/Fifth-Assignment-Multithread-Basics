package sbu.cs;

import java.util.ArrayList;
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

        @Override
        public void run() {
           try{
               Thread.sleep(processingTime);

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();
        ArrayList<Task> sortedtask = new ArrayList<>();

        for (Task task : tasks) {
            boolean added = false;
            for (int i = 0; i < sortedtask.size(); i++) {
                if (task.processingTime >= sortedtask.get(i).processingTime) {
                    sortedtask.add(i, task);
                    added = true;
                    break;
                }
            }
            if (!added) {
                sortedtask.add(task);
            }
        }
        for (Task task : sortedtask) {
            Thread thread = new Thread(task);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishedTasks.add(task.taskName);
        }

        return finishedTasks;
    }

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task("A", 200));
        tasks.add(new Task("B", 250));
        tasks.add(new Task("C", 150));
        tasks.add(new Task("E", 500));
        tasks.add(new Task("F", 50));
        tasks.add(new Task("G", 300));

        ArrayList<String> finishedTasks = doTasks(tasks);

        for (String task : finishedTasks) {
            System.out.println(task);
        }
    }
}
