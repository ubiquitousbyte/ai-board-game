package org.de.htw.aiforgames.boardgame.evolution;

import org.de.htw.aiforgames.boardgame.player.Player;

import java.util.List;
import java.util.Map;

public interface EvolutionStrategy {

    /**
     * Run a competition between the different player perceptions and return the winner
     * @param perceptions the perceptions/coefficients that will compete
     * @return the winner
     */
    public double[] compete(List<double[]> perceptions) throws InterruptedException;

    /**
     * Select the fittest perceptions in the population
     * Each perception is mapped to a fitness value that may be used in the selection process
     * @param population all of the perceptions
     * @return a subset of perceptions
     */
    public List<double[]> select(Map<double[], Integer> population, int limit);

    /**
     * Produce an offspring generation from the given parents
     * @param parents the parents
     * @return parent offspring
     */
    public List<double[]> produceOffspring(List<double[]> parents);

    /**
     * Evolves the generation
     * @param generation the generation
     * @return the offspring
     */
    public List<double[]> evolve(List<double[]> generation) throws InterruptedException;
}
