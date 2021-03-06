/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AtomicReferenceArray9Test extends JSR166TestCase {
    public static void main(String[] args) {
        main(suite(), args);
    }
    public static Test suite() {
        return new TestSuite(AtomicReferenceArray9Test.class);
    }

    /**
     * get and set for out of bound indices throw IndexOutOfBoundsException
     */
    public void testIndexing() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int index : new int[] { -1, SIZE }) {
            final int j = index;
            final Runnable[] tasks = {
                () -> aa.getPlain(j),
                () -> aa.getOpaque(j),
                () -> aa.getAcquire(j),
                () -> aa.setPlain(j, null),
                () -> aa.setOpaque(j, null),
                () -> aa.setRelease(j, null),
                () -> aa.compareAndExchange(j, null, null),
                () -> aa.compareAndExchangeAcquire(j, null, null),
                () -> aa.compareAndExchangeRelease(j, null, null),
                () -> aa.weakCompareAndSetVolatile(j, null, null),
                () -> aa.weakCompareAndSetAcquire(j, null, null),
                () -> aa.weakCompareAndSetRelease(j, null, null),
            };

            assertThrows(IndexOutOfBoundsException.class, tasks);
        }
    }

    /**
     * getPlain returns the last value set
     */
    public void testGetPlainSet() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.getPlain(i));
            aa.set(i, two);
            assertEquals(two, aa.getPlain(i));
            aa.set(i, m3);
            assertEquals(m3, aa.getPlain(i));
        }
    }

    /**
     * getOpaque returns the last value set
     */
    public void testGetOpaqueSet() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.getOpaque(i));
            aa.set(i, two);
            assertEquals(two, aa.getOpaque(i));
            aa.set(i, m3);
            assertEquals(m3, aa.getOpaque(i));
        }
    }

    /**
     * getAcquire returns the last value set
     */
    public void testGetAcquireSet() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.getAcquire(i));
            aa.set(i, two);
            assertEquals(two, aa.getAcquire(i));
            aa.set(i, m3);
            assertEquals(m3, aa.getAcquire(i));
        }
    }

    /**
     * get returns the last value setPlain
     */
    public void testGetSetPlain() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setPlain(i, one);
            assertEquals(one, aa.get(i));
            aa.setPlain(i, two);
            assertEquals(two, aa.get(i));
            aa.setPlain(i, m3);
            assertEquals(m3, aa.get(i));
        }
    }

    /**
     * get returns the last value setOpaque
     */
    public void testGetSetOpaque() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setOpaque(i, one);
            assertEquals(one, aa.get(i));
            aa.setOpaque(i, two);
            assertEquals(two, aa.get(i));
            aa.setOpaque(i, m3);
            assertEquals(m3, aa.get(i));
        }
    }

    /**
     * get returns the last value setRelease
     */
    public void testGetSetRelease() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setRelease(i, one);
            assertEquals(one, aa.get(i));
            aa.setRelease(i, two);
            assertEquals(two, aa.get(i));
            aa.setRelease(i, m3);
            assertEquals(m3, aa.get(i));
        }
    }

    /**
     * compareAndExchange succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchange() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.compareAndExchange(i, one, two));
            assertEquals(two, aa.compareAndExchange(i, two, m4));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchange(i,m5, seven));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchange(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

    /**
     * compareAndExchangeAcquire succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchangeAcquire() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.compareAndExchangeAcquire(i, one, two));
            assertEquals(two, aa.compareAndExchangeAcquire(i, two, m4));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchangeAcquire(i,m5, seven));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchangeAcquire(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

    /**
     * compareAndExchangeRelease succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchangeRelease() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            assertEquals(one, aa.compareAndExchangeRelease(i, one, two));
            assertEquals(two, aa.compareAndExchangeRelease(i, two, m4));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchangeRelease(i,m5, seven));
            assertEquals(m4, aa.get(i));
            assertEquals(m4, aa.compareAndExchangeRelease(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetVolatile succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetVolatile() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            do {} while (!aa.weakCompareAndSetVolatile(i, one, two));
            do {} while (!aa.weakCompareAndSetVolatile(i, two, m4));
            assertEquals(m4, aa.get(i));
            do {} while (!aa.weakCompareAndSetVolatile(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetAcquire succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetAcquire() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            do {} while (!aa.weakCompareAndSetAcquire(i, one, two));
            do {} while (!aa.weakCompareAndSetAcquire(i, two, m4));
            assertEquals(m4, aa.get(i));
            do {} while (!aa.weakCompareAndSetAcquire(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetRelease succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetRelease() {
        AtomicReferenceArray<Integer> aa = new AtomicReferenceArray<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, one);
            do {} while (!aa.weakCompareAndSetRelease(i, one, two));
            do {} while (!aa.weakCompareAndSetRelease(i, two, m4));
            assertEquals(m4, aa.get(i));
            do {} while (!aa.weakCompareAndSetRelease(i, m4, seven));
            assertEquals(seven, aa.get(i));
        }
    }

}
