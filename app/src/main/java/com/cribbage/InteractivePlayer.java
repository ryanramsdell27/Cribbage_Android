package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class InteractivePlayer extends Player {

    @Override
    public Card[] discard() {
        Card [] dis = new Card[2];
        Iterator<Card> itt = this.hand.iterator();
        dis[0] = itt.next();
        dis[1] = itt.next();
        super.discard(dis);
        return dis;
    }

    @Override
    public Card peg(ArrayList<Card> peg_pile) {
        return null;
    }
}
