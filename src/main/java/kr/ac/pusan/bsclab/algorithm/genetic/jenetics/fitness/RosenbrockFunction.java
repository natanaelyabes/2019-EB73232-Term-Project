package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness;

public class RosenbrockFunction implements IFunction {
  public static final int    D = 30;
  public static final double R = 30;

  public static double fitness(final double[] x) {

    double value = 0;
    for (int i = 1; i < D - 1; i++) {
      value += Math.log(1 + (100 * Math.pow(x[i + 1] - Math.pow(x[i], 2), 2)) + Math.pow(1 - x[i], 2));
    }

    return value;
  };
}
