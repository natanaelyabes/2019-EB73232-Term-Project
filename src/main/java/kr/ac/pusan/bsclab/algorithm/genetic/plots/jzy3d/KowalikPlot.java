package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d;

import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

public class KowalikPlot extends Plot {

  public KowalikPlot() {

    // Override function parameter
    super.RANGE = new Range(-5, 5);
    super.STEPS = 100;

    // Override jzy3D parameter
    super.TITLE = "Kowalik Function";
    super.IMAGE_URI = "out/kowalik.above.plot.png";
    super.TOOLKIT = Toolkit.offscreen;

    // Run the algorithm
    super.run();
  }

  @Override
  public Mapper function() {
    return new Mapper() {

      @Override
      public double f(double x, double y) {
        return (4 * Math.pow(x, 2)) - (2.1 * Math.pow(x, 2)) + (Math.pow(3, -1) * Math.pow(x, 6)) + (x * y) - (4 * Math.pow(y, 2)) + (4 * Math.pow(y, 4));
      }
    };
  }

  public static void main(String args[]) {
    new KowalikPlot();
  }
}