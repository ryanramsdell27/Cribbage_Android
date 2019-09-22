package com.cribbage;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/**
 * An iterator for the elements in the power set of this hand. Enumerates the power set
 * by creating an n-bit binary representation of the given set and cycling through all 2^n
 * values of the representation.
 * Assumes that the given set will not change before use of the iterator is done. Changes
 * in cardinality or in ordering will break this
 * @author Ryan
 */
public class PowerSetIterator<E> implements Iterator {
    int loc = 0;
    int num_bits;
    List<E> set;

    /**
     * Creates a new iterator to enumerate the 2^num_bits elements in the power set
     * @param set The set
     */
    PowerSetIterator(List<E> set){
        this.num_bits = set.size();
        this.set = set;
    }

    /**
     * Tracks that our location in the enumeration of the power set is less
     * than it's cardinality of 2^num_bits
     * @return True if there are still elements to enumerate
     */
    @Override
    public boolean hasNext() {
        return this.loc < 1<<num_bits;
    }

    /**
     * Generates the next set in the enumeration of the Power set
     * Converts the loc marker into a bit array, any set bits indicate
     * that that element will be in the returned set. Constructs said
     * set, increases the marker, and returns the set.
     * @return An element of the power set of the given set, i.e., a subset of the given set
     */
    @Override
    public Object next() {
        ArrayList<E> ret = new ArrayList<E>();
        BitSet bs = BitSet.valueOf( new long[]{this.loc});
        //System.out.print(bs.toString() + " ");
        for(int i = 0; i < this.num_bits; i++){
            if(bs.get(i)) ret.add(this.set.get(i));
        }
        loc++;
        //System.out.print(loc + " ");
        return ret;
    }
}
