package com.rr.cribbage_android;

import android.app.Activity;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.Toolbar;
import com.cribbage.*;

import java.util.Arrays;

public class MainActivity extends Activity {
    private final String TAG = "Main activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow(); w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

//        CPUPlayerAVG p1 = new CPUPlayerAVG();
        HandLayout hl1 = findViewById(R.id.HandLayoutPlayer);
        HandLayout hl2 = findViewById(R.id.HandLayoutOpponent);
        WrapperCPUPlayer p1 = new WrapperCPUPlayer(new CPUPlayerAVG(), hl1);
        WrapperCPUPlayer p2 = new WrapperCPUPlayer(new CPUPlayerAVG(), hl2);
        Game game = new Cribbage(p1, p2, 0);
//        this.game.step();
//        while(!this.game.isDone()) {
//            this.game.step();
//            Log.d(TAG, p1.getScore() + " " + p2.getScore());
//        }

        RunGameRunnable rgr = new RunGameRunnable(game);
        new Thread(rgr).start();

    }

    class RunGameRunnable implements Runnable{
        private Game game;
        ConditionVariable discard;
        RunGameRunnable(Game game){
            this.game = game;
            discard = new ConditionVariable();

        }

        @Override
        public void run() {
            while(!this.game.isDone()){
                this.game.step();
                Log.d(TAG, Arrays.toString(this.game.getScore()));
            }
        }

        public void openDiscard(){
            this.discard.open();
        }
    }

}
