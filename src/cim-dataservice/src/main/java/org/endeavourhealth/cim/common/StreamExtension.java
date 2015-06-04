package org.endeavourhealth.cim.common;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamExtension {
    public static <T> Collector<T, ?, T> singleCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public static <T> Collector<T, ?, T> singleOrNullCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() == 0) {
                        return null;
                    }
                    else if (list.size() == 1) {
                        return list.get(0);
                    }
                    else {
                        throw new IllegalStateException();
                    }
                }
        );
    }

    public static <T> Collector<T, ?, T> firstCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() == 0) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public static <T> Collector<T, ?, T> firstOrNullCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() == 0) {
                        return null;
                    }
                    else {
                        return list.get(0);
                    }
                }
        );
    }

}
