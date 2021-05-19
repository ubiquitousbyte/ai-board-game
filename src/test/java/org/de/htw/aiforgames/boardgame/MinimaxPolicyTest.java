package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MinimaxPolicyTest {

    @Test
    public void test() {
        BoardGame game = new BoardGame();
        GamePolicy<BoardState, Move> policy = new AlphaBetaPolicy();
        BoardState currentState = game.startState();
        while (true) {
            if (game.isTerminal(currentState)) {
                int nextPlayer = game.getNextPlayer(currentState);
                currentState.setPlayer(nextPlayer);
                continue;
            }
            System.out.println(currentState.getUnmaskedPositions());
            GamePolicy.Decision<Move> move = policy.apply(game, currentState, 4);
            if (move.action == null) {
                break;
            }
            System.out.println(move);
            currentState = game.transition(currentState, move.action);
        }
    }
}
