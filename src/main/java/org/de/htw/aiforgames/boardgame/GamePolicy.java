package org.de.htw.aiforgames.boardgame;

import java.util.Arrays;

public interface GamePolicy<S, A> {

    class Decision<A> implements Comparable<Decision<A>> {
        int utility;
        A action;

        public Decision(int utility, A action) {
            this.utility = utility;
            this.action = action;
        }

        @Override
        public String toString() {
            return "Decision{" +
                    "utility=" + utility +
                    ", action=" + action +
                    '}';
        }

        @Override
        public int compareTo(Decision<A> o) { return Integer.compare(this.utility, o.utility); }
    }

    Decision<A> apply(Game<S, A> game, S state, int depth);
}
