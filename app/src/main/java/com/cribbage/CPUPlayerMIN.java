package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class CPUPlayerMIN extends CPUPlayer {
    Deck deck = new Deck();

    @Override
    public Card[] discard() {
        Iterator<ArrayList<Card>> comb_it = this.hand.getCombinationIterator(4);
        int min_guaranteed = -10000; //TODO define this better so it makes sense that this is unattainable (-52*2?)
        Card [] dis = new Card[this.hand.size()-4];
        Card [] copy_list = new Card[4];
        while(comb_it.hasNext()){
            ArrayList<Card> test_set = comb_it.next();
            ArrayList<Card> temp_dis = new ArrayList<Card>(dis.length);
            for(Card c:this.hand){
                if(!test_set.contains(c)) temp_dis.add(c);
            }
            int penalty = 0;
            Iterator<Card> card_it = deck.iterator();
            int running_min = 100;
            if(Hand.isTotal(temp_dis, 15)) penalty += 2;
            if(Hand.isDuplicate(temp_dis)) penalty += 2;
            if(!this.dealer) penalty *= -1;

            int possible_score = -1000;
            while(card_it.hasNext()){
                Card test_card = card_it.next();
                if(this.hand.contains(test_card)) continue;

                Hand test_hand = new Hand();
                test_hand.add(test_set.toArray(copy_list));
                test_hand.add(test_card);
                test_hand.setStarter(test_card);

                possible_score = test_hand.scoreHand() + penalty;
                if(possible_score < running_min) running_min = possible_score;
            }
            if(possible_score > min_guaranteed){
                for(int i = 0; i < temp_dis.size(); i++){
                    dis[i] = temp_dis.get(i);
                }
                min_guaranteed = possible_score;
            }

        }
        super.discard(dis);
        return dis;
    }
}

