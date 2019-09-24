package com.rr.cribbage_android;

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
    public Card[] discard() {
        List<Card> ccl_hand = player.getHand().getHand();

        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for(Card c:ccl_hand){
            PlayingCardView pcv = new PlayingCardView(handLayout.getContext());
            pcv.setCard(c.getInt());
            pcv.showCardFace(true);
            hand.add(pcv);
        }
        handLayout.addHand(hand);

        return this.player.discard();
    }
}
