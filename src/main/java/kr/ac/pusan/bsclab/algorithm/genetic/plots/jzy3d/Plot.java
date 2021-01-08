package kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d;

import java.io.File;
import java.io.IOException;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Dimension;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.legends.colorbars.AWTColorbarLegend;

import kr.ac.pusan.bsclab.algorithm.genetic.plots.jzy3d.interfaces.IPlot;

public class Plot implements IPlot {

  // Function Parameter
  public Range RANGE;
  public int STEPS;

  // JZY3D parameter
  public String TITLE;
  public String IMAGE_URI;
  public Toolkit TOOLKIT;

  // Function implementation
  public Mapper function() {
    return null;
  }

  // Main algorithm
  public void run() {
    // Build surface mesh.
    Shape surface = buildSurface();

    // Build chart and take surface mesh object to be rendered.
    Chart chart = buildChart(surface);

    // Get chart layout
    IAxeLayout layout = chart.getAxeLayout();

    // to build the legend.
    buildLegend(surface, layout);

    // Finally,
    if (TOOLKIT.equals(Toolkit.offscreen)) {

      // either save it as an image
      File image = new File(IMAGE_URI);

      try {
        // chart.getView().setViewPoint(new Coord3d(1, .25, 1));
        chart.getView().setViewPoint(new Coord3d(0, 2, 0));
        chart.screenshot(image);

      } catch (IOException e) {
        e.printStackTrace();
      }

    } else if (TOOLKIT.equals(Toolkit.awt)) {
      // chart.getView().setViewPoint(new Coord3d(1, .25, 1));
      chart.getView().setViewPoint(new Coord3d(0, 2, 0));

      // or display it to screen
      ChartLauncher.openChart(chart, TITLE);
    }
  }

  // Build surface mesh.
  public Shape buildSurface() {
    Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(RANGE, STEPS, RANGE, STEPS), function());
    surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
    surface.setFaceDisplayed(true);
    surface.setWireframeDisplayed(false);
    surface.setWireframeColor(Color.BLACK);
    return surface;
  }

  // Build chart by taking surface mesh as an input.
  public Chart buildChart(Shape surface) {
    Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, TOOLKIT);
    chart.getScene().getGraph().add(surface);
    return chart;
  }

  // Build legend by taking surface mesh and chart layout as inputs.
  public void buildLegend(Shape surface, IAxeLayout layout) {
    AWTColorbarLegend colorbar = new AWTColorbarLegend(surface, layout);
    colorbar.setMinimumSize(new Dimension(100, 600));
    surface.setLegend(colorbar);
  }
}