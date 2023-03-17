package Controlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Calculator {
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
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            resultVector[i] = sum;
        }
        return resultVector;
    }

    public static double[][] multiplyMatrixMatrix(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;
        if (cols1 != rows2) {
            throw new IllegalArgumentException("Invalid matrix dimensions");
        }
        double[][] resultMatrix = new double[rows1][cols2];

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            for (int i = 0; i < rows1; i++) {
                for (int j = 0; j < cols2/2; j++) {
                    for (int k = 0; k < cols1; k++) {
                        synchronized (resultMatrix) {
                            resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                        }
                    }

                }
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < rows1; i++) {
                for (int j = cols2/2; j < cols2; j++) {
                    for (int k = 0; k < cols1; k++) {
                        synchronized (resultMatrix) {
                            resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                        }
                    }

                }
            }
        };

        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return resultMatrix;
    }

    public static double[][] multiplyScalarMatrixVector(double scalar, double[][] matrix) {
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
