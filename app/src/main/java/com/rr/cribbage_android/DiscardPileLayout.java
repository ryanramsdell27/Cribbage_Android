package com.rr.cribbage_android;

import android.content.Context;
import android.os.ConditionVariable;
import android.util.AttributeSet;
import android.widget.Button;

public class DiscardPileLayout extends HandLayout{
    enum StackStyle {DISCARD, PEG};
    private StackStyle style;
    private ConditionVariable inputLock;
    private Button confirmButton;

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

    public void setConfirmButton(Button button){
        this.confirmButton = button;
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
    }

    @Override
    public void removeCard(final PlayingCardView card){
        super.removeCard(card);
        if(this.inputLock != null) this.inputLock.open();
    }

    public void showConfirmButton(boolean show){
        final int state;
        if(show) state = VISIBLE;
        else state = GONE;
        this.confirmButton.post(new Runnable() {
            @Override
            public void run() {
                confirmButton.setVisibility(state);
            }
        });
    }
}
