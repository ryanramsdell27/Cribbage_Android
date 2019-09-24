package com.cribbage;

import java.util.ArrayList;

/**
 * Player objects used for making play decisions
 */
public abstract class Player {
    private int score;
    boolean dealer;
    private Hand hand;
    private Hand peg;

    public abstract Card peg(ArrayList<Card> peg_pile);
    public abstract Card [] discard();

    Card[] discard(Card [] cards){
        Card [] ret = new Card[cards.length];
        int index = 0;
        this.dealer = false;
        for(Card c :cards){
            ret[index] = c;
            this.hand.remove(c);
        }
        return ret;
    }

    /**
     * Constructor that creates hand
     */
    public Player(){
        this.hand = new Hand();
    }

    /**
     * Sets up this players pegging hand from the selected cards
     */
    public void setPeg(){
        this.peg = new Hand();
        for(Card c : this.hand){
            this.peg.add(c);
        }
    }

    /**
     * Returns the cards that can still be pegged
     * @return The peg hand
     */
    public Hand getPegHand(){
        return this.peg;
    }

    /**
     * Check if this player has cards in their peg hand that can be pegged
     * while keeping the score at or under 31
     * @param peg_pile The current stack of card
     * @return True if this player has a card that can be pegged
     */
    public boolean canPeg(ArrayList<Card> peg_pile){
        int sum = 0;
        for(Card c:peg_pile){
            sum += c.value;
        }
        boolean ret = false;
        for(Card c: this.peg){
            ret |= sum + c.value <= 31;
        }
        return ret;
    }

    /**
     * Increases the score of this player by the specified amount
     * @param score Amount to increase score by
     */
    public void increaseScore(int score){
        this.score += score;
    }

    /**
     * Query the score value
     * @return The score of the Player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Sets the score of the player
     * @param score The value to set the score to
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Makes this player's representation as its current <code>Hand</code>
     * @return String displaying the current hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card c : this.hand){
            sb.append(c.toString());
            sb.append(" ");
        }
        return sb.toString();
    }

    public void setDealer(boolean b){
        this.dealer = b;
    }

    /**
     * Returns the hand
     * @return the hand object
     */
    public Hand getHand() {
        return hand;
    }
}
