package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.Arrays;
import java.util.List;

public class MinimaxPolicy implements GamePolicy<BoardState, Move> {

    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state, int depth) {
        //return alphaBeta(game, state, depth, Arrays.stream(game.utility(state)).sum());
        //return maxn(game, state, depth);
        return null;
    }

    private Decision<Move> minimax(Game<BoardState, Move> game, BoardState state, int depth) {
        if (game.isTerminal(state) || depth == 0) return new Decision<>(game.utility(state), null);
        int player = game.getPlayer(state);

    }
    /**
     * The vanilla minimax approach extended to a non-cooperative perfect information multiplayer environment.
     * The scientific literature refers to this approach as the maxn algorithm.
     *
     * @param game the game
     * @param state the state of the board
     * @param depth the maximum depth to traverse to
     * @return the best decision for the current player
     */
    private Decision<Move> maxn(Game<BoardState, Move> game, BoardState state, int depth) {
        if (game.isTerminal(state) || depth == 0) return new Decision<>(game.utility(state), null);
        int player = game.getPlayer(state);
        Decision<Move> best = new Decision<>(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}, null);
        for (Move action : game.getActions(state)) {
            Decision<Move> lookup = maxn(game, game.transition(state, action), depth-1);
            if (best.utilities[player] < lookup.utilities[player]) {
                best.utilities = lookup.utilities;
                best.action = action;
            }
        }
        return best;
    }

    private Decision<Move> alphaBeta(Game<BoardState, Move> game, BoardState state, int depth, int bound) {
        if (game.isTerminal(state) || depth == 0) return new Decision<>(game.utility(state), null);
        List<Move> actions = game.getActions(state);
        int player = game.getPlayer(state);
        Decision<Move> best = alphaBeta(game, game.transition(state, actions.get(0)), depth-1, bound);
        best.action = actions.get(0);
        for(int i = 1; i < actions.size(); i++) {
            if (best.utilities[player] >= bound) {
                return best;
            }
            Decision<Move> current = alphaBeta(game, game.transition(state, actions.get(i)), depth-1, bound - best.utilities[player]);
            if (current.utilities[player] > best.utilities[player]) {
                best.utilities = current.utilities;
                best.action = actions.get(i);
            }
        }
        return best;
    }

}
