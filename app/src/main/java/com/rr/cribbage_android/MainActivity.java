package com.rr.cribbage_android;

import android.app.Activity;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.Toolbar;
import com.cribbage.*;

import java.sql.Wrapper;
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

        WrapperCPUPlayer p1 = new WrapperCPUPlayer(new CPUPlayerAVG(), handLayoutOpponent, discardPileOpponent);
        //WrapperCPUPlayer p2 = new WrapperCPUPlayer(new CPUPlayerAVG(), handLayoutOpponent);
        WrapperInteractivePlayer p2 = new WrapperInteractivePlayer(handLayoutPlayer, discardPilePlayer);
        Game game = new Cribbage(p1, p2, 1);
//        this.game.step();
//        while(!this.game.isDone()) {
//            this.game.step();
//            Log.d(TAG, p1.getScore() + " " + p2.getScore());
//        }

        RunGameRunnable rgr = new RunGameRunnable(game, p1, p2);
        new Thread(rgr).start();

    }

    public void openInputLock(View v){
        inputLock.open();
    }

    class RunGameRunnable implements Runnable{
        private Game game;
        private WrapperCPUPlayer p1;
        private WrapperInteractivePlayer p2;
        RunGameRunnable(Game game, WrapperCPUPlayer p1, WrapperInteractivePlayer p2){
            this.game = game;
            p1.setUiLockVariable(inputLock);
            p2.setUiLockVariable(inputLock);
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public void run() {
            while(!this.game.isDone()){
                p1.clearHands();
                p2.clearHands();

                this.game.step();
                inputLock.close();
                Log.d(TAG, Arrays.toString(this.game.getScore()));
            }
        }
    }

}
