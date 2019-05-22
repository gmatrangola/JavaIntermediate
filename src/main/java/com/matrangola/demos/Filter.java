package com.matrangola.demos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Filter {
    public interface Test<E> {
        boolean test(E e);
    }

    public interface Something {
        void doit();
    }

    public static <E> void prettyPrint(List<E> data, Something p) {
        for (E datum : data) {
            p.doit();
        }

    }

    public static <E> void filterList(List<E> data,
                                      Test<E> t) {
        Iterator<E> iterator = data.iterator();
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (!t.test(item))
                iterator.remove();
        }
    }

    public static <E> List<E> filterListCopy(List<E> data,
                                      Test<E> t) {
        List<E> copy = new ArrayList<>();
        for (E e : data) {
            if (t.test(e)) copy.add(e);
        }

        return copy;
    }

}