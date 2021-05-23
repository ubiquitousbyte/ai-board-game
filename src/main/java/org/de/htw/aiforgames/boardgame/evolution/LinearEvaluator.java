package org.de.htw.aiforgames.boardgame.evolution;

import org.de.htw.aiforgames.boardgame.game.BoardState;

import java.util.Arrays;

public class LinearEvaluator implements Evaluator<BoardState> {

    private static final int PLAYER_COUNT = 3;
    private static final int FEATURE_COUNT = 5;
    private static final int COEFFICIENT_COUNT = PLAYER_COUNT*FEATURE_COUNT;
    private final double[] coefficients = new double[COEFFICIENT_COUNT];

    @Override
    public void setCoefficients(double[] coefficients) {
        assert (coefficients.length == COEFFICIENT_COUNT);
        System.arraycopy(coefficients, 0, this.coefficients, 0, COEFFICIENT_COUNT);
    }

    @Override
    public double[] features(BoardState state) {
        double[] features = new double[COEFFICIENT_COUNT];
        for (int player = 0; player < PLAYER_COUNT; player++) {
            double[] playerFeatures = playerFeatures(state, player);
            System.arraycopy(playerFeatures, 0, features, player * FEATURE_COUNT, playerFeatures.length);
        }
        return features;
    }

    @Override
    public double eval(double[] features) {
        assert (features.length == coefficients.length);
        double score = 0f;
        for (int i = 0; i < features.length; i++) {
            score += coefficients[i]*features[i];
        }
        return score;
    }

    @Override
    public double[] getCoefficients() { return coefficients.clone(); }

    /**
     * Compute the number of free neighbours that the player's left token has
     * @param state the state
     * @param player the player
     * @return the number of free neighbours that the player's left token has
     */
    private long leftTokenNeighbourCount(BoardState state, int player) {
        return Arrays.stream(state.getLeftTokenNeighbours(player)).filter(t -> t != null && !t.masked()).count();
    }

    /**
     * Compute the number of free neighbours that the player's right token has
     * @param state the state
     * @param player the player
     * @return the number of free neighbours that the player's right token has
     */
    private long rightTokenNeighbourCount(BoardState state, int player) {
        return Arrays.stream(state.getRightTokenNeighbours(player)).filter(t -> t != null && !t.masked()).count();
    }

    /**
     * Extract the features of the given player
     * These currently are:
     *  1. The number of free neighbours that the player's left token has
     *  2. The number of free neighbours that the player's right token has
     *  3. The player's points
     *  4. The position of the player's left token
     *  5. The position of the player's right token
     * @param state the state
     * @param player the player
     * @return the player's feature vector
     */
    private double[] playerFeatures(BoardState state, int player) {
        int[] playerPositions = state.getPlayerPositions(player);
        int leftTokenPosition = playerPositions[0];
        int rightTokenPosition = playerPositions[1];
        return new double[] {
                leftTokenNeighbourCount(state, player),
                rightTokenNeighbourCount(state, player),
                state.getPlayerPoints(player),
                leftTokenPosition,
                rightTokenPosition
        };
    }
}
