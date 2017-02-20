package org.marsik.ham.util;

import java.util.Optional;

public class MultiOptional {
    public static <R,A,B> Optional<R> two(Optional<A> one, Optional<B> two, BiConsumer<R,A,B> consumer) {
        if (!one.isPresent()) {
            return Optional.empty();
        } else if (!two.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(consumer.consume(one.get(), two.get()));
        }
    }

    @FunctionalInterface
    public interface BiConsumer<R,A,B> {
        R consume(A one, B two);
    }
}
