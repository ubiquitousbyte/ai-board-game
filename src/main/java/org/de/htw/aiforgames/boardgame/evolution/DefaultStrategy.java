package org.de.htw.aiforgames.boardgame.evolution;

import lenz.htw.blocks.Move;
import org.de.htw.aiforgames.boardgame.game.BoardGame;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.game.Game;
import org.de.htw.aiforgames.boardgame.player.BoardGamePlayer;
import org.de.htw.aiforgames.boardgame.player.Player;
import org.de.htw.aiforgames.boardgame.policies.AlphaBetaPolicy;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;
import org.de.htw.aiforgames.boardgame.utils.RunnableServer;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// We use the roulette wheel selection method and a random mutation for modifying the parent's genome to produce offspring
public class DefaultStrategy implements EvolutionStrategy, Runnable {

    private final List<double[]> currentGeneration;
    private static final int OFFSPRING_COUNT = 3;
    private static final int SURVIVOR_COUNT = 5;
    private static final int COMPETITION_COUNT = 20;
    private static final int GENERATION_COUNT = 10;
    private final String server;
    private final BufferedImage icon;

    public DefaultStrategy(String server, BufferedImage icon, List<double[]> firstGeneration) {
        this.server = server;
        // Just get some icon...
        this.icon = icon;
        this.currentGeneration = new ArrayList<>();
        this.currentGeneration.addAll(firstGeneration);
    }

    private static Player<BoardState, Move> makePlayer(String server, BufferedImage icon, double[] perception) {
        Evaluator<BoardState> eval = new LinearEvaluator();
        Game<BoardState, Move> game = new BoardGame(eval);
        GamePolicy<BoardState, Move> policy = new AlphaBetaPolicy();
        game.setPlayerPerception(perception);
        Player<BoardState, Move> player = new BoardGamePlayer(server, UUID.randomUUID().toString(), icon);
        player.setGame(game);
        player.setGamePolicy(policy);
        return player;
    }

    public static double[] randomCoefficients(int size) {
        double[] coefficients = new double[size];
        Random r = new Random();
        for(int i = 0; i < size; i++) {
            coefficients[i] = r.nextDouble();
        }
        return coefficients;
    }

    @Override
    public double[] compete(List<double[]> perceptions) throws InterruptedException {
        RunnableServer nature = new RunnableServer(10);
        Thread natureThread = new Thread(nature);
        natureThread.start();
        for (double[] perception : perceptions) {
            Player<BoardState, Move> player = makePlayer(server, icon, perception);
            Thread t = new Thread(player);
            t.start();
        }
        natureThread.join();
        int winner = nature.getWinner();
        return (winner > 0) ? perceptions.get(winner-1) : null;
    }

    /**
     * Select a subset of perceptions from the whole population.
     * Each perception is mapped to a fitness value that may be used in the selection process
     * @param population all of the perceptions.
     * @return a subset of perceptions
     */
    @Override
    public List<double[]> select(Map<double[], Integer> population, int limit) {
        return population
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<double[]> produceOffspring(List<double[]> parents) {
        List<double[]> offspring = new ArrayList<>();
        for (double[] parentPerception : parents) {
            for(int i = 0; i < OFFSPRING_COUNT; i++) {
                double[] childPerception = randomCoefficients(parentPerception.length);
                for (int j = 0; j < parentPerception.length; j++) {
                    childPerception[j] += parentPerception[j];
                }
                offspring.add(childPerception);
            }
        }
        return offspring;
    }

    /**
     * Evolves the generation
     * @param generation the generation
     * @return the offspring
     */
    public List<double[]> evolve(List<double[]> generation) throws InterruptedException {
        Map<double[], Integer> newGeneration = new HashMap<>();
        for (double[] perception : generation) {
            newGeneration.put(perception, 0);
        }
        for (int i = 0; i < COMPETITION_COUNT; i++) {
            List<double[]> competitors = select(newGeneration, newGeneration.size());
            Collections.shuffle(competitors);
            competitors = competitors.stream().limit(3).collect(Collectors.toList());
            double[] winner = compete(competitors);
            int winnerFitness = newGeneration.getOrDefault(winner, 0);
            newGeneration.replace(winner, winnerFitness + 1);
        }
        return select(newGeneration, SURVIVOR_COUNT);
    }

    @Override
    public void run() {
        List<double[]> fittest = currentGeneration;
        for (int i = 0; i < GENERATION_COUNT; i++) {
            try {
                fittest = evolve(fittest);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            List<double[]> offspring = produceOffspring(fittest);
            fittest.addAll(offspring);
        }
        try {
            fittest = evolve(fittest);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        try (FileWriter writer = new FileWriter("perceptions.txt", true)) {
            for (double[] perception : fittest) {
                writer.append(Arrays.toString(perception)).append(System.lineSeparator());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
