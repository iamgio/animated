package eu.iamgio.animated;

import java.util.function.Function;

/**
 * Interface that defines a class with customizable {@link AnimationSettings}.
 * @param <T> settings owner
 * @author Giorgio Garofalo
 */
public interface CustomizableAnimation<T> {

    /**
     * @return current animation settings for this object
     */
    AnimationSettings getSettings();

    /**
     * Applies custom animation settings
     * @param settings animation settings to set
     * @param <A> either {@link T} or subclass
     * @return this for concatenation
     */
    <A extends T> A withSettings(AnimationSettings settings);

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings -> settings.withDuration(...))</pre>
     * @param <A> either {@link T} or subclass
     * @return this for concatenation
     */
    default <A extends T> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(getSettings()));
    }
}
