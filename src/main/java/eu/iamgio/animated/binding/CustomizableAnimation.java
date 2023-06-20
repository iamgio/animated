package eu.iamgio.animated.binding;

import java.util.function.Function;

/**
 * Interface that defines a class with customizable {@link AnimationSettings}.
 * @param <T> settings owner
 * @author Giorgio Garofalo
 */
public interface CustomizableAnimation<T> {

    /**
     * Applies custom animation settings.
     * @param settings animation settings to set
     */
    void setSettings(AnimationSettings settings);

    /**
     * Applies custom animation settings.
     * @param settings animation settings to set
     * @param <A> {@link T} or subclass
     * @return this for concatenation
     */
    @SuppressWarnings("unchecked")
    default <A extends T> A withSettings(AnimationSettings settings) {
        setSettings(settings);
        return (A) this;
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings{@literal ->} settings.withDuration(...))</pre>
     * @param <A> {@link T} or subclass
     * @return this for concatenation
     */
    <A extends T> A custom(Function<AnimationSettings, AnimationSettings> settings);
}
