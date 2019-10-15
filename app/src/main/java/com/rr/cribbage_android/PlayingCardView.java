package com.rr.cribbage_android;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatImageView;
import com.cribbage.Card;

public class PlayingCardView extends AppCompatImageView {
    private final String TAG = "Playing card view";

    private boolean faceUp;
    private int imageId;
    private Card card;
    private boolean peggable;

    private static int backId = R.drawable.back;
    private static int [] cardIds = {R.drawable.hearts_ace,R.drawable.hearts_2,R.drawable.hearts_3,R.drawable.hearts_4,R.drawable.hearts_5,R.drawable.hearts_6,R.drawable.hearts_7,R.drawable.hearts_8,R.drawable.hearts_9,R.drawable.hearts_10,R.drawable.hearts_jack,R.drawable.hearts_queen,R.drawable.hearts_king,R.drawable.diamonds_ace,R.drawable.diamonds_2,R.drawable.diamonds_3,R.drawable.diamonds_4,R.drawable.diamonds_5,R.drawable.diamonds_6,R.drawable.diamonds_7,R.drawable.diamonds_8,R.drawable.diamonds_9,R.drawable.diamonds_10,R.drawable.diamonds_jack,R.drawable.diamonds_queen,R.drawable.diamonds_king,R.drawable.clubs_ace,R.drawable.clubs_2,R.drawable.clubs_3,R.drawable.clubs_4,R.drawable.clubs_5,R.drawable.clubs_6,R.drawable.clubs_7,R.drawable.clubs_8,R.drawable.clubs_9,R.drawable.clubs_10,R.drawable.clubs_jack,R.drawable.clubs_queen,R.drawable.clubs_king,R.drawable.spades_ace,R.drawable.spades_2,R.drawable.spades_3,R.drawable.spades_4,R.drawable.spades_5,R.drawable.spades_6,R.drawable.spades_7,R.drawable.spades_8,R.drawable.spades_9,R.drawable.spades_10,R.drawable.spades_jack,R.drawable.spades_queen,R.drawable.spades_king};
    public PlayingCardView(Context context) {
        super(context);
        setUp();
    }
    public PlayingCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }
    public PlayingCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp(){
        this.setImageResource(R.drawable.back);
        this.faceUp = false;
        this.setAdjustViewBounds(true);
        this.peggable = true;
        /* For drop shadow */
//        this.setElevation(20);
//        this.setOutlineProvider(new PlayingCardViewOutlineProvider());
    }

    public void toggleFaceUp(){
        this.faceUp = !this.faceUp;
        this.showCardFace(this.faceUp);
    }

    public void setCard(Card card){
        int index = card.getRank()-1 + 13*card.getSuit();
        this.imageId = cardIds[index];
        this.card = card;
    }

    public void setPeggable(boolean peggable){
        this.peggable = peggable;
    }

    public boolean getPeggable(){
        return this.peggable;
    }

    public Card getCard(){
        return this.card;
    }

    public void showCardFace(boolean show){
        if(show){
            this.setImageResource(imageId);
        }
        else{
            this.setImageResource(backId);
        }
        this.faceUp = show;
    }

//    public static class PlayingCardViewOutlineProvider extends ViewOutlineProvider{
//        @Override
//        public void getOutline(View view, Outline outline) {
//            Rect rect = new Rect();
//            view.getDrawingRect(rect);
//            outline.setRoundRect( rect, 50);
//        }
//    }

}
