package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MinimaxPolicyTest {

    @Test
    public void test() {
        BoardGame game = new BoardGame();
        MinimaxPolicy policy = new MinimaxPolicy();
        BoardState currentState = game.startState();
        while (true) {
            System.out.println(currentState.getUnmaskedPositions());
            GamePolicy.Decision<Move> move = policy.apply(game, currentState, 3);
            System.out.println(move);
            currentState = game.transition(currentState, move.action);
            if (game.isTerminal(currentState)) {
                int nextPlayer = game.getNextPlayer(currentState);
                currentState.setPlayer(nextPlayer);
            }
        }
    }
}
