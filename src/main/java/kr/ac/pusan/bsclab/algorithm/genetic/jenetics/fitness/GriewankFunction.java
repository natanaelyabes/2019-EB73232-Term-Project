package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness;

public class GriewankFunction implements IFunction {
  public static final int    D = 30;
  public static final double R = 600;

  public static double fitness(final double[] x) {

    double first_term = 1;
    double second_term = 1;

    for (int i = 0; i < D; i++) {
      first_term += Math.pow(x[i], 2) / 4000;
      second_term *= Math.cos(x[i] / Math.sqrt(i + 1));
    }

    return first_term - second_term;
  };
}
