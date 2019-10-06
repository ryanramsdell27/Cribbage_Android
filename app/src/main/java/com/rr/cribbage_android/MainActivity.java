package com.rr.cribbage_android;

import android.app.Activity;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.cribbage.CPUPlayerAVG;
import com.cribbage.Cribbage;

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

//        CPUPlayerAVG p1 = new CPUPlayerAVG();
        HandLayout handLayoutOpponent = findViewById(R.id.HandLayoutOpponent);
        HandLayout discardPileOpponent = findViewById(R.id.DiscardPileOpponent);
        TextView scoreViewOpponent = findViewById(R.id.ScoreOpponent);

        HandLayout handLayoutPlayer = findViewById(R.id.HandLayoutPlayer);
        HandLayout discardPilePlayer = findViewById(R.id.DiscardPilePlayer);
        TextView scoreViewPlayer = findViewById(R.id.ScorePlayer);

        UICPUPlayer p1 = new UICPUPlayer(new CPUPlayerAVG(), handLayoutOpponent, discardPileOpponent, scoreViewOpponent);
        //WrapperCPUPlayer p2 = new WrapperCPUPlayer(new CPUPlayerAVG(), handLayoutOpponent);
        UIInteractivePlayer p2 = new UIInteractivePlayer(handLayoutPlayer, discardPilePlayer, scoreViewPlayer);
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
        private PlayingCardView starterView;
        private TextView cribPointer;

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
            this.starterView = mainActivity.findViewById(R.id.StarterView);
            this.cribPointer = mainActivity.findViewById(R.id.CribPointer);

            this.discardPilePlayer.setInputLock(inputLock);
            this.discardPilePlayer.setConfirmButton((Button) mainActivity.findViewById(R.id.confirmButton));
        }

        @Override
        public void run() {
            while(!this.game.isDone()){
                p1.clearHands();
                p2.clearHands();
                this.starterView.showCardFace(false);
                if(p1 == this.game.getDealer()) this.cribPointer.setText("Opponent's crib");
                else this.cribPointer.setText("Player's crib");

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
                this.starterView.setCard(this.game.getStarter());
                this.starterView.showCardFace(true);

                this.discardPileOpponent.setStackStyle(DiscardPileLayout.StackStyle.PEG);
                this.discardPilePlayer.setStackStyle(DiscardPileLayout.StackStyle.PEG);
                this.game.peg();
                // return pegging card to hands
                // TODO implement this
                // switch discard piles back to discard style
                this.discardPileOpponent.setStackStyle(DiscardPileLayout.StackStyle.DISCARD);
                this.discardPilePlayer.setStackStyle(DiscardPileLayout.StackStyle.DISCARD);
//                inputLock.block();
                this.game.scorePlayers();

                Log.d(TAG, Arrays.toString(this.game.getScore()));
            }
        }
    }

}
