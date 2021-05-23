package org.de.htw.aiforgames.boardgame.utils;

import lenz.htw.blocks.Server;

public class RunnableServer implements Runnable {

    private int winner;
    private final int timePerMove;

    public RunnableServer(int timePerMove) {
        this.timePerMove = timePerMove;
    }

    @Override
    public void run() {
        winner = Server.runOnceAndReturnTheWinner(timePerMove);
    }

    public int getWinner() { return winner; }
}
