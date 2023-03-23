package Controlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {

    private static int size = 100;

    public static void writeVectorToFile(String filename, double[] vector) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < vector.length; i++) {
                writer.println(vector[i]);
            }
            writer.close();
            System.out.println("Vector saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void writeMatrixToFile(String filename, double[][] matrix) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.print(matrix[i][j] + " ");
                }
                writer.println();
            }
            writer.close();
            System.out.println("Matrix saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static double[] readVectorFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = size;
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            vector[i] = Double.parseDouble(line);
        }
        scanner.close();
        return vector;
    }

    public static double[][] readMatrixFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = size;
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] numbersAsString = line.split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Double.parseDouble(numbersAsString[j]);
            }
        }
        scanner.close();
        return matrix;

    }

    public static double[][] readMatrixFromFile2(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = size;
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] numbersAsString = line.split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Double.parseDouble(numbersAsString[i]);
            }
        }
        scanner.close();
        return matrix;

    }
}
