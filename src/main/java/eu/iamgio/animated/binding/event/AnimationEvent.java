package eu.iamgio.animated.binding.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * An animation-related event.
 * @see ListenableAnimation
 */
public final class AnimationEvent extends Event {

    private static final EventType<AnimationEvent> ANIMATION = new EventType<>("ANIMATION");

    private final boolean interrupted;

    public AnimationEvent(boolean interrupted) {
        super(ANIMATION);
        this.interrupted = interrupted;
    }

    /**
     * @return whether this animation was interrupted.
     * Note:
     * <ul>
     *     <li>
     *         If this event represents the end of an animation, the interrupted status
     *         means the animation stopped before the expected time because the wrapped value
     *         was externally changed and a new animation has to be played.
     *     </li>
     *     <li>
     *         If this event represents the start of an animation, the interrupted status
     *         means the animation started right after an interrutped end animation.
     *     </li>
     * </ul>
     */
    public boolean isInterrupted() {
        return this.interrupted;
    }
}
