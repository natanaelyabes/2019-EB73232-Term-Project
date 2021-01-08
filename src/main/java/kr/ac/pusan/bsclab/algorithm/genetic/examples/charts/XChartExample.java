package kr.ac.pusan.bsclab.algorithm.genetic.examples.charts;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class XChartExample {

  // Fitness function
  private static double fitness(final double x) {
    return cos(0.5 + sin(x)) * cos(x);
  }

  public static void main(String args[]) {
    ArrayList<Double> xData = new ArrayList<Double>();
    ArrayList<Double> yData = new ArrayList<Double>();

    for (double i = 0; i < 2 * PI; i = i + 0.01) {
      xData.add(i);
      yData.add(fitness(i));
    }

    XYChart chart = QuickChart.getChart("Real Function", "X", "Y", "y(x)", xData, yData);

    new SwingWrapper<XYChart>(chart).displayChart();
  }
}