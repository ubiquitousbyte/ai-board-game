package org.de.htw.aiforgames.boardgame.evolution;

/**
 * An evaluator operating on game states of type S.
 * The key feature of this interface is the evaluation function which returns an approximation of the best possible move
 * @param <S> the game state type
 */
public interface Evaluator<S> {

    public void setCoefficients(double[] coefficients);

    public double[] features(S state);

    public double eval(double[] features);

    public double[] getCoefficients();
}
