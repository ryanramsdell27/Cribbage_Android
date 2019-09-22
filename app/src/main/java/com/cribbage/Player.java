package com.cribbage;

import java.util.ArrayList;

/**
 * Player objects used for making play decisions
 */
public abstract class Player {
    int score;
    boolean dealer;
    Hand hand;
    Hand peg;

    abstract Card peg(ArrayList<Card> peg_pile);
    abstract Card [] discard();

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
    void setPeg(){
        this.peg = new Hand();
        for(Card c : this.hand){
            this.peg.add(c);
        }
    }

    /**
     * Check if this player has cards in their peg hand that can be pegged
     * while keeping the score at or under 31
     * @param peg_pile The current stack of card
     * @return True if this player has a card that can be pegged
     */
    boolean canPeg(ArrayList<Card> peg_pile){
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
    void increaseScore(int score){
        this.score += score;
    }

    /**
     * Query the score value
     * @return The score of the Player
     */
    int getScore(){
        return this.score;
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

    void setDealer(boolean b){
        this.dealer = b;
    }
}
