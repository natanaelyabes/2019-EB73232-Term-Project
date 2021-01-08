package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d;

import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

public class GriewankPlot extends Plot {

  public GriewankPlot() {

    // Override function parameter
    super.RANGE = new Range(-30, 30);
    super.STEPS = 100;

    // Override jzy3D parameter
    super.TITLE = "Griewank Function";
    super.IMAGE_URI = "out/griewank-fine-grained.above.plot.png";
    super.TOOLKIT = Toolkit.offscreen;

    // Run the algorithm
    super.run();
  }

  @Override
  public Mapper function() {

    return new Mapper() {

      @Override
      public double f(double x, double y) {
        return 1 + (Math.pow(x, 2) / 4000) + (Math.pow(y, 2) / 4000) - (Math.cos(x / 1) * Math.cos(y / 2));
      }
    };
  }

  public static void main(String args[]) {
    new GriewankPlot();
  }
}