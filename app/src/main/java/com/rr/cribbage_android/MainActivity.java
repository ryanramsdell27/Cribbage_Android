package com.rr.cribbage_android;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;
import com.cribbage.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private final String TAG = "Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow(); w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        CPUPlayerAVG p1 = new CPUPlayerAVG();
        Player p2 = new CPUPlayerMAX();
        Cribbage game = new Cribbage(p1,p2,0);
        game.step();

        List<Card> ccl_hand = p1.getHand().getHand();

        HandLayout hl = findViewById(R.id.HandLayoutPlayer);
        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for(Card c:ccl_hand){
            Log.d(TAG, c.toString());
            PlayingCardView pcv = new PlayingCardView(this);
            pcv.setCard(c.getInt());
            hand.add(pcv);
        }
        hl.addHand(hand);
    }
}
