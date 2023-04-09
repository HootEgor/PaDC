package Controlers;

public class Calculator {

    private static final int pullSize = 2;
    public static double[] addVectors(double[] vector1, double[] vector2) {
        double[] resultVector = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            resultVector[i] = vector1[i] + vector2[i];
        }
        return resultVector;
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        double[] resultVector;
        resultVector = MatrixVectorMultiplication.multiplyMatrixVector(matrix,vector,pullSize);

        return resultVector;
    }

    public static double[][] multiplyMatrixMatrix(double[][] matrix1, double[][] matrix2) {
        double[][] resultMatrix;
        resultMatrix = MatrixMatrixMultiplication.multiplyMatrixMatrix(matrix1, matrix2, pullSize*pullSize);

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
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                if (aDouble < minValue) {
                    minValue = aDouble;
                }
            }
        }
        return minValue;
    }

    public static void printMatrix(double[][] matrix) {
        int cols = matrix[0].length;
        for (double[] doubles : matrix) {
            for (int j = 0; j < cols; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
    }
}
