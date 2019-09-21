package com.rr.cribbage_android;

import android.content.Context;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import java.util.ArrayList;
import java.util.List;

public class HandLayout extends ConstraintLayout {
    private String TAG = "Hand Layout";

    private List<PlayingCardView> hand_list;

    public HandLayout(Context context) {
        super(context);
        setUp(context);
    }
    public HandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
//        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mInflater.inflate(R.layout.hand_layout, this, true);
    }

    public void addCard(PlayingCardView card){
        if(this.hand_list == null) this.hand_list = new ArrayList<>();
        this.hand_list.add(card);
        this.applyHand();
    }

    public void removeCard(PlayingCardView card){
        this.hand_list.remove(card);
        this.applyHand();
    }

    public void addHand(List<PlayingCardView> hand){
        this.hand_list = hand;
        applyHand();
    }

    public void applyHand(){
        if(this.hand_list == null){
            Log.e(TAG, "Hand array is null");
            return;
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(this);

        PlayingCardView prev = null;
        for(int i = 0; i < this.hand_list.size(); i++){
            boolean is_last = i == this.hand_list.size()-1;
            PlayingCardView pcv = new PlayingCardView(this.getContext());
            pcv.setCard(i);

            pcv.setId(View.generateViewId());
            set.constrainHeight(pcv.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainWidth(pcv.getId(), ConstraintSet.WRAP_CONTENT);
            pcv.setOnClickListener(new CardClickListener());
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
            this.addView(pcv);
        }
        set.applyTo(this);
    }

    static class CardClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            PlayingCardView pcv = (PlayingCardView)v;
            TransitionManager.beginDelayedTransition((ViewGroup) v.getRootView());
            pcv.toggleSelected();
        }
    }


}
