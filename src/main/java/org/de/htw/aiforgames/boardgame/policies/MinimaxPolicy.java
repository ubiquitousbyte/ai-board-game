package org.de.htw.aiforgames.boardgame.policies;

import lenz.htw.blocks.Move;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.game.Game;

import java.util.List;

public class MinimaxPolicy implements GamePolicy<BoardState, Move> {

    /**
     * Apply the game policy to the game at the given game state up to a certain depth
     * @param game the game
     * @param state the state to start from
     * @param depth the search depth
     * @return the decision that the game policy thinks is best
     */
    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state, int depth) {
        return minimax(game, state, depth, game.getPlayer(state));
    }

    /**
     * The minimax game policy
     * @param game the game to apply the policy to
     * @param state the current game state
     * @param depth the search depth
     * @param maximizingPlayer the player that tries to maximize her utility
     * @return the decision
     */
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
