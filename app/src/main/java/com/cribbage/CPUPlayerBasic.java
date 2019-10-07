package com.cribbage;

import java.util.List;

public class CPUPlayerBasic extends CPUPlayer {

    @Override
    public Card[] discard() {
        List<Card> h = this.getHand().getHand();
        Card [] dis = {h.get(0), h.get(1)};
        super.discard(dis);
        return dis;
    }
}
