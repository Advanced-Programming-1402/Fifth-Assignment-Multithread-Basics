package sbu.cs;

import java.util.*;

public class MatrixMultiplication {

    static List<List<Integer>> matrixA = new ArrayList<>();
    static List<List<Integer>> matrixB = new ArrayList<>();

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        List<List<Integer>> matrix_A = new ArrayList<>();
        List<List<Integer>> matrix_B = new ArrayList<>();
        public BlockMultiplier() {
            // TODO
            this.matrix_A = matrixA;
            this.matrix_B = matrixB;
        }

        @Override
        public void run() {
            /*
            TODO
                Perform the calculation and store the final values in tempMatrixProduct
                we combine the 4 matrices into one here using thread

            */
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
            we split the matrices here
         */
        return null;
    }

    public static void main(String[] args) {
        // Test your code here
        theRun();
    }

    public static void theRun(){
        //*we get the matrice input from the user*/
        System.out.println("enter the size of the matrice A");
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        Scanner scanner2 = new Scanner(System.in);
        int q = scanner2.nextInt();

        System.out.println("enter the size of the matrice B");
        Scanner scanner3 = new Scanner(System.in);
        int Q = scanner3.nextInt();
        Scanner scanner4 = new Scanner(System.in);
        int r = scanner4.nextInt();

        if(q != Q){
            System.out.println("Can't multiply the matrices");
            theRun();
        }

        for(int i = 0; i < q; i++){
            List<Integer> lists = new ArrayList<>();
            for(int j = 0; j < p; j++){
                int element = scanner.nextInt();
                lists.add(element);
            }
            matrixA.add(lists);
        }

        for(int i = 0; i < Q; i++){
            List<Integer> theLists = new ArrayList<>();
            for(int j = 0; j < r; j++){
                int element = scanner.nextInt();
                theLists.add(element);
            }
            matrixB.add(theLists);
        }

    }
}
