package com.rr.cribbage_android;

import android.app.Activity;
import android.util.Log;
import com.cribbage.CPUPlayer;
import com.cribbage.Card;
import com.cribbage.Hand;
import com.cribbage.Player;

import java.util.ArrayList;
import java.util.List;

public class WrapperCPUPlayer extends CPUPlayer {
    CPUPlayer player;
    HandLayout handLayout;

    public WrapperCPUPlayer(CPUPlayer player, HandLayout handLayout){
        this.player = player;
        this.handLayout = handLayout;
    }

    @Override
    public Card[] discard() {
        ((Activity) handLayout.getContext()).runOnUiThread(new sendDiscardUiUpdates());
        return this.player.discard();
    }

    @Override
    public Card peg(ArrayList<Card> peg_pile){
        return this.player.peg(peg_pile);
    }

    @Override
    public int getScore(){
        return this.player.getScore();
    }
    @Override
    public void setScore(int score){
        this.player.setScore(score);
    }
    @Override
    public Hand getHand(){
        return this.player.getHand();
    }
    @Override
    public Hand getPegHand(){
        return this.player.getPegHand();
    }
    @Override
    public boolean canPeg(ArrayList<Card> peg_pile){
        return this.player.canPeg(peg_pile);
    }
    @Override
    public void increaseScore(int score){
        this.player.increaseScore(score);
    }
    @Override
    public String toString() {
        return this.player.toString();
    }
    @Override
    public void setDealer(boolean b){
        this.player.setDealer(b);
    }
    @Override
    public void setPeg(){
        this.player.setPeg();
    }

    class sendDiscardUiUpdates implements Runnable{

        @Override
        public void run() {
            List<Card> ccl_hand = player.getHand().getHand();
            ArrayList<PlayingCardView> hand = new ArrayList<>();
            for(Card c:ccl_hand){
                PlayingCardView pcv = new PlayingCardView(handLayout.getContext());
                pcv.setCard(c.getInt());
                pcv.showCardFace(true);
                hand.add(pcv);
            }
            handLayout.addHand(hand);
        }
    }
}
