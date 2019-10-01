package com.rr.cribbage_android;

import android.os.ConditionVariable;
import android.util.Log;
import com.cribbage.CPUPlayer;
import com.cribbage.Card;
import com.cribbage.Hand;
import com.cribbage.Player;

import java.util.ArrayList;
import java.util.List;

public class UIInteractivePlayer extends UIPlayer {
    private final String TAG = "WrapperInteractPlayer";

    public UIInteractivePlayer(HandLayout handLayout, HandLayout discardLayout){
        super(handLayout, discardLayout);
    }

    @Override
    public Card[] discard() {
        List<Card> ccl_hand = this.getHand().getHand();
        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for (Card c : ccl_hand) {
            PlayingCardView pcv = new PlayingCardView(handLayout.getContext());
            pcv.setCard(c.getInt());
            pcv.showCardFace(true);
            hand.add(pcv);
        }
        handLayout.post(new SendDiscardUpdateUI(hand));

        List<PlayingCardView> hand_list = this.discardLayout.getHandList() ;
        while (hand_list == null || hand_list.size() < 2){
            uiLock.close();
            uiLock.block();
            hand_list = this.discardLayout.getHandList();
        }


        Card [] dis = new Card[hand_list.size()];
        for(PlayingCardView pcv : hand_list){
            dis[hand_list.indexOf(pcv)] = pcv.getCard();
        }

        final ArrayList<PlayingCardView> discardPile = new ArrayList<>(2);
        for(PlayingCardView pcv: hand){
            for(Card c: dis){
                if(pcv.getCard().getInt() == c.getInt()) discardPile.add(pcv);
            }
        }
        handLayout.post(new Runnable(){
            @Override
            public void run() {
                for(PlayingCardView pcv : discardPile){
                    pcv.performClick();
                }
            }
        });
        return dis;
    }

    @Override
    public Card peg(ArrayList<Card> peg_pile){
        int total = 0;
        for(Card c:peg_pile){
            total += c.getValue();
        }
        Log.d(TAG, "(player) total is " + total);
        for(PlayingCardView pcv:this.handLayout.getHandList()){
            pcv.setPeggable(pcv.getCard().getValue() + total <= 31);
        }

        // TODO make this work with a condition variable rather than a spin lock
        uiLock.close();
        uiLock.block();
//        while(this.discardLayout.getHandList() == null || this.discardLayout.getHandList().size() != 1){
//
//        }
        PlayingCardView pcv = this.discardLayout.getHandList().get(0);
        return pcv.getCard();
    }
}
