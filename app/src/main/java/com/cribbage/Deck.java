package com.cribbage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Iterable {
    ArrayList<Card> deck;
    int top;
    final static Random rng = new Random();

    /**
     * Creates a standard 52 card French deck
     */
    Deck(){
        top = 0;
        deck = new ArrayList<Card>(52);
        for(int init = 0; init < 52; init++){
            deck.add(new Card(init));
        }
    }

    /**
     * Shuffles this deck using the static random number generator
     */
    void shuffle(){
        this.top = 0;
        Collections.shuffle(deck, rng);
    }

    /**
     * Selects the top card(s) from this deck to return and moves deck pointer
     * @param num The number of card to deal, positive integer less than size of this deck
     * @return An array of the dealt cards, null if invalid input
     */
    Card [] deal(int num){
        if(this.top + num > deck.size() || num < 0){
            return null;
        }
        Card [] ret = new Card[num];
        for(int i = this.top; i < this.top + num; i++ ){
            ret[i-this.top] = deck.get(i);
        }
        this.top += num;
        return ret;
    }

    /**
     * Produces an iterator to go over the implemented list of cards in this deck.
     * @return A <code>Card</code> iterator over a standard deck of cards
     */
    @Override
    public Iterator<Card> iterator() {
        return this.deck.iterator();
    }
}
