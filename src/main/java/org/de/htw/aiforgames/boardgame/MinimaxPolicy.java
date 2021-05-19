package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.List;

public class MinimaxPolicy implements GamePolicy<BoardState, Move> {

    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state, int depth) {
        return minimax(game, state, depth, game.getPlayer(state));
    }

    private Decision<Move> minimax(Game<BoardState, Move> game, BoardState state, int depth, int maximizingPlayer) {
        if (game.isTerminal(state) || depth == 0) return new Decision<>(game.utility(state), null);
        List<Move> actions = game.getActions(state);
        int currentPlayer = game.getPlayer(state);
        if (currentPlayer == maximizingPlayer) {
            Decision<Move> max = new Decision<>(Integer.MIN_VALUE, null);
            for (Move action : actions) {
                Decision<Move> eval = minimax(game, game.transition(state, action), depth - 1, currentPlayer);
                if (eval.compareTo(max) > 0) {
                    max.utility = eval.utility;
                    max.action = action;
                }
            }
            return max;
        }
        else {
            Decision<Move> min = new Decision<>(Integer.MAX_VALUE, null);
            for (Move action : actions) {
                Decision<Move> eval = minimax(game, game.transition(state, action), depth - 1, maximizingPlayer);
                if (eval.compareTo(min) < 0) {
                    min.utility = eval.utility;
                    min.action = action;
                }
            }
            return min;
        }
    }
}
