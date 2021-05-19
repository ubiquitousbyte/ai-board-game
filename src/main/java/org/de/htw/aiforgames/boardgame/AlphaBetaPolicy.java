package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.List;

public class AlphaBetaPolicy implements GamePolicy<BoardState, Move> {

    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state, int depth) {
        return alphaBeta(game, state, depth, game.getPlayer(state), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private Decision<Move> alphaBeta(Game<BoardState, Move> game,
                                     BoardState state,
                                     int depth,
                                     int maximizingPlayer,
                                     int alpha,
                                     int beta) {
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
