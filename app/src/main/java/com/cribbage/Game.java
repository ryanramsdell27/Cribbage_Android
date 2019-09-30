package com.cribbage;

/**
 * A basic interface for driving a game.
 * Assumes that a game can be broken into atomic steps which
 * can run repeatedly until the game is done
 *
 * @author Ryan
 */
public interface Game {

    /**
     * Steps a single round/hand in this game
     * @return true if done, false if can continue
     */
    boolean step();

    /**
     * Determines if this game has finished
     * @return Whether or not the game has finished
     */
    boolean isDone();

    /**
     * Returns an array holding the current scores
     * @return int array of scores
     */
    int [] getScore();

}
