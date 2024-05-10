package sbu.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        List<List<Integer>> MatrixA;
        List<List<Integer>> MatrixB;
        int startrow;
        int endrow;
        int sum;


        public BlockMultiplier(List<List<Integer>> MatrixA, List<List<Integer>> MatrixB, List<List<Integer>> tempMatrixProduct, int startrow, int endrow) {
            this.MatrixA = MatrixA;
            this.MatrixB = MatrixB;
            this.tempMatrixProduct = tempMatrixProduct;
            this.startrow = startrow;
            this.endrow = endrow;
        }

        @Override
        public void run() {
            int p = MatrixA.size(); //تعداد سطر های ماتریس اول
            int r = MatrixB.get(0).size(); //تعداد ستون های ماتریس دوم
            int q = MatrixA.get(0).size(); //تعداد ستون های ماتری اول که همان تعداد سطر های ماتریس دوم است

            for(int i = startrow ; i < endrow ; i++){
                for(int j = 0 ; j < r ; j++){
                    sum = 0;
                    for(int m = 0 ; m < q ; m++){
                        sum += MatrixA.get(i).get(m) * MatrixB.get(m).get(j);
                    }
                    tempMatrixProduct.get(i).set(j , sum);
                }
            }
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> MatrixA, List<List<Integer>> MatrixB) {
        int p = MatrixA.size();
        int r = MatrixB.get(0).size();

        List<List<Integer>> MatrixProduct = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            MatrixProduct.add(new ArrayList<>(Collections.nCopies(r, 0))); //مقدار دهی اولیه برای ماتریس جدید
        }

        Thread thread1 = new Thread(new BlockMultiplier(MatrixA, MatrixB, MatrixProduct, 0, (p/4)));
        Thread thread2 = new Thread(new BlockMultiplier(MatrixA, MatrixB, MatrixProduct, (p/4), p/2));
        Thread thread3 = new Thread(new BlockMultiplier(MatrixA, MatrixB, MatrixProduct, p/2, ((3/4)*p)));
        Thread thread4 = new Thread(new BlockMultiplier(MatrixA, MatrixB, MatrixProduct, ((3/4)*p), p));


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try{
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return MatrixProduct;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
