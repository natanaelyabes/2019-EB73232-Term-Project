package kr.ac.pusan.bsclab.algorithm.genetic.jmetal;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.singleobjective.Griewank;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

public class SteadyStateGeneticAlgorithm {
  public static void main(String args[]) {
    Algorithm<DoubleSolution> algorithm;
    Griewank problem = new Griewank(30);

    CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(0.9, 20.0) ;
    MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20.0) ;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>() ;

    algorithm = new GeneticAlgorithmBuilder<DoubleSolution>(problem, crossoverOperator, mutationOperator)
      .setPopulationSize(100)
      .setMaxEvaluations(25000)
      .setSelectionOperator(selectionOperator)
      .setVariant(GeneticAlgorithmBuilder.GeneticAlgorithmVariant.STEADY_STATE)
      .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
      .execute() ;

    long computingTime = algorithmRunner.getComputingTime() ;

    DoubleSolution solution = algorithm.getResult() ;
    List<DoubleSolution> population = new ArrayList<>(1) ;
    population.add(solution) ;

    new SolutionListOutput(population)
      .setSeparator("\t")
      .setVarFileOutputContext(new DefaultFileOutputContext("out/griewank/VAR.tsv"))
      .setFunFileOutputContext(new DefaultFileOutputContext("out/griewank/FUN.tsv"))
      .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    JMetalLogger.logger.info("Fitness: " + solution.getObjective(0)) ;
  }
}