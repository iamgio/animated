package eu.iamgio.animated.binding.event;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;

/**
 * A provider of listeners for animation-related events.
 * @see AnimationEvent
 */
public interface ListenableAnimation {

    /**
     * @return the action to run when an animation begins
     */
    ObjectProperty<EventHandler<AnimationEvent>> onAnimationStartedProperty();

    /**
     * @param handler the action to run when an animation begins
     */
    default void setOnAnimationStarted(EventHandler<AnimationEvent> handler) {
        onAnimationStartedProperty().set(handler);
    }

    /**
     * @return the action to run when an animation finishes
     */
    ObjectProperty<EventHandler<AnimationEvent>> onAnimationEndedProperty();

    /**
     * @param handler the action to run when an animation finishes
     */
    default void setOnAnimationEnded(EventHandler<AnimationEvent> handler) {
        onAnimationEndedProperty().set(handler);
    }
}
