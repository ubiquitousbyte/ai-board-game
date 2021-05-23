package org.de.htw.aiforgames.boardgame.policies;

import org.de.htw.aiforgames.boardgame.game.Game;

public interface GamePolicy<S, A> {

    /**
     * A decision represented as the utility that it brings and the action to be taken to yield the utility
     * @param <A> the action type
     */
    class Decision<A> implements Comparable<Decision<A>> {
        double utility;
        A action;

        public Decision(double utility, A action) {
            this.utility = utility;
            this.action = action;
        }

        public A getAction() { return action; }

        public double getUtility() { return utility; }

        @Override
        public String toString() {
            return "Decision{" +
                    "utility=" + utility +
                    ", action=" + action +
                    '}';
        }

        @Override
        public int compareTo(Decision<A> o) { return Double.compare(this.utility, o.utility); }
    }

    /**
     * Apply the game policy to the game at the given game state up to a certain depth
     * @param game the game
     * @param state the state to start from
     * @param depth the search depth
     * @return the decision that the game policy thinks is best
     */
    Decision<A> apply(Game<S, A> game, S state, int depth);
}
