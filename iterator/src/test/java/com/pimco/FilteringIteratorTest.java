package com.pimco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by valdch2 on 2/17/2017.
 */
public class FilteringIteratorTest {

    @Test
    public void testSomeFiltered() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = Arrays.asList(object1, filteredObject, object2, filteredObject);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object1, filteringIterator.next());

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object2, filteringIterator.next());

        Assert.assertFalse(filteringIterator.hasNext());
        boolean exceptionCaught = false;
        try {
            filteringIterator.next();
        } catch(NoSuchElementException e) {
            exceptionCaught = true;
        } finally {
            Assert.assertTrue(exceptionCaught);
        }
    }

    @Test
    public void testAllFiltered() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Collection<Object> items = Arrays.asList(filteredObject, filteredObject, filteredObject);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        Assert.assertFalse(filteringIterator.hasNext());
        boolean exceptionCaught = false;
        try {
            filteringIterator.next();
        } catch(NoSuchElementException e) {
            exceptionCaught = true;
        } finally {
            Assert.assertTrue(exceptionCaught);
        }
    }


    @Test
    public void testNoneFiltered() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = Arrays.asList(object1, object2);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object1, filteringIterator.next());

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object2, filteringIterator.next());

        Assert.assertFalse(filteringIterator.hasNext());
        boolean exceptionCaught = false;
        try {
            filteringIterator.next();
        } catch(NoSuchElementException e) {
            exceptionCaught = true;
        } finally {
            Assert.assertTrue(exceptionCaught);
        }
    }

    @Test
    public void hasNextCalledMultipleTimesBeforeNext() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = Arrays.asList(filteredObject, object1, filteredObject, object2, filteredObject);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object1, filteringIterator.next());

        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertTrue(filteringIterator.hasNext());
        Assert.assertEquals(object2, filteringIterator.next());

        Assert.assertFalse(filteringIterator.hasNext());
        Assert.assertFalse(filteringIterator.hasNext());
    }

    @Test
    public void testNextIsCalledWithoutHasNext() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = Arrays.asList(object1, filteredObject, object2, filteredObject);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        Assert.assertEquals(object1, filteringIterator.next());

        Assert.assertEquals(object2, filteringIterator.next());

        Assert.assertFalse(filteringIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextIsCalledWithNoNextAllFiltered() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Collection<Object> items = Arrays.asList(filteredObject, filteredObject);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        filteringIterator.next();
    }

    @Test
    public void testRemoveNoneFiltered() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        List<Object> items = new ArrayList<>();
        items.add(object1);
        items.add(object2);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        filteringIterator.next();
        filteringIterator.remove();
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(object2, items.get(0));
    }


    @Test
    public void testRemove() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        List<Object> items = new ArrayList<>();
        items.add(filteredObject);
        items.add(object1);
        items.add(filteredObject);
        items.add(object2);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        filteringIterator.next();
        filteringIterator.remove();
        Assert.assertEquals(3, items.size());
        Assert.assertEquals(filteredObject, items.get(0));
        Assert.assertEquals(filteredObject, items.get(1));
        Assert.assertEquals(object2, items.get(2));
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveWithoutNext() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(object1);
        items.add(object2);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        filteringIterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void testMultipleRemove() {
        final Object filteredObject = new Object();
        IObjectTest filter = new IObjectTest() {
            @Override
            public boolean test(Object o) {
                return o != filteredObject;
            }
        };
        Object object1 = new Object();
        Object object2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(object1);
        items.add(object2);
        Iterator filteringIterator = new FilteringIterator(items.iterator(), filter);

        filteringIterator.next();
        filteringIterator.remove();
        filteringIterator.remove();
    }

}
