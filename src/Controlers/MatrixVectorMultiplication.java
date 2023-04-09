package Controlers;

import java.util.concurrent.*;

public class MatrixVectorMultiplication {
    private final double[][] matrix;
    private final double[] vector;
    private final double[] resultVector;
    private final int numThreads;
    private final BlockingQueue<Integer> queue;

    public MatrixVectorMultiplication(double[][] matrix, double[] vector, double[] resultVector, int numThreads) {
        this.matrix = matrix;
        this.vector = vector;
        this.resultVector = resultVector;
        this.numThreads = numThreads;
        this.queue = new LinkedBlockingQueue<>(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            queue.add(i);
        }
    }

    public double[] multiply() {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.submit(new MatrixVectorMultiplicationTask());
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultVector;
    }

    private class MatrixVectorMultiplicationTask implements Callable<Void> {
        @Override
        public Void call(){
            while (true) {
                Integer i = queue.poll();
                if (i == null) {
                    break;
                }
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
            return null;
        }
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector, int numThreads) {
        double[] resultVector = new double[matrix.length];
        MatrixVectorMultiplication task = new MatrixVectorMultiplication(matrix, vector, resultVector, numThreads);
        return task.multiply();
    }
}