package sbu.cs;

import java.util.ArrayList;
import java.util.Collection;
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
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                System.out.println("thread Interrupted.");
            }

        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();
        for (int i = 1; i < tasks.size(); i++) {
            int j = i;
            while (j > 0 && tasks.get(j).processingTime > tasks.get(j - 1).processingTime) {
                Task save = tasks.get(j);
                tasks.set(j, tasks.get(j - 1));
                tasks.set(j - 1, save);
                j--;
            }
        }
        for (Task t: tasks) {
            finishedTasks.add(t.taskName);
        }
        for (Task i: tasks) {
            Thread thread = new Thread(i);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted.");
            }
        }

        return finishedTasks;
    }

    public static void main(String[] args) {
    }
}
