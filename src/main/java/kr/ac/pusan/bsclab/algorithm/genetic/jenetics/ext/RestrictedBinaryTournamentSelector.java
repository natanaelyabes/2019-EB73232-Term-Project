package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.ext;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jenetics.Gene;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.Selector;
import io.jenetics.util.ISeq;
import io.jenetics.util.MSeq;
import io.jenetics.util.RandomRegistry;
import io.jenetics.util.Seq;

/*
 * @author Natanael Yabes Wirawan
 * @email <yabes.wirawan@gmail.com>
 * @email <yabes.wirawan@pusan.ac.kr>
 *
 */
public class RestrictedBinaryTournamentSelector<G extends Gene<?, G>, C extends Comparable<? super C>> implements Selector<G, C> {

  private final int _sampleSize = 2;

  public int getSampleSize() {
    return _sampleSize;
  }

  @Override
  public ISeq<Phenotype<G, C>> select(Seq<Phenotype<G, C>> population, int count, Optimize opt) {
    requireNonNull(population, "Population");
    requireNonNull(opt, "Optimization");

    if (count < 0) {
	throw new IllegalArgumentException(format(
		"Selection count must be greater or equal then zero, but was %s", count));
    }

    final Random random = RandomRegistry.getRandom();
	return population.isEmpty() ? ISeq.empty() 
		                    : MSeq.<Phenotype<G, C>>ofLength(count)
                                          .fill(() -> select(population, opt, random))
					  .toISeq();
  }
  
  /**
   *  
   * Interpreted from https://pdfs.semanticscholar.org/e1ae/31f98b321c99d4c0bc4cf4be5fdcf2c828ca.pdf
   * 
   * @author Natanael Yabes Wirawan
   * @param population
   * @param opt
   * @param random
   * @return Phenotype<G, C>
   * 
   */
  private Phenotype<G, C> select(
		final Seq<Phenotype<G, C>> population,
		final Optimize opt,
		final Random random) {
	  
    final int N = population.size();

    assert _sampleSize >= 2;
    assert N >= 1;

    // Obtain 1 random individual out of population
    Phenotype<G, C> x = population.get(random.nextInt(N));

    // Sample w individuals out of population
    List<Phenotype<G, C>> samples = Stream.generate(() -> population.get(random.nextInt(N)))
      .limit(_sampleSize).collect(Collectors.toList());

    Map<Phenotype<G, C>, Double> distances = new HashMap<>();

    // For each sample
    for (Phenotype<G,C> phenotype : samples) {
      double distance_2 = 0;

      double x_val = Double.parseDouble(x.getGenotype()
					 .getGene()
					 .getAllele().toString());
      double y_val = Double.parseDouble(phenotype.getGenotype()
					         .getGene()
					         .getAllele().toString());

      // Evaluate the euclidean distance between each sample individual and x.
      distance_2 += Math.pow(x_val - y_val, 2);

      // Make sure that such distance is non-negative
      assert distance_2 >= 0;

      distances.put(phenotype, Math.sqrt(distance_2));
    }

    // Pick the closest individual to x
    Phenotype<G, C> best = Collections.min(distances.entrySet(), Comparator.comparingDouble(Entry::getValue)).getKey();

    // Put x and the "closest to x" individual to the tournament pool 
    List<Phenotype<G, C>> tournamentPool = new ArrayList<>();
    tournamentPool.add(x);
    tournamentPool.add(best);

    // Return individual with maximum fitness out of tournament pool (winner) 
    return tournamentPool.stream().max(opt.ascending()).orElseThrow(AssertionError::new); 
  }
}
