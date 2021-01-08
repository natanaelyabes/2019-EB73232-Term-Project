package kr.ac.pusan.bsclab.algorithm.genetic.jenetics.ext;

import java.util.Random;

import io.jenetics.Mutator;
import io.jenetics.NumericGene;
import io.jenetics.internal.util.require;
import static io.jenetics.internal.math.base.clamp;

/*
 * @author Natanael Yabes Wirawan
 * @email <yabes.wirawan@gmail.com>
 * @email <yabes.wirawan@pusan.ac.kr>
 *
 */
public class PolynomialMutator<G extends NumericGene<?, G>, C extends Comparable<? super C>> extends Mutator<G, C> {

  private double _contiguity;

  public PolynomialMutator(final double probability, final double contiguity) {
    super(probability);
    _contiguity = require.nonNegative(contiguity);
  }

  public PolynomialMutator(final double probability) {
    this(probability, 20.0);
  }

  public PolynomialMutator() {
    this(DEFAULT_ALTER_PROBABILITY);
  }

  public double getContiguity() {
    return _contiguity;
  }


  /**
   * @author https://www.sciencedirect.com/science/article/pii/S0957417414001894
   * @param gene
   * @param random
   * @return G
   */
  @Override
  protected G mutate(G gene, Random random) {
    double rnd, δ1, δ2, mutPow, δq;
    double y, yl, yu, val, xy;

    y = gene.doubleValue();
    yl = gene.getMin().doubleValue();
    yu = gene.getMax().doubleValue();

    if (yl == yu) {
      y = yl ;
    } else {
      δ1 = (y - yl) / (yu - yl);
      δ2 = (yu - y) / (yu - yl);
      rnd = random.nextDouble();
      mutPow = 1.0 / (getContiguity() + 1.0);
      if (rnd <= 0.5) {
        xy = 1.0 - δ1;
        val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, getContiguity() + 1.0));
        δq = Math.pow(val, mutPow) - 1.0;
      } else {
        xy = 1.0 - δ2;
        val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, getContiguity() + 1.0));
        δq = 1.0 - Math.pow(val, mutPow);
      }
      y = y + δq * (yu - yl);
      y = clamp(y, yl, yu);
    }

    return gene.newInstance(y);
  }
}
