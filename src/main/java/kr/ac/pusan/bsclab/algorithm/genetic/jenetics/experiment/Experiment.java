package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.experiment;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jenetics.DoubleGene;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.DoubleRange;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.ext.PolynomialMutator;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.ext.RestrictedBinaryTournamentSelector;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness.GriewankFunction;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness.KowalikFunction;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness.RastriginFunction;
import kr.ac.pusan.bsclab.algorithm.genetic.jenetics.fitness.RosenbrockFunction;
import io.jenetics.StochasticUniversalSelector;
import io.jenetics.TournamentSelector;
import io.jenetics.ext.SimulatedBinaryCrossover;

/*
 * @author Natanael Yabes Wirawan
 * @email <yabes.wirawan@gmail.com>
 * @email <yabes.wirawan@pusan.ac.kr>
 *
 */
public class Experiment {

  // Test parameter
  private static final int N = 10;

  // Fitness parameter
  private static final int    D = KowalikFunction.D;
  private static final double R = KowalikFunction.R;

  // Report subject
  private static final String SUBJECT = KowalikFunction.class.getSimpleName() + "/RTS";

  // Fitness function
  private static double fitness(final double[] x) {
    return KowalikFunction.fitness(x);
  };

  public Experiment () throws IOException {

    // Create directory and files to statistic results.
    File statFile = new File(System.getProperty("user.dir") + "/statistics/" + SUBJECT + ".statistics.txt");
    statFile.getParentFile().mkdirs();
    statFile.delete();
    statFile.createNewFile();

    // Run experiment for N independent test.
    for (int i = 0; i < N; i++) {
      
      // Create directory and files to store results. Otherwise overwrite each file.
      File file = new File(System.getProperty("user.dir") + "/results/" + SUBJECT + "/" + i + ".txt");
      file.getParentFile().mkdirs();
      file.delete();
      file.createNewFile();

      // Tune GA engine parameter
      final Engine<DoubleGene, Double> engine = Engine
        .builder(
          Experiment::fitness,
          Codecs.ofVector(DoubleRange.of(-R, R), D))
        .populationSize(100)
        .optimize(Optimize.MINIMUM)
        .alterers(
          new SimulatedBinaryCrossover<>(0.9, 2), new PolynomialMutator<>(1 / D, 10))
        .selector(new RestrictedBinaryTournamentSelector<>())
        .build();

      // Define statistics collector
      final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

      // Print header to screen
      System.out.println(SUBJECT);
      System.out.println(String.format("%s\t%s", "iter", "fitness"));

      // Run simulation
      List<EvolutionResult<DoubleGene, Double>> er = engine.stream()
        .limit(20) // Stopping criteria: maximum number of generations.
        .peek(statistics).collect(Collectors.toList());

      // Re-stream the list and for each evolution result
      er.stream().forEach(evolutionResult -> {

        // Print fitness value to screen
        System.out.println(String.format("%s\t%s", evolutionResult.getGeneration(), evolutionResult.getBestFitness()));

        FileWriter wr;

        // Write fitness value to disk
        try {
          wr = new FileWriter(file, true);
          String fitness = evolutionResult.getBestFitness().toString() + "\n";
          wr.write(fitness);
          wr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      // Re-stream the list and collect best phenotype
      Phenotype<DoubleGene, Double> best = er.stream().collect(toBestPhenotype());

      // Print statistics
      System.out.println(statistics.toString());
      System.out.println(best.toString());

      FileWriter statWr;
      try {
        statWr = new FileWriter(statFile, true);
        String stats = statistics.toString() + "\n";
        String bst = best.toString() + "\n\n";
        statWr.write("Experiment " + (i + 1) + ":\n");
        statWr.write(stats);
        statWr.write(bst);
        statWr.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(final String args[]) throws IOException {
    new Experiment();
  }
}
