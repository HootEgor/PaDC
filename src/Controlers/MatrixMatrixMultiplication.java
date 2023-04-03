package Controlers;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MatrixMatrixMultiplication extends RecursiveAction {
    private static final int THRESHOLD = 64;
    private final double[][] matrix1;
    private final double[][] matrix2;
    private final double[][] resultMatrix;
    private final int startRow;
    private final int endRow;

    public MatrixMatrixMultiplication(double[][] matrix1, double[][] matrix2, double[][] resultMatrix, int startRow, int endRow) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    protected void compute() {
        if (endRow - startRow <= THRESHOLD) {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    double sum = 0;
                    double c = 0;
                    for (int k = 0; k < matrix1[0].length; k++) {
                        double y = matrix1[i][k] * matrix2[k][j] - c;
                        double t = sum + y;
                        c = (t - sum) - y;
                        sum = t;
                    }
                    resultMatrix[i][j] = sum;
                }
            }
        } else {
            int mid = startRow + (endRow - startRow) / 2;
            MatrixMatrixMultiplication left = new MatrixMatrixMultiplication(matrix1, matrix2, resultMatrix, startRow, mid);
            MatrixMatrixMultiplication right = new MatrixMatrixMultiplication(matrix1, matrix2, resultMatrix, mid, endRow);
            invokeAll(left, right);
        }
    }

    public static double[][] multiplyMatrixMatrix(double[][] matrix1, double[][] matrix2) {
        double[][] resultMatrix = new double[matrix1[0].length][matrix1[0].length];
        MatrixMatrixMultiplication task = new MatrixMatrixMultiplication(matrix1, matrix2, resultMatrix, 0, matrix1[0].length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        pool.shutdown();
        return resultMatrix;
    }
}