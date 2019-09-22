package com.cribbage;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A playable hand class. Stores a collection of cards with the ability to add and remove from the collection.
 * Maintains the starter card, and has scoring functionality
 * @author Ryan
 */
public class Hand implements Iterable<Card> {
    private final int max_hand_size = 7;
    private ArrayList<Card> hand;
    boolean is_crib;
    Card starter;

    /**
     * Constructor creates a new array of max size
     */
    public Hand(){
        this.hand = new ArrayList<>(max_hand_size);
        this.starter = null;
        this.is_crib = false;
    }

    /**
     * Creates an iterator for the list of card in the hand
     * @return Inherited iterator from the hand member
     */
    @Override
    public Iterator<Card> iterator() {
        return this.hand.iterator();
    }

    /**
     * Creates an iterator to go through elements of the power set of the hand
     * @return An iterator that enumerates the power set
     */
    Iterator<ArrayList<Card>> getPowerSetIterator(){
        PowerSetIterator<Card[]> psi = new PowerSetIterator(this.hand);
        return psi;
    }

    public Iterator<ArrayList<Card>> getCombinationIterator(int num_elements){
        CombinationIterator<Card[]> ci = new CombinationIterator(this.hand, num_elements);
        return ci;
    }

    /**
     * Finds the number of cards in this hand by looking at size of card list
     * @return The number of cards in the hand
     */
    int size(){
        return this.hand.size();
    }

    /**
     * Adds a card to this hand
     * @param c The Card object to add
     */
    public void add(Card c){
        this.hand.add(c);
    }

    /**
     * Adds several cards to this hand
     * @param cards The array of Cards
     */
    void add(Card [] cards){
        Collections.addAll(this.hand, cards);
    }

    /**
     * Checks if this hand contains a specific card
     * @param card The card to check if in this hand
     * @return True if this hand contains the card
     */
    boolean contains(Card card){
        return this.hand.contains(card);
    }
    /**
     * Removes a card from this hand
     * @param c The Card object to remove
     */
    void remove(Card c){
        this.hand.remove(c);
    }

    /**
     * Removes all cards from this hand
     */
    void clear(){
        this.hand.clear();
    }

    void setStarter(Card c){
        this.starter = c;
    }


    /**
     * Scores this hand and returns its value
     * TODO keep list(s) of scoring sets to display to user which parts scored
     * @return The value of this hand
     */
    int scoreHand(){
        int score = 0;
        Iterator<ArrayList<Card>> psi = this.getPowerSetIterator();
        while(psi.hasNext()){
            int old_score = score;
            ArrayList<Card> set = psi.next();

            if(isTotal(set,15)) score += 2;
            if(isDuplicate(set)) score += 2;
            // TODO investigate. Should count the first 3 cards of the set as a full run then adds on for the rest
            if(isRun(set)){
                int ss = set.size();
                if(ss == 3) score += 3;
                if(ss == 4) score -= 2; // [1,2,3,4] counts [1,2,3] + 3 and [2,3,4] +3 = 6 so we subtract 2 to get 4
                if(ss == 5) score += 0; // [1,2,3,4,5] counts 3 sets of 3 and 2 sets of 4; +3 +3 +3 -2 -2 = 5. Hence 0
            }
            if(isFlush(set)) {
                if (set.size() == 5 && this.is_crib) score += 5;
                else if (set.size() == 5 && !this.is_crib) score += 1;
                else score += 4;
            }
            if(isNob(set)) score++;

//            if(score > old_score) System.out.printf("%s +%d\n", set,score-old_score); //TESTING
        }
        return score;
    }

    /**
     * Checks if the set of cards' ranks add up to 15
     * @param set A list of cards
     * @return True if the sum of the cards add to 15
     */
    static boolean isTotal(ArrayList<Card> set, int total){
        if(set.size() == 0) return false;
        int sum = 0;
        for(Card c : set){
            sum += c.value;
        }
        return sum == total;
    }

    /**
     * Checks if a pair of cards are a duplicate (same rank)
     * @param set A set of cards
     * @return True if duplicate
     */
    static boolean isDuplicate(List<Card> set){
        if(set.size() != 2) return false;
        return set.get(0).rank == set.get(1).rank;
    }

    /**
     * Checks if a set of cards' ranks form a run at least 3 card long
     * @param set A list of cards
     * @return True if there is a run
     */
    static boolean isRun(List<Card> set){
        if(set.size() < 3) return false;
        Collections.sort(set);
        for(int i = 0; i < set.size()-1; i++){
            if(set.get(i).rank != set.get(i+1).rank-1) return false;
        }
        return true;
    }

    /**
     * Determine if the set is a flush. Takes into consideration that cribs can only score flushes of 5
     * Also checks that a flush of 4 does not include the starter.
     * TODO ensure that the starter is initialized/not null
     * @param set A list of cards
     * @return True if a valid flush exists in the hand
     */
    boolean isFlush(List<Card> set){
        if((!this.is_crib && set.size() < 4) || (this.is_crib && set.size() < 5)) return false;
        int base = set.get(0).suit;
        for(Card c : set){
            if(c.suit != base) return false;
        }
        if(set.size() == 4 && set.contains(this.starter)) return false;
        return true;
    }

    /**
     * Checks if the set is a jack (non-starter) and if it matches the suit of the starter
     * Assumes set is not null.
     * @param set
     * @return True if the set awards nob
     */
    boolean isNob(List<Card> set){
        if(set.size() != 1) return false;
        Card c = set.get(0);
        if(c == this.starter || c.rank != 11) return false;
        if(c.suit == this.starter.suit) return true;
        return false;
    }

}
