package com.rr.cribbage_android;

import android.os.ConditionVariable;
import com.cribbage.CPUPlayer;
import com.cribbage.Card;
import com.cribbage.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class UIPlayer extends Player {
    HandLayout handLayout;
    HandLayout discardLayout;
    ConditionVariable uiLock;

    public UIPlayer(HandLayout handLayout, HandLayout discardLayout){
        this.handLayout = handLayout;
        this.discardLayout = discardLayout;
    }

    public void setUiLockVariable(ConditionVariable uiLock){
        this.uiLock = uiLock;
    }

    public void clearHands(){
        this.handLayout.post(new Runnable(){
            @Override
            public void run() {
                handLayout.removeAllCards();
                discardLayout.removeAllCards();
            }
        });
    }

    @Override
    public Card peg(ArrayList<Card> peg_pile) {
        int total = 0;
        for(Card c:peg_pile){
            total += c.getValue();
        }
        //TODO iterate over each card seeing what will return max points
        for(Card c:this.getPegHand()){
            if(c.getValue() + total <= 31) {
                this.getPegHand().remove(c);
                return c;
            }
        }
        return null;
    }


    class SendDiscardUpdateUI implements Runnable{
        private List<PlayingCardView> hand;
        public SendDiscardUpdateUI(List<PlayingCardView> hand){
            this.hand = hand;
        }
        @Override
        public void run() {
            handLayout.addHand(hand);
        }
    }
}
