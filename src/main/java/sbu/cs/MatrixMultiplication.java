package sbu.cs;

import java.util.*;

public class MatrixMultiplication {

    static List<List<Integer>> matrixA = new ArrayList<>();
    static List<List<Integer>> matrixB = new ArrayList<>();

    static int p;
    static int q;
    static int Q;
    static int r;

    static List<List<Integer>> firstPart = new ArrayList<>();
    static List<List<Integer>> secondtPart = new ArrayList<>();
    static List<List<Integer>> thirdPart = new ArrayList<>();
    static List<List<Integer>> fourthPart = new ArrayList<>();

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        List<List<Integer>> matrix_A = new ArrayList<>();
        List<List<Integer>> matrix_B = new ArrayList<>();
        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) {
            // TODO
            this.matrix_A = matrixA;
            this.matrix_B = matrixB;
            this.tempMatrixProduct = new ArrayList<>();
        }

        @Override
        public void run() {
            /*
            TODO
                Perform the calculation and store the final values in tempMatrixProduct
                we combine the 4 matrices into one here using thread

            */
            // Perform matrix multiplication for the assigned blocks
            int rowsA = matrix_A.size();
            int colsA = matrix_A.get(0).size();
            int colsB = matrix_B.get(0).size();

            // Initialize the temporary result matrix
            tempMatrixProduct = new ArrayList<>();
            for (int i = 0; i < rowsA; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < colsB; j++) {
                    int sum = 0;
                    for (int k = 0; k < colsA; k++) {
                        sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                    }
                    row.add(sum);
                }
                tempMatrixProduct.add(row);
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

//    public List<List<Integer>> theMatrices(){
//
//    }
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        /*
        TODO
            Parallelize the matrix multiplication by dividing tasks between 4 threads.
            Each thread should calculate one block of the final matrix product. Each block should be a quarter of the final matrix.
            Combine the 4 resulting blocks to create the final matrix product and return it.
            we split the matrices here
         */


        for(int i = 0; i < p/2; i++){
            firstPart.add(matrix_A.get(i));
        }

        for(int i = p/2; i < p; i++){
            secondtPart.add(matrix_A.get(i));
        }


        for(int i = 0; i < r/2; i++){
            List<Integer> verticalList = new ArrayList<>();

            for(List<Integer> sublist : matrix_B){
                verticalList.add(sublist.get(i));
            }
            thirdPart.add(verticalList);
        }

        for(int i = r/2; i < r; i++){
            List<Integer> verticalList = new ArrayList<>();

            for(List<Integer> sublist : matrix_B){
                verticalList.add(sublist.get(i));
            }
            fourthPart.add(verticalList);
        }

//        System.out.println(firstPart);
//        System.out.println(secondtPart);
//        System.out.println(thirdPart);
//        System.out.println(fourthPart);


        // Create four instances of BlockMultiplier and start four threads
        BlockMultiplier multiplier1 = new BlockMultiplier(firstPart, thirdPart);
        BlockMultiplier multiplier2 = new BlockMultiplier(firstPart, fourthPart);
        BlockMultiplier multiplier3 = new BlockMultiplier(secondtPart, thirdPart);
        BlockMultiplier multiplier4 = new BlockMultiplier(secondtPart, fourthPart);

        Thread thread1 = new Thread(multiplier1);
        Thread thread2 = new Thread(multiplier2);
        Thread thread3 = new Thread(multiplier3);
        Thread thread4 = new Thread(multiplier4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print(multiplier1.getTempMatrixProduct());
        System.out.println(multiplier2.getTempMatrixProduct());
        System.out.print(multiplier3.getTempMatrixProduct());
        System.out.println(multiplier4.getTempMatrixProduct());

        // Combine the results
        List<List<Integer>> result = new ArrayList<>();
        result.addAll(multiplier1.getTempMatrixProduct());
        result.addAll(multiplier2.getTempMatrixProduct());
        result.addAll(multiplier3.getTempMatrixProduct());
        result.addAll(multiplier4.getTempMatrixProduct());
        return result;
    }

    public static void main(String[] args) {
        // Test your code here
        theRun();
    }

    public static void theRun(){
        //*we get the matrice input from the user*/
        System.out.println("enter the size of the matrice A");
        Scanner scanner = new Scanner(System.in);
        p = scanner.nextInt();
        q = scanner.nextInt();

        System.out.println("enter the size of the matrice B");
        Scanner scanner2 = new Scanner(System.in);
        Q = scanner2.nextInt();
        r = scanner2.nextInt();

        if(q != Q){
            System.out.println("Can't multiply the matrices");
            theRun();
        }

        System.out.println("Please enter the elements of matrice A");

        for(int i = 0; i < q; i++){
            List<Integer> lists = new ArrayList<>();
            for(int j = 0; j < p; j++){
                int element = scanner.nextInt();
                lists.add(element);
            }
            matrixA.add(lists);
        }

        System.out.println("Please enter the elements of matrice B");

        for(int i = 0; i < Q; i++){
            List<Integer> theLists = new ArrayList<>();
            for(int j = 0; j < r; j++){
                int element = scanner.nextInt();
                theLists.add(element);
            }
            matrixB.add(theLists);
        }
        // Get the result of matrix multiplication
        List<List<Integer>> result = ParallelizeMatMul(matrixA, matrixB);

        // Print the result
//        System.out.println("Result of matrix multiplication:");
//        for (List<Integer> row : result) {
//            System.out.println(row);
//        }
    }
}
