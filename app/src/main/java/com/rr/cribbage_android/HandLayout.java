package com.rr.cribbage_android;

import android.content.Context;
import android.content.res.TypedArray;
import android.nfc.Tag;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.List;

public class HandLayout extends ConstraintLayout {
    private String TAG = "Hand Layout";

    private List<PlayingCardView> hand_list;
    private int destination = 0;

    public HandLayout(Context context) {
        super(context);
        setUp(context);
    }
    public HandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HandLayout, 0 ,0);
        try{
            this.destination = a.getResourceId(R.styleable.HandLayout_Destination, R.id.DiscardPilePlayer);
        } finally {
            a.recycle();
        }

        setUp(context);
    }
    public HandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    /**
     * Called from the constructors so things are the same
     */
    private void setUp(Context context){
        if(this.destination == 0) this.destination = R.id.DiscardPilePlayer;
    }

    public void addCard(PlayingCardView card){
        if(this.hand_list == null) this.hand_list = new ArrayList<>();
        this.hand_list.add(card);
        this.applyHand();
    }

    public void removeCard(PlayingCardView card){
        this.hand_list.remove(card);
        this.removeView(card);
        this.applyHand();
    }

    public void removeAllCards(){
        this.hand_list = null;
        this.applyHand();
    }

    public void addHand(List<PlayingCardView> hand){
        this.removeAllViews();//for(PlayingCardView pcv : this.hand_list) this.removeView(pcv);
        this.hand_list = hand;
        applyHand();
    }

    public void applyHand(){
        if(this.hand_list == null){
            this.removeAllViews();
            return;
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(this);

        PlayingCardView prev = null;
        for(int i = 0; i < this.hand_list.size(); i++){
            boolean is_last = i == this.hand_list.size()-1;
            PlayingCardView pcv = this.hand_list.get(i);

            if(pcv.getId() == View.NO_ID) pcv.setId(View.generateViewId());
            set.clear(pcv.getId());

            set.constrainHeight(pcv.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainWidth(pcv.getId(), ConstraintSet.WRAP_CONTENT);
            if(!pcv.hasOnClickListeners()){
                pcv.setOnClickListener(new CardClickListener ( this, (HandLayout)(getRootView().findViewById(this.destination))));
            }

            /* Connect to previous card or edge */
            if(prev == null){
                set.connect(pcv.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START);
                set.setHorizontalBias(pcv.getId(), 0);
            }
            else{
                set.connect(prev.getId(), ConstraintSet.END, pcv.getId(), ConstraintSet.END);
                set.connect(pcv.getId(), ConstraintSet.START, prev.getId(), ConstraintSet.START);
                if(is_last){
                    set.connect(pcv.getId(), ConstraintSet.END, this.getId(), ConstraintSet.END);
                    set.setHorizontalBias(pcv.getId(), 1);

                }
            }
            prev = pcv;

            ViewGroup pcv_parent = ((ViewGroup)pcv.getParent());
            if(pcv_parent != null) pcv_parent.removeView(pcv);
            this.addView(pcv);
        }
        set.applyTo(this);
    }

    public List<PlayingCardView> getHandList() {
        return this.hand_list;
    }

    static class CardClickListener implements OnClickListener {
        private HandLayout destination, source;

        public CardClickListener(HandLayout source, HandLayout destination){
            super();
            this.destination = destination;
            this.source = source;
        }
        @Override
        public void onClick(View v) {
            Log.d("Card click listener", this.source.getId() + "-->" + this.destination.getId());
            PlayingCardView pcv = (PlayingCardView)v;
            TransitionManager.beginDelayedTransition((ViewGroup) v.getRootView());
            pcv.toggleFaceUp();
            this.source.removeCard(pcv);
            this.destination.addCard(pcv);
            HandLayout tmp = this.source;
            this.source = this.destination;
            this.destination = tmp;
        }
    }

}
