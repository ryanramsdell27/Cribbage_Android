package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class CPUPlayerAVG extends CPUPlayer {
    Deck deck = new Deck();

    @Override
    public Card[] discard() {
        Iterator<ArrayList<Card>> comb_it = this.getHand().getCombinationIterator(4);
        int best_avg = -10000; //TODO define this better so it makes sense that this is unattainable (-52*2?)
        Card [] dis = new Card[this.getHand().size()-4];
        Card [] copy_list = new Card[4];
        while(comb_it.hasNext()){
            ArrayList<Card> test_set = comb_it.next();
            ArrayList<Card> temp_dis = new ArrayList<Card>(dis.length);
            for(Card c:this.getHand()){
                if(!test_set.contains(c)) temp_dis.add(c);
            }
            int penalty = 0;
            Iterator<Card> card_it = deck.iterator();
            int running_avg = 0;
            if(Hand.isTotal(temp_dis, 15)) penalty += 2;
            if(Hand.isDuplicate(temp_dis)) penalty += 2;
            if(!this.dealer) penalty *= -1;

            while(card_it.hasNext()){
                Card test_card = card_it.next();
                if(this.getHand().contains(test_card)) continue;

                Hand test_hand = new Hand();
                test_hand.add(test_set.toArray(copy_list));
                test_hand.add(test_card);
                test_hand.setStarter(test_card);

                int possible_score = test_hand.scoreHand();
                running_avg += possible_score + penalty;
            }
            if(running_avg > best_avg){
                for(int i = 0; i < temp_dis.size(); i++){
                    dis[i] = temp_dis.get(i);
                }
                best_avg = running_avg;
            }

        }
        super.discard(dis);
        return dis;
    }
}

