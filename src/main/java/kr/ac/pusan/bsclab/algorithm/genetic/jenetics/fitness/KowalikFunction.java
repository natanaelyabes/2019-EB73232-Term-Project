package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness;

public class KowalikFunction implements IFunction {
  public static final int    D = 2;
  public static final double R = 5;

  public static double fitness(final double[] x) {
    return (4 * Math.pow(x[0], 2)) - (2.1 * Math.pow(x[0], 2)) + (1/3 * Math.pow(x[0], 6)) + (x[0] * x[1]) - (4 * Math.pow(x[1], 2)) + (4 * Math.pow(x[1], 4));
  };
}
