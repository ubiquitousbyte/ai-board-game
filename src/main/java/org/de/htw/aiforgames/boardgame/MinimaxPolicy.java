package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.ArrayList;
import java.util.List;

public class MinimaxPolicy implements GamePolicy<BoardState, Move> {

    @Override
    public Decision<Move> apply(Game<BoardState, Move> game, BoardState state) {
        if (game.isTerminal(state)) return new Decision<>(game.utility(state), null);
        List<Move> actions = game.getActions(state);
        List<Decision<Move>> decisions = new ArrayList<>(actions.size());
        for(int i = 0; i < actions.size(); i++) {
            decisions.add(i, apply(game, game.transition(state, actions.get(i))));
        }
        int player = game.getPlayer(state);
        Decision<Move> best = decisions.get(0);
        for(int i = 1; i < actions.size(); i++) {
            if (best.utilities[player] < decisions.get(i).utilities[player]) {
                best = decisions.get(i);
                best.action = actions.get(i);
            }
        }
        return best;
    }
}
