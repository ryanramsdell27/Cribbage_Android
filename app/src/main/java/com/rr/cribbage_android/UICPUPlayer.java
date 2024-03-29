package com.rr.cribbage_android;

import android.os.ConditionVariable;
import android.util.Log;
import android.widget.TextView;
import com.cribbage.CPUPlayer;
import com.cribbage.Card;
import com.cribbage.Hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UICPUPlayer extends UIPlayer {
    private final String TAG = "UICPUPlayer";
    CPUPlayer player;

    public UICPUPlayer(CPUPlayer player, HandLayout handLayout, HandLayout discardLayout, TextView scoreView){
        super(handLayout, discardLayout, scoreView);
        this.player = player;
    }

    @Override
    public Card[] discard() {
        List<Card> ccl_hand = player.getHand().getHand();
        Collections.sort(ccl_hand);
        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for(Card c:ccl_hand){
            PlayingCardView pcv = new PlayingCardView(handLayout.getContext());
            pcv.setCard(c);
            pcv.showCardFace(false);
            hand.add(pcv);
        }
        handLayout.post(new SendDiscardUpdateUI(hand));
        Card [] dis = this.player.discard();
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
//        Log.d(TAG, "total is " + total);
        this.updatePegCountView(total);
        Card pegged = this.player.peg(peg_pile);
        PlayingCardView peggedView = null;
        for(PlayingCardView pcv : this.handLayout.getHandList()){
            if(pcv.getCard() == pegged){
                peggedView = pcv;
            }
        }
        if(peggedView == null) return pegged;
        final PlayingCardView finalPeggedView = peggedView;
        this.handLayout.post(new Runnable() {
            @Override
            public void run() {
                finalPeggedView.performClick();
                finalPeggedView.showCardFace(true);
            }
        });
        return pegged;
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
        super.increaseScore(score);
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

}
