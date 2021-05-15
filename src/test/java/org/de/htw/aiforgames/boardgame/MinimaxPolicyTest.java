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
        GamePolicy.Decision<Move> move = policy.apply(game, currentState);
        currentState = game.transition(currentState, move.action);
        move = policy.apply(game, currentState);
        currentState = game.transition(currentState, move.action);

    }
}
