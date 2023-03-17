package Main;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

import static Controlers.Calculator.*;
import static Controlers.FileManager.*;

public class Main {

    public static void main(String[] args){

        double[][] MC;
        double[][] MD;
        double[][] MX;
        double[] D;
        double[] B;

        double b = 3.1415926;

        double[] E;
        double[][] MA;

        try {
            MC = readMatrixFromFile("MC.txt");
            MD = readMatrixFromFile("MD.txt");
            MX = readMatrixFromFile("MX.txt");
            D = readVectorFromFile("D.txt");
            B = readVectorFromFile("B.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        long startTime = System.nanoTime();

        double minMC = findMinValue(MC);
        double[] DminMC = multiplyScalarVector(D, minMC);
        double[] BxMC = multiplyMatrixVector(MC, B);
        E = addVectors(BxMC, DminMC);
        long endTime = System.nanoTime();
        long ECalcTime = endTime - startTime;

        startTime = System.nanoTime();
        double[][] MCminusMX = subtractMatrixMatrix(MC, MX);
        double[][] bMDxMCminusMX = multiplyScalarMatrix(b, multiplyMatrixMatrix(MD, MCminusMX));
        double[][] MXxMC = multiplyMatrixMatrix(MX, MC);
        double[][] MXxMCxb = multiplyScalarMatrix(b, MXxMC);
        MA = addMatrices(bMDxMCminusMX, MXxMCxb);
        endTime = System.nanoTime();

        long MACalcTime = endTime - startTime;

        System.out.println("E = " + Arrays.toString(E));
        System.out.println("MA = ");
        printMatrix(MA);

        System.out.println("Calculation time:\tE = " + ECalcTime);
        System.out.println("\t\t\t\t\tMA = " + MACalcTime);

        writeMatrixToFile("Result_MA.txt", MA);
        writeVectorToFile("Result_E.txt", E);

    }
}

//    private static double[] generateVector(int size) {
//        double[] vector = new double[size];
//        Random random = new Random();
//        for (int i = 0; i < size; i++) {
//            vector[i] = random.nextDouble();
//        }
//        return vector;
//    }
//
//    private static double[][] generateMatrix(int size) {
//        double[][] matrix = new double[size][size];
//        Random random = new Random();
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                matrix[i][j] = random.nextDouble();
//            }
//        }
//        return matrix;
//    }