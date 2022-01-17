package com.github.rguliamov.dreamtrip.app.infra.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Guliamov Rustam
 */
public class CommonUtils {
    private CommonUtils() {
    }

    /**
     * returns not-null unmodifiable copy of the source set
     *
     * @param source
     * @param <T>
     * @return
     */
    public static <T> Set<T> getSafeSet(Set<T> source) {
        return Collections.unmodifiableSet(Optional.ofNullable(source).orElse(Collections.emptySet()));
    }

    /**
     * returns not-null unmodifiable copy of the source set
     *
     * @param source
     * @param <T>
     * @return
     */
    public static <T> List<T> getSafeList(List<T> source) {
        return Collections.unmodifiableList(Optional.ofNullable(source).orElse(Collections.emptyList()));
    }
}
