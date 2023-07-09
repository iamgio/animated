package eu.iamgio.animated.common;

import javafx.beans.property.BooleanProperty;

/**
 * A {@link Pausable} object exposes a boolean value that represents whether animations on that object should be skipped,
 * meaning the animated implementation should be ignored and the standard one should be used.
 * @author Giorgio Garofalo
 */
public interface Pausable {

    /**
     * @return whether animations should be skipped
     */
    BooleanProperty pausedProperty();

    /**
     * @return whether animations should be skipped
     */
    default boolean isPaused() {
        return pausedProperty().get();
    }

    /**
     * Prevents any animation from playing until {@link #resume()} is called
     * (only effective if {@link #isPaused()} is <tt>false</tt>).
     */
    default void pause() {
        pausedProperty().set(true);
    }

    /**
     * Lets animations play (only effective if {@link #isPaused()} is <tt>true</tt>).
     */
    default void resume() {
        pausedProperty().set(false);
    }
}
