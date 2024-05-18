package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    public static class BlockMultiplier implements Runnable {
        List<List<Integer>> tempMatrixProduct;
        List<List<Integer>> matrix_A;
        List<List<Integer>> matrix_B;
        int startRow;
        int endRow;
        int startCol;
        int endCol;

        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B,
                               List<List<Integer>> tempMatrixProduct,
                               int startRow, int endRow, int startCol, int endCol) {
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
            this.tempMatrixProduct = tempMatrixProduct;
            this.startRow = startRow;
            this.endRow = endRow;
            this.startCol = startCol;
            this.endCol = endCol;
        }

        @Override
        public void run() {
            // Calculate the product for the assigned quarter
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    int sum = 0;
                    for (int k = 0; k < matrix_A.get(0).size(); k++) {
                        sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                    }
                    tempMatrixProduct.get(i).set(j, sum);
                }
            }
        }
    }

    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A,
                                                        List<List<Integer>> matrix_B) {
        int p = matrix_A.size();
        int q = matrix_A.get(0).size();
        int r = matrix_B.get(0).size();

        List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            tempMatrixProduct.add(new ArrayList<>());
            for (int j = 0; j < r; j++) {
                tempMatrixProduct.get(i).add(0);
            }
        }

        // Divide the quarters among four threads
        Thread t1 = new Thread(new BlockMultiplier(matrix_A, matrix_B, tempMatrixProduct,
                0, p / 2, 0, r / 2));
        Thread t2 = new Thread(new BlockMultiplier(matrix_A, matrix_B, tempMatrixProduct,
                0, p / 2, r / 2, r));
        Thread t3 = new Thread(new BlockMultiplier(matrix_A, matrix_B, tempMatrixProduct,
                p / 2, p, 0, r / 2));
        Thread t4 = new Thread(new BlockMultiplier(matrix_A, matrix_B, tempMatrixProduct,
                p / 2, p, r / 2, r));

        // Start the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tempMatrixProduct;
    }

    public static void main(String[] args) {
        // Example usage
        // Create matrices A and B (fill in your actual data)
        List<List<Integer>> matrix_A = new ArrayList<>();
        List<List<Integer>> matrix_B = new ArrayList<>();
        // Initialize matrices A and B (e.g., random values)

        // Call the ParallelizeMatMul method
        List<List<Integer>> result = ParallelizeMatMul(matrix_A, matrix_B);

        // Display the resulting matrix C
        System.out.println("Resulting matrix C:");
        for (List<Integer> row : result) {
            System.out.println(row);
        }
    }
}