package com.rr.cribbage_android;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.Toolbar;
import com.cribbage.*;

public class MainActivity extends Activity {
    private final String TAG = "Main activity";
    private Game game;

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
        this.game = new Cribbage(p1,p2,0);
        this.game.step();
//        List<Card> ccl_hand = p1.getHand().getHand();
//
//        HandLayout hl1 = findViewById(R.id.HandLayoutPlayer);
//        ArrayList<PlayingCardView> hand = new ArrayList<>();
//        for(Card c:ccl_hand){
//            Log.d(TAG, c.toString());
//            PlayingCardView pcv = new PlayingCardView(this);
//            pcv.setCard(c.getInt());
//            hand.add(pcv);
//        }
//        hl1.addHand(hand);
    }

}
