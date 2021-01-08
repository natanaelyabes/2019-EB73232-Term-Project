package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness;

public class RastriginFunction implements IFunction {
  public static final double A = 10;
  public static final int    D = 30;
  public static final double R = 5.12;

  public static double fitness(final double[] x) {
    double value = A * D;

    for (int i = 0; i < D; i++) {
      value += Math.pow(x[i],2) - A * Math.cos(2.0 * Math.PI * x[i]);
    }

    return value;
  };
}
