package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import org.de.htw.aiforgames.boardgame.game.BoardGame;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.policies.AlphaBetaPolicy;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;
import org.junit.Test;

public class MinimaxPolicyTest {

    @Test
    public void test() {
       /* BoardGame game = new BoardGame();
        GamePolicy<BoardState, Move> policy = new AlphaBetaPolicy();
        BoardState currentState = game.startState();
        while (true) {
            if (game.isTerminal(currentState)) {
                int nextPlayer = game.getNextPlayer(currentState);
                currentState.setPlayer(nextPlayer);
                continue;
            }
            System.out.println(currentState.getUnmaskedPositions());
            GamePolicy.Decision<Move> move = policy.apply(game, currentState, 3);
            if (move.getAction() == null) {
                break;
            }
            System.out.println(move);
            currentState = game.transition(currentState, move.getAction());
        }*/
    }
}
