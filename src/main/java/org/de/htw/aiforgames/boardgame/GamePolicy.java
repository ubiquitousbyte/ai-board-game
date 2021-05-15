package org.de.htw.aiforgames.boardgame;

import java.util.Arrays;

public interface GamePolicy<S, A> {

    class Decision<A> {
        int[] utilities;
        A action;

        public Decision(int[] utilities, A action) {
            this.utilities = utilities;
            this.action = action;
        }

        @Override
        public String toString() {
            return "Decision{" +
                    "utilities=" + Arrays.toString(utilities) +
                    ", action=" + action +
                    '}';
        }
    }

    Decision<A> apply(Game<S, A> game, S state);
}
