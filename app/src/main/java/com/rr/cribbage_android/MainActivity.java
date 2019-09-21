package com.rr.cribbage_android;

import android.app.Activity;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandLayout hl = findViewById(R.id.HandLayoutPlayer);
        ArrayList<PlayingCardView> hand = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            hand.add(new PlayingCardView(this));
        }
        hl.addHand(hand);
    }
}
