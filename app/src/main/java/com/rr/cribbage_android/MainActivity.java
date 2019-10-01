package com.rr.cribbage_android;

import android.app.Activity;
import android.content.Context;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.Toolbar;
import com.cribbage.*;

import java.util.Arrays;

public class MainActivity extends Activity {
    private final String TAG = "Main activity";
    ConditionVariable inputLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputLock = new ConditionVariable();

        Window w = getWindow(); w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

//        CPUPlayerAVG p1 = new CPUPlayerAVG();
        HandLayout handLayoutOpponent = findViewById(R.id.HandLayoutOpponent);
        HandLayout discardPileOpponent = findViewById(R.id.DiscardPileOpponent);

        HandLayout handLayoutPlayer = findViewById(R.id.HandLayoutPlayer);
        HandLayout discardPilePlayer = findViewById(R.id.DiscardPilePlayer);

        UICPUPlayer p1 = new UICPUPlayer(new CPUPlayerAVG(), handLayoutOpponent, discardPileOpponent);
        //WrapperCPUPlayer p2 = new WrapperCPUPlayer(new CPUPlayerAVG(), handLayoutOpponent);
        UIInteractivePlayer p2 = new UIInteractivePlayer(handLayoutPlayer, discardPilePlayer);
        Cribbage game = new Cribbage(p1, p2, 1);

        RunGameRunnable rgr = new RunGameRunnable(game, p1, p2, this);
        new Thread(rgr).start();

    }

    public void openInputLock(View v){
        inputLock.open();
    }

    class RunGameRunnable implements Runnable{
        private Cribbage game;
        private UIPlayer p1;
        private UIPlayer p2;
        private Activity mainActivity;
        private HandLayout handLayoutPlayer, handLayoutOpponent;
        private DiscardPileLayout discardPilePlayer, discardPileOpponent;

        RunGameRunnable(Cribbage game, UIPlayer p1, UIPlayer p2, Activity mainActivity){
            this.game = game;
            p1.setUiLockVariable(inputLock);
            p2.setUiLockVariable(inputLock);
            this.p1 = p1;
            this.p2 = p2;
            this.mainActivity = mainActivity;
            this.handLayoutPlayer    = mainActivity.findViewById(R.id.HandLayoutPlayer);
            this.handLayoutOpponent  = mainActivity.findViewById(R.id.HandLayoutOpponent);
            this.discardPilePlayer   = mainActivity.findViewById(R.id.DiscardPilePlayer);
            this.discardPileOpponent = mainActivity.findViewById(R.id.DiscardPileOpponent);

            this.discardPilePlayer.setInputLock(inputLock);
        }

        @Override
        public void run() {
            while(!this.game.isDone()){
                p1.clearHands();
                p2.clearHands();

                this.game.dealAndDiscard();
                // Move discards to crib pile
                // TODO implement this
                this.handLayoutOpponent.post(new Runnable(){
                    @Override
                    public void run() {
                        discardPileOpponent.removeAllCards();
                        discardPilePlayer.removeAllCards();
                    }
                });


                inputLock.close();
                this.discardPileOpponent.setStackStyle(DiscardPileLayout.StackStyle.PEG);
                this.discardPilePlayer.setStackStyle(DiscardPileLayout.StackStyle.PEG);
                this.game.peg();
                // return pegging card to hands
                // TODO implement this
                // switch discard piles back to discard style
                this.discardPileOpponent.setStackStyle(DiscardPileLayout.StackStyle.DISCARD);
                this.discardPilePlayer.setStackStyle(DiscardPileLayout.StackStyle.DISCARD);
                inputLock.block();
                this.game.scorePlayers();

                Log.d(TAG, Arrays.toString(this.game.getScore()));
            }
        }
    }

}
