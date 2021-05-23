package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import org.de.htw.aiforgames.boardgame.game.BoardGame;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.policies.MinimaxPolicy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardGameTest {

    @Test
    public void testGameTransitionShouldMaskCorrectGridPositions() {
       /* BoardGame game = new BoardGame();
        MinimaxPolicy policy = new MinimaxPolicy();
        BoardState state = game.startState();
        Move decision = new Move(0, 1, 7, 15);
        state = game.transition(state, decision);
        var expectedUnmasked = Arrays.asList(2, 3, 5, 6, 8, 10, 11, 12, 13, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 26, 27, 28, 30, 32, 33, 34);
        var unmasked = state.getUnmaskedPositions();
        assertTrue(unmasked.containsAll(expectedUnmasked));*/
    }
}
