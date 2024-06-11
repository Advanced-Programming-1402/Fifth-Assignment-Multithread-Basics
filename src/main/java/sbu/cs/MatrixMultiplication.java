package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        List<List<Integer>> matrix_A;
        List<List<Integer>> matrix_B;
        int row;
        int column;
        int num;
        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) {
            // TODO
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
            tempMatrixProduct = new ArrayList<>();
            row = matrix_A.size();
            column = matrix_B.getFirst().size();
            num = matrix_B.size();
        }

        @Override
        public void run() {
            /*
            TODO
                Perform the calculation and store the final values in tempMatrixProduct
            */
            for (int i = 0; i < row; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < column; j++) {
                    int sum = 0;
                    for (int k = 0; k < num; k++) {
                        sum += matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                    }
                    row.add(sum);
                }
                tempMatrixProduct.add(row);
            }
            getTempMatrixProduct();
        }
        public List<List<Integer>> getTempMatrixProduct() {
            return tempMatrixProduct;
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        /*
        TODO
            Parallelize the matrix multiplication by dividing tasks between 4 threads.
            Each thread should calculate one block of the final matrix product. Each block should be a quarter of the final matrix.
            Combine the 4 resulting blocks to create the final matrix product and return it.
         */

        /*
            i want to pass two matrix that makes a quarter of the final matrix
            to the BlockMultiplier class to calculate that
        */

        List<Thread> threads = new ArrayList<>();
        List<BlockMultiplier> blocks = new ArrayList<>();
        List<List<List<Integer>>> finalBlocks = new ArrayList<>();

        List<List<Integer>> matrix_UA = new ArrayList<>(); //separated the uper part of matrixA
        for (int i = 0; i < matrix_A.size()/2; i++) {
            matrix_UA.add(matrix_A.get(i));
        }
        List<List<Integer>> matrix_LA = new ArrayList<>(); //separated the lower part of matrixA
        for (int i = matrix_A.size()/2 ; i < matrix_A.size(); i++) {
            matrix_LA.add(matrix_A.get(i));
        }
        List<List<Integer>> matrix_LB = new ArrayList<>(); //separated the left part of matrixB
        for (int i = 0; i < matrix_B.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix_B.getFirst().size()/2 ; j++) {
                row.add(matrix_B.get(i).get(j));
            }
            matrix_LB.add(row);
        }
        List<List<Integer>> matrix_RB = new ArrayList<>(); //separated the right part of matrixB
        for (int i = 0; i < matrix_B.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = matrix_B.getFirst().size()/2 ; j < matrix_B.getFirst().size() ; j++) {
                row.add(matrix_B.get(i).get(j));
            }
            matrix_RB.add(row);
        }

        //make thread of the particular objects
        BlockMultiplier block0 = new BlockMultiplier(matrix_UA,matrix_LB);
        Thread thread0 = new Thread(block0);
        threads.add(thread0);
        blocks.add(block0);
        BlockMultiplier block1 = new BlockMultiplier(matrix_UA,matrix_RB);
        Thread thread1 = new Thread(block1);
        threads.add(thread1);
        blocks.add(block1);
        BlockMultiplier block2 = new BlockMultiplier(matrix_LA,matrix_LB);
        Thread thread2 = new Thread(block2);
        threads.add(thread2);
        blocks.add(block2);
        BlockMultiplier block3 = new BlockMultiplier(matrix_LA,matrix_RB);
        Thread thread3 = new Thread(block3);
        threads.add(thread3);
        blocks.add(block3);

        //start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        for (int i = 0; i < 4; i++) {
            try {
                threads.get(i).join();
                finalBlocks.add(blocks.get(i).getTempMatrixProduct());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        List<List<Integer>> finalMatrix = new ArrayList<>();
        //make the final matrix with final blocks
        for (int i = 0; i < matrix_A.size() / 2; i++) {
            List<Integer> row = new ArrayList<>();
            for (int k = 0; k < 2; k++) {
                for (int j = 0; j < matrix_B.getFirst().size() / 2; j++) {
                    row.add(finalBlocks.get(k).get(i).get(j));
                }
            }
            finalMatrix.add(row);
        }
        for (int i = 0; i < matrix_A.size() / 2; i++) {
            List<Integer> row = new ArrayList<>();
            for (int k = 2; k < 4; k++) {
                for (int j = 0; j < matrix_B.getFirst().size() / 2; j++) {
                    row.add(finalBlocks.get(k).get(i).get(j));
                }
            }
            finalMatrix.add(row);
        }

        return finalMatrix;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}