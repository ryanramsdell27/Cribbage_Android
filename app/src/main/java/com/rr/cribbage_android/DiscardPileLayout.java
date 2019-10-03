package com.rr.cribbage_android;

import android.content.Context;
import android.os.ConditionVariable;
import android.util.AttributeSet;

public class DiscardPileLayout extends HandLayout{
    enum StackStyle {DISCARD, PEG};
    private StackStyle style;
    private ConditionVariable inputLock;

    public DiscardPileLayout(Context context) {
        super(context);
        setUp();
    }

    public DiscardPileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DiscardPileLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp(){
        this.style = StackStyle.DISCARD;
    }

    public void setStackStyle(StackStyle style){
        this.style = style;
    }

    public void setInputLock(ConditionVariable inputLock){
        this.inputLock = inputLock;
    }

    public boolean canAcceptCard(PlayingCardView pcv){
        switch(this.style){
            case PEG:
                if( this.getId() == R.id.DiscardPileOpponent) return true;
                boolean ret = pcv.getPeggable();
                if(ret) this.setLastAdded(pcv);
                return ret;

            case DISCARD:
                if(this.getHandList() == null) return true;
                return this.getHandList().size() < 2;
        }
        return true;
    }

    @Override
    public void addCard(final PlayingCardView card){
        super.addCard(card);
        if( this.inputLock != null) this.inputLock.open();
//
//        if(this.getHandList()== null || this.getHandList().size() < 2){
//            super.addCard(card);
//        }
//        else{
//            card.post(new Runnable(){
//                @Override
//                public void run(){
//                    card.performClick();
//                }
//            });
//
//        }
    }
}
