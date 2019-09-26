package com.rr.cribbage_android;

import android.os.ConditionVariable;
import android.util.Log;
import com.cribbage.CPUPlayer;
import com.cribbage.Card;
import com.cribbage.Hand;
import com.cribbage.Player;

import java.util.ArrayList;
import java.util.List;

public class WrapperInteractivePlayer extends CPUPlayer {
    private final String TAG = "WrapperInteractPlayer";
    HandLayout handLayout;
    HandLayout discardLayout;
    private ConditionVariable uiLock;

    public WrapperInteractivePlayer(HandLayout handLayout, HandLayout discardLayout){
        this.handLayout = handLayout;
        this.discardLayout = discardLayout;
    }

    public void setUiLockVariable(ConditionVariable uiLock){
        this.uiLock = uiLock;
    }

    @Override
    public Card[] discard() {
        List<Card> ccl_hand = this.getHand().getHand();
        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for(Card c:ccl_hand){
            PlayingCardView pcv = new PlayingCardView(handLayout.getContext());
            pcv.setCard(c.getInt());
            pcv.showCardFace(true);
            hand.add(pcv);
        }
        handLayout.post(new SendDiscardUpdateUI(hand));
        uiLock.block();

        List<PlayingCardView> hand_list = this.discardLayout.getHandList();
        Card [] dis = new Card[hand_list.size()];
        for(PlayingCardView pcv : hand_list){
            dis[hand_list.indexOf(pcv)] = pcv.getCard();
        }

        final ArrayList<PlayingCardView> discardPile = new ArrayList<>(2);
        for(PlayingCardView pcv: hand){
            for(Card c: dis){
                Log.d(TAG, pcv.getCard().toString() + " " + c.toString());
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

    public void clearHands(){
        this.handLayout.post(new Runnable(){
            @Override
            public void run() {
                handLayout.removeAllCards();
                discardLayout.removeAllCards();
            }
        });
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
