package sbu.cs;

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
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        /*
        TODO
            Parallelize the matrix multiplication by dividing tasks between 4 threads.
            Each thread should calculate one block of the final matrix product. Each block should be a quarter of the final matrix.
            Combine the 4 resulting blocks to create the final matrix product and return it.
         */
        return null;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
