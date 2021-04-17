package eu.iamgio.animated.property;

import eu.iamgio.animated.AnimationSettings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

import java.util.function.Function;

/**
 * Interface that allows wrapping object and primitive JavaFX properties without the explicit <pre>asObject()</pre> call.
 * @param <T>
 * @author Giorgio Garofalo
 */
public interface PropertyWrapper<T> {

    Property<T> getProperty();
    void set(T value);
    void addListener(ChangeListener<? super T> listener);

    /**
     * Applies custom animation settings
     * @param settings animation settings to set
     * @return this for concatenation
     */
    default AnimatedProperty<T> withSettings(AnimationSettings settings) {
        return new AnimatedProperty<>(this, settings);
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings -> settings.withDuration(...))</pre>
     * @return this for concatenation
     */
    default AnimatedProperty<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(new AnimationSettings()));
    }
}
