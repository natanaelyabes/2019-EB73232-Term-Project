package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d;

import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

public class RastriginPlot extends Plot {

  public RastriginPlot() {

    // Override function parameter
    super.RANGE = new Range(-5.12f, 5.12f);
    super.STEPS = 100;

    // Override jzy3D parameter
    super.TITLE = "Rastrigin Function";
    super.IMAGE_URI = "out/rastrigin.above.plot.png";
    super.TOOLKIT = Toolkit.offscreen;

    // Run the algorithm
    super.run();
  }

  @Override
  public Mapper function() {
    return new Mapper() {

      @Override
      public double f(double x, double y) {
        return (10 * 2) + (Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x) + Math.pow(y, 2) - 10 * Math.cos(2 * Math.PI * y));
      }
    };
  }

  public static void main(String args[]) {

    // Do plot the Rastrigin function
    new RastriginPlot();
  }
}