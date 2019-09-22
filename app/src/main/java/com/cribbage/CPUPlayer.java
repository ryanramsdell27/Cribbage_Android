package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class CPUPlayer extends Player {
    Deck deck = new Deck();

    @Override
    public Card peg(ArrayList<Card> peg_pile) {
        int total = 0;
        for(Card c:peg_pile){
            total += c.value;
        }
        //TODO iterate over each card seeing what will return max points
        for(Card c:this.peg){
            if(c.value + total <= 31) {
                this.peg.remove(c);
                return c;
            }
        }
        return null;
    }
}
