package com.rr.cribbage_android;

import android.content.Context;
import android.util.AttributeSet;

public class DiscardPileLayout extends HandLayout{
    public DiscardPileLayout(Context context) {
        super(context);
    }

    public DiscardPileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscardPileLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addCard(final PlayingCardView card){
        if(this.getHand_list()== null || this.getHand_list().size() < 2){
            super.addCard(card);
        }
        else{
            card.post(new Runnable(){
                @Override
                public void run(){
                    card.performClick();
                }
            });

        }
    }
}
