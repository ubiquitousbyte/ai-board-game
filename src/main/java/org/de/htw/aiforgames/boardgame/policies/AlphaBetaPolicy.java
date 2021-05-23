package org.de.htw.aiforgames.boardgame.policies;

import lenz.htw.blocks.Move;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.game.Game;

import java.util.List;

public class AlphaBetaPolicy implements GamePolicy<BoardState, Move> {

    /**
     * Apply the game policy to the game at the given game state up to a certain depth
     * @param game the game
     * @param state the state to start from
     * @param depth the search depth
     * @return the decision that the game policy thinks is best
     */
    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state, int depth) {
        return alphaBeta(game, state, depth, game.getPlayer(state), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Minimax game policy with alpha beta pruning
     * @param game the game
     * @param state the initial state
     * @param depth the search depth
     * @param maximizingPlayer the current player that attempts to maximize her utility
     * @param alpha the best choice (highest value) along the path for the maximizing player
     * @param beta the best choice (lowest value) along the path for the minimizing player
     * @return the decision
     */
    private Decision<Move> alphaBeta(Game<BoardState, Move> game,
                                     BoardState state,
                                     int depth,
                                     int maximizingPlayer,
                                     double alpha,
                                     double beta) {
        if (game.isTerminal(state) || depth == 0) return new Decision<>(game.utility(state), null);
        List<Move> actions = game.getActions(state);
        int currentPlayer = game.getPlayer(state);
        if (currentPlayer == maximizingPlayer) {
            Decision<Move> max = new Decision<>(Integer.MIN_VALUE, null);
            for (Move action : actions) {
                Decision<Move> eval = alphaBeta(game, game.transition(state, action), depth - 1, game.getNextPlayer(state), alpha, beta);
                if (eval.compareTo(max) > 0) {
                    max.utility = eval.utility;
                    max.action = action;
                }
                if (eval.utility > alpha) {
                    alpha = eval.utility;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return max;
        }
        else {
            Decision<Move> min = new Decision<>(Integer.MAX_VALUE, null);
            for (Move action : actions) {
                Decision<Move> eval = alphaBeta(game, game.transition(state, action), depth - 1, game.getNextPlayer(state), alpha, beta);
                if (eval.compareTo(min) < 0) {
                    min.utility = eval.utility;
                    min.action = action;
                }
                if (eval.utility < beta) {
                    beta = eval.utility;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return min;
        }
    }
}