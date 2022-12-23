package eu.iamgio.animated.transition;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * A {@link Pausable} object features a boolean value that represents whether animations should be skipped, alias the <i>animated</i> implementation should be ignored.
 * @author Giorgio Garofalo
 */
public interface Pausable {

    /**
     * @return whether animations are paused, so that animations are skipped
     */
    SimpleBooleanProperty pausedProperty();

    /**
     * @return whether animations are paused, so that animations are skipped
     */
    default boolean isPaused() {
        return pausedProperty().get();
    }

    /**
     * Prevents any animation from playing until {@link #resume()} is called.
     */
    default void pause() {
        pausedProperty().set(true);
    }

    /**
     * Lets animations play (effective only if {@link #isPaused()} is <tt>true</tt>).
     */
    default void resume() {
        pausedProperty().set(false);
    }
}
