package it.unicam.cs.followme.jrobot.util;

/**
 * Instances of this class are used to represent a pair of values.
 * @param <F> Type of first element.
 * @param <S> Type of second element.
 */
public record Pair<F, S>(F first, S second) {

    /**
     * Constructs a pair of elements.
     *
     * @param first  The first element.
     * @param second The second element.
     */
    public Pair {
    }
}
