package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator for the combinations of fixed cardinality n from a set. n > 0. Represents set as
 * binary number where each bit is whether or not that element in the set will be selected
 * for the combination. i.e. for a[n ordered] set {3,6,8} a bit string 010 would create output
 * set {6}. This iterator keeps track of the location of 0s in the bit string rather than 1s
 * since the normal use case will be for 6 choose 4 so only two indices need to be tracked for 0s
 * rather than 4 for 1s. These two indices are ordered and incremented to avoid duplicate sets being
 * produced.
 * @param <E>
 * @author Ryan
 */
public class CombinationIterator<E> implements Iterator {

    List<E> set;
    ArrayList<Integer> zero_indices;
    int num_zeros;
    int size_selected;
    private boolean has_next;

    /**
     * Constructs an iterator that enumerate combinations of size <code>num</code> of elements in
     * the given set.
     * @param set The set from which the combinations are constructed
     * @param num The cardinality of the returned sets
     */
    public CombinationIterator(List<E> set, int num){
        this.set = set;
        this.size_selected = num;
        this.num_zeros = set.size() - num;
        this.has_next = num_zeros >= 0 && num > 0;
        this.zero_indices = new ArrayList<>(this.num_zeros);
        for(int i = 0; i < this.num_zeros; i++){
            this.zero_indices.add(i);
        }
    }

    /**
     * Helper function that increases the values storing which indices are 0s. If a higher index in
     * the zero_indices array reaches it's max value a recursive call is made to increase the lower
     * index/indices as appropriate maintaining the order that the value zero_indices[i] < zero_indices[i+1]
     *
     * Side effect is that if the first element in zero_indices reaches beyond it's max value then the
     * entire set has been enumerated so the iterator has no next value
     * @param index The intended index to update
     * @return The value in the updated index
     */
    private int increment(int index){
        int val = this.zero_indices.get(index);
        this.zero_indices.set(index, val+1);
        int max_val_at_index = this.set.size()-(this.num_zeros-index);
        if(val == max_val_at_index){
            if(index == 0){
                this.has_next = false;
                return max_val_at_index;
            }
            int update = increment(index-1)+1;
            if(update > max_val_at_index) update = index;
            this.zero_indices.set(index, update);
        }
        return this.zero_indices.get(index);
    }

    /**
     * Determines if this iterator has a next combination
     * @return True if another combination exists
     */
    @Override
    public boolean hasNext() {
        return this.has_next;
    }

    /**
     * Get a combination of the desired number of elements form the provided set in the enumeration
     * @return A subset (n-combination) of the set
     */
    @Override
    public Object next() {
        ArrayList<E> ret = new ArrayList<>(this.size_selected);
        for(int i = 0; i < this.set.size(); i++) {
            if(!this.zero_indices.contains(i)) ret.add(this.set.get(i));
        }
        increment(this.num_zeros-1);
        return ret;
    }
}
