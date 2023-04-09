package Controlers;

import java.util.concurrent.*;


public class MatrixMatrixMultiplication{
    private final int step = 10;
    private final double[][] matrix1;
    private final double[][] matrix2;
    private final double[][] resultMatrix;
    private final int numThreads;
    private final BlockingQueue<Integer> queue;

    public MatrixMatrixMultiplication(double[][] matrix1, double[][] matrix2, double[][] resultMatrix, int numThreads) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;
        this.numThreads = numThreads;
        this.queue = new LinkedBlockingQueue<>(matrix1.length);
        for (int i = 0; i < matrix1.length; i+=step) {
            if(i + step <= matrix1.length)
                queue.add(i + step);
            else
                queue.add((i + step)%matrix1.length);
        }
    }

    public double[][] multiply() {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.submit(new MatrixMatrixMultiplication.MatrixMatrixMultiplicationTask());
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMatrix;
    }

    private class MatrixMatrixMultiplicationTask implements Callable<Void> {
        @Override
        public Void call(){
            while (true) {
                Integer end = queue.poll();
                if (end == null) {
                    break;
                }
                for(int i = end - step; i < end; i++)
                {
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
            }
            return null;
        }
    }

    public static double[][] multiplyMatrixMatrix(double[][] matrix1, double[][] matrix2, int numThreads) {
        double[][] resultMatrix = new double[matrix1.length][matrix2[0].length];
        MatrixMatrixMultiplication task = new MatrixMatrixMultiplication(matrix1, matrix2, resultMatrix, numThreads);
        return task.multiply();
    }
}