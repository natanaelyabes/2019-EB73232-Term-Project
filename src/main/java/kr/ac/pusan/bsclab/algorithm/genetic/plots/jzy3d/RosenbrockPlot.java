package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d;

import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

public class RosenbrockPlot extends Plot {

  public RosenbrockPlot() {

    // Override function parameter
    super.RANGE = new Range(0, 5);
    super.STEPS = 100;

    // Override jzy3D parameter
    super.TITLE = "Rosenbrock Function";
    super.IMAGE_URI = "plots/rosenbrock.log-scaled.above.plot.png";
    super.TOOLKIT = Toolkit.offscreen;

    // Run the algorithm
    super.run();
  }

  @Override
  public Mapper function() {
    return new Mapper() {

		  @Override
      public double f(double x, double y) {
        // return (100 * Math.pow(y - Math.pow(x, 2), 2)) + Math.pow(1 - x, 2);
        return Math.log(1 + (100 * Math.pow(y - Math.pow(x, 2), 2)) + Math.pow(1 - x, 2));
      }
    };
  }

  public static void main(String args[]) {
    new RosenbrockPlot();
  }
}