package com.cribbage;

/**
 * Representation of a card from a standard 52 card French deck.
 * @author Ryan
 */
public class Card implements Comparable<Card> {
    int suit;
    int rank;
    int value;

    /**
     * Creates a new card. There is a bijective mapping between Z/52 and cards in a deck.
     * @param init The int representation of this card
     */
    public Card(int init){
        this.suit = init % 4;
        this.rank = init % 13 + 1;
        if(this.rank > 10) this.value = 10;
        else this.value = rank;
    }

    /**
     * Builds a string showing rank and suit of this card
     * @return String representation of rank and suit
     */
    @Override
    public String toString() {
        String suit_str = null;
        switch(this.suit){
            case 0: suit_str = Character.toString((char)9825); break; // heart
            case 1: suit_str = Character.toString((char)9826); break; // diamond
            case 2: suit_str = Character.toString((char)9827); break; // clubs
            case 3: suit_str = Character.toString((char)9824); break; // spades
            default: System.err.print("Bad suit");
        }
        String rank_str;
        switch(this.rank){
            case 1 : rank_str = "A"; break;
            case 11: rank_str = "J"; break;
            case 12: rank_str = "Q"; break;
            case 13: rank_str = "K"; break;
            default: rank_str = Integer.toString(this.rank);
        }
        return rank_str + suit_str;
    }

    @Override
    public int compareTo(Card o) {
        return this.rank - o.rank;
    }
}
