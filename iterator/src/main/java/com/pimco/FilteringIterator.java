package com.pimco;

import java.util.Iterator;

/**
 * Created by valdch2 on 2/17/2017.
 */
public class FilteringIterator implements Iterator {

    private final Iterator myIterator;
    private final IObjectTest myTest;

    private Object nextElement;

    public FilteringIterator(Iterator myIterator, IObjectTest myTest) {
        this.myIterator = myIterator;
        this.myTest = myTest;
    }

    @Override
    public boolean hasNext() {
        if (this.nextElement != null) {
            return true;
        }
        if (this.myIterator.hasNext()) {
            Object next = this.myIterator.next();
            if (this.myTest.test(next)) {
                this.nextElement = next;
                return true;
            } else {
                return hasNext();
            }
        }
        return false;
    }

    @Override
    public Object next() {
        if (this.nextElement != null) {
            Object next = this.nextElement;
            this.nextElement = null;
            return next;
        }
        Object next = this.myIterator.next();
        if (this.myTest.test(next)) {
            return next;
        } else {
            return next();
        }
    }

    @Override
    public void remove() {
        this.myIterator.remove();
    }
}
