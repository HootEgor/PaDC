package Controlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Calculator {

    private static int pullSize = 2;
    public static double[] addVectors(double[] vector1, double[] vector2) {
        double[] resultVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            resultVector[i] = vector1[i] + vector2[i];
        }
        return resultVector;
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] resultVector = new double[rows];
        ReentrantLock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(pullSize);

        for (int i = 0; i < rows; i++) {
            int finalI = i;
            executorService.submit(() -> {
                double sum = 0;
                double c = 0;
                for (int j = 0; j < cols; j++) {
                    double y = matrix[finalI][j] * vector[j] - c;
                    double t = sum + y;
                    c = (t - sum) - y;
                    sum = t;
                }

                lock.lock();
                try {
                    resultVector[finalI] = sum;
                } finally {
                    lock.unlock();
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultVector;
    }

    public static double[][] multiplyMatrixMatrix(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] resultMatrix = new double[rows1][cols2];
        ReentrantLock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(pullSize);

        for (int i = 0; i < rows1; i++) {
            int finalI = i;
            executorService.submit(() -> {
                for (int j = 0; j < cols2; j++) {
                    double sum = 0;
                    for (int k = 0; k < cols1; k++) {
                        sum += matrix1[finalI][k] * matrix2[k][j];
                    }
                    lock.lock();
                    try {
                        resultMatrix[finalI][j] = sum;
                    } finally {
                        lock.unlock();
                    }

                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultMatrix;
    }

    public static double[][] multiplyScalarMatrix(double scalar, double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] resultMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = scalar * matrix[i][j];
            }
        }
        return resultMatrix;
    }

    public static double[][] subtractMatrixMatrix(double[][] matrix1, double[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        double[][] resultMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return resultMatrix;
    }

    public static double[][] addMatrices(double[][] matrixA, double[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        double[][] resultMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        return resultMatrix;
    }

    public static double[] multiplyScalarVector(double[] vector, double scalar) {
        int length = vector.length;
        double[] resultVector = new double[length];
        for (int i = 0; i < length; i++) {
            resultVector[i] = vector[i] * scalar;
        }
        return resultVector;
    }

    public static double findMinValue(double[][] matrix) {
        double minValue = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < minValue) {
                    minValue = matrix[i][j];
                }
            }
        }
        return minValue;
    }

    public static void printMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
