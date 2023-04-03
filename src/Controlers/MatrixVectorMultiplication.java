package Controlers;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MatrixVectorMultiplication extends RecursiveAction {
    private static final int THRESHOLD = 100;
    private final double[][] matrix;
    private final double[] vector;
    private final double[] resultVector;
    private final int start;
    private final int end;

    public MatrixVectorMultiplication(double[][] matrix, double[] vector, double[] resultVector, int start, int end) {
        this.matrix = matrix;
        this.vector = vector;
        this.resultVector = resultVector;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                double sum = 0;
                double c = 0;
                for (int j = 0; j < matrix[0].length; j++) {
                    double y = matrix[i][j] * vector[j] - c;
                    double t = sum + y;
                    c = (t - sum) - y;
                    sum = t;
                }
                resultVector[i] = sum;
            }
        } else {
            int mid = start + (end - start) / 2;
            MatrixVectorMultiplication left = new MatrixVectorMultiplication(matrix, vector, resultVector, start, mid);
            MatrixVectorMultiplication right = new MatrixVectorMultiplication(matrix, vector, resultVector, mid, end);
            invokeAll(left, right);
        }
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        double[] resultVector = new double[matrix.length];
        MatrixVectorMultiplication task = new MatrixVectorMultiplication(matrix, vector, resultVector, 0, matrix.length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        pool.shutdown();
        return resultVector;
    }
}