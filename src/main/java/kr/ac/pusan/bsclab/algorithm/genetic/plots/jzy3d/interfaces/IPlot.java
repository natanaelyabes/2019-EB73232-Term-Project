package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d.interfaces;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;

public interface IPlot {
  public Range RANGE = new Range(-100, 100);
  public int STEPS = 50;

  public String TITLE = "Example Function";
  public String IMAGE_URI = "out/example_function.png";
  public Toolkit TOOLKIT = Toolkit.offscreen;

  public Mapper function();
  public void run();

  public Shape buildSurface();
  public Chart buildChart(Shape surface);
  public void buildLegend(Shape surface, IAxeLayout layout);
}