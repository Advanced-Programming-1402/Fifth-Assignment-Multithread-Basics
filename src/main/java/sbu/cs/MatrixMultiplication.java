package sbu.cs;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable {
        List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        int task;
        List<List<Integer>> matrix_A, matrix_B;

        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B, int task) {
            // TODO
            this.task = task;
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
        }

        @Override
        public void run() {
            /*
            TODO
                Perform the calculation and store the final values in tempMatrixProduct
            */
            int m ,n,r;
            m = matrix_A.size();
            r = matrix_B.get(0).size();
            n = matrix_B.size();
            if (task == 1 ){
                for (int i =0 ; i<m/2 ; i++){
                    List<Integer> satr = new ArrayList<>();
                    for (int j =0 ; j<r/2 ; j++){
                        int sum =0 ;
                        for (int k = 0 ; k<n ; k++){
                            sum+=matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                        }
                        satr.add(sum);
                    }
                    tempMatrixProduct.add(satr);
                }
            }

            else if (task ==2){
                for (int i =0 ; i<m/2 ; i++){
                    List<Integer> satr = new ArrayList<>();
                    for (int j =r/2 ; j<r ; j++){
                        int sum =0 ;
                        for (int k = 0 ; k<n ; k++){
                            sum+=matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                        }
                        satr.add(sum);
                    }
                    tempMatrixProduct.add(satr);
                }
            }

            else if (task ==3){

                for (int i =m/2 ; i<m ; i++){
                    List<Integer> satr = new ArrayList<>();
                    for (int j =0 ; j<r/2 ; j++){
                        int sum =0 ;
                        for (int k = 0 ; k<n ; k++){
                            sum+=matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                        }
                        satr.add(sum);
                    }
                    tempMatrixProduct.add(satr);
                }

            }

            else if (task ==4){
                for (int i =m/2 ; i<m ; i++){
                    List<Integer> satr = new ArrayList<>();
                    for (int j =r/2 ; j<r ; j++){
                        int sum =0 ;
                        for (int k = 0 ; k<n ; k++){
                            sum+=matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                        }
                        satr.add(sum);
                    }
                    tempMatrixProduct.add(satr);
                }
            }
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
            List<List<Integer>> ParallelizeMatMul = new ArrayList<>();

            List<List<Integer>> mat1 = new ArrayList<>();
            List<List<Integer>> mat2 = new ArrayList<>();
            List<List<Integer>> mat3 = new ArrayList<>();
            List<List<Integer>> mat4 = new ArrayList<>();

            BlockMultiplier BM1 = new BlockMultiplier(matrix_A, matrix_B, 1);
            BlockMultiplier BM2 = new BlockMultiplier( matrix_A, matrix_B,2);
            BlockMultiplier BM3 = new BlockMultiplier( matrix_A, matrix_B,3);
            BlockMultiplier BM4 = new BlockMultiplier( matrix_A, matrix_B , 4);

            Thread thread1 = new Thread(BM1);
            Thread thread2 = new Thread(BM2);
            Thread thread3 = new Thread(BM3);
            Thread thread4 = new Thread(BM4);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            try {
                thread1.join();
                mat1 = BM1.getTempMatrixProduct();
                thread2.join();
                mat2 = BM2.getTempMatrixProduct();
                thread3.join();
                mat3 = BM3.getTempMatrixProduct();
                thread4.join();
                mat4 = BM4.getTempMatrixProduct();
            }catch (InterruptedException e) {
                System.out.println("thread Interrupted.");
            }
            for (int i = 0; i < mat1.size(); i++) {
                List<Integer> satr = new ArrayList<>();
                for (int j = 0; j < mat1.get(0).size(); j++)
                    satr.add(mat1.get(i).get(j));
                for (int j = 0; j < mat2.get(0).size(); j++)
                    satr.add(mat2.get(i).get(j));
                ParallelizeMatMul.add(satr);
            }
            for (int i = 0; i < mat3.size(); i++) {
                List<Integer> satr = new ArrayList<>();
                for (int j = 0; j < mat3.get(0).size(); j++)
                    satr.add(mat3.get(i).get(j));
                for (int j = 0; j < mat4.get(0).size(); j++)
                    satr.add(mat4.get(i).get(j));
                ParallelizeMatMul.add(satr);
            }
            return ParallelizeMatMul;
        }

    public static void main(String[] args) {
        // Test your code here

    }
}
