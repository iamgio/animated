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
     * @return this for concatenation
     */
    @SuppressWarnings("unchecked")
    default T withSettings(AnimationSettings settings) {
        setSettings(settings);
        return (T) this;
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings{@literal ->} settings.withDuration(...))</pre>
     * @return this for concatenation
     */
    T custom(Function<AnimationSettings, AnimationSettings> settings);
}
